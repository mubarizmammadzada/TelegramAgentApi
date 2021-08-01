package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.OfferDto;
import com.example.telegramagentapi.dtos.ReplyDto;
import com.example.telegramagentapi.dtos.RequestDto;
import com.example.telegramagentapi.dtos.UserDto;
import com.example.telegramagentapi.enums.Status;
import com.example.telegramagentapi.models.*;
import com.example.telegramagentapi.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    RabbitService rabbitService;
    UserRepository userRepository;
    RequestRepository requestRepository;
    OfferRepository offerRepository;
    ModelMapper mapper;
    ArchivedRequestRepository archivedRequestRepository;
    UserRequestRepository userRequestRepository;

    public OfferServiceImpl(RabbitService rabbitService, UserRepository userRepository,
                            RequestRepository requestRepository,
                            OfferRepository offerRepository,
                            ModelMapper mapper,
                            ArchivedRequestRepository archivedRequestRepository,
                            UserRequestRepository userRequestRepository) {
        this.rabbitService = rabbitService;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.offerRepository = offerRepository;
        this.mapper = mapper;
        this.archivedRequestRepository = archivedRequestRepository;
        this.userRequestRepository = userRequestRepository;
    }


    @Override
    public String sendOffer(ReplyDto replyDto, UserDto userDto) {
        OfferDto offerDto = new OfferDto();
        ClientRequest request = requestRepository.findClientRequestBySessionId(replyDto.getSessionId());
        User user = userRepository.findUserByUserName(userDto.getUsername());
        Offer offer = new Offer();
        offer.setSessionId(replyDto.getSessionId());
        offer.setBudget(replyDto.getBudget());
        offer.setTourDate(replyDto.getTourDate());
        offer.setTourDestination(replyDto.getTourDestination());
        offer.setTourPeriod(replyDto.getTourPeriod());
        offer.setTravellerCount(replyDto.getTravellerCount());
        offer.setUser(user);
        Optional<UserRequest> userRequest = user.getUserRequests().stream()
                .filter(r -> r.getClientRequest().getSessionId().equals(request.getSessionId())).findAny();
        if (!userRequest.isEmpty()) {
            return "This request already exist";
        }
        UserRequest userRequest1 = new UserRequest();
        userRequest1.setUser(user);
        userRequest1.setClientRequest(request);
        userRequest1.setStatus(Status.OFFERED);
        user.getUserRequests().add(userRequest1);
        user.getOffers().add(offer);
//        request.getUserRequests().add(userRequest1);
//        userRequestRepository.save(userRequest1);
        userRepository.save(user);
        System.out.println("hi");
        File file = generateImage(replyDto, user.getAgentName(), user.getCompanyName());
        offerDto.setImage(file);
        offerDto.setSessionId(replyDto.getSessionId());
        rabbitService.offerSender(offerDto);
        return "ok";
    }

    @Override
    public List<ReplyDto> getOfferByUser(UserDto userDto) {
        User user = userRepository.findUserByUserName(userDto.getUsername());
        if (user != null) {
            List<Offer> offers = offerRepository.findOffersByUser(user.getId());
            List<ReplyDto> offerDto = mapList(offers, ReplyDto.class);
            return offerDto;
        }
        return null;
    }

    private File generateImage(ReplyDto replyDto, String agentName, String companyName) {
        int width = 250;
        int height = 250;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(Color.white);
        graphics2D.drawString("TravelDate " + ":" + replyDto.getTourDate(), 10, 10);
        graphics2D.drawString("Travel Destination " + ":" + replyDto.getTourDestination(), 10, 30);
        graphics2D.drawString("Travel Budget " + ":" + replyDto.getBudget(), 10, 50);
        graphics2D.drawString("Travel Date " + ":" + replyDto.getTourDate(), 10, 70);
        graphics2D.drawString("Agent Name " + ":" + agentName, 10, 90);
        graphics2D.drawString("Agent Name " + ":" + companyName, 10, 110);
        File file = new File("C:\\Users\\Acer\\Desktop\\FirebaseFolder\\salam.png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
