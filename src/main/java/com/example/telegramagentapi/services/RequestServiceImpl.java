package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.ReplyDto;
import com.example.telegramagentapi.dtos.RequestDto;
import com.example.telegramagentapi.dtos.SessionIdDto;
import com.example.telegramagentapi.dtos.UserDto;
import com.example.telegramagentapi.models.ArchivedRequest;
import com.example.telegramagentapi.models.ClientRequest;
import com.example.telegramagentapi.models.RequestAnswers;
import com.example.telegramagentapi.models.User;
import com.example.telegramagentapi.repositories.ArchivedRequestRepository;
import com.example.telegramagentapi.repositories.RequestRepository;
import com.example.telegramagentapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    UserRepository userRepository;
    RequestRepository requestRepository;
    ArchivedRequestRepository archivedRequestRepository;
    ModelMapper mapper;

    public RequestServiceImpl(UserRepository userRepository,
                              RequestRepository requestRepository,
                              ArchivedRequestRepository archivedRequestRepository,
                              ModelMapper mapper) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.archivedRequestRepository = archivedRequestRepository;
        this.mapper = mapper;
    }

    @Override
    public String toArchiveRequest(UserDto userDto, SessionIdDto sessionId) {
        User user = userRepository.findUserByUserName(userDto.getUsername());
        ClientRequest request = requestRepository.findClientRequestBySessionId(sessionId.getSessionId());
        RequestAnswers answers = request.getAnswers();
        if (user != null && request != null) {
            ArchivedRequest archivedRequest = new ArchivedRequest();
            archivedRequest.setBudget(answers.getBudget());
            archivedRequest.setSessionId(request.getSessionId());
            archivedRequest.setTravelDate(answers.getTravelDate());
            archivedRequest.setAddressTo(answers.getAddressTo());
            archivedRequest.setAddressFrom(answers.getAddressFrom());
            archivedRequest.setTravellerCount(answers.getTravellerCount());
            archivedRequest.setTourType(answers.getTourType());
            user.getArchivedRequests().add(archivedRequest);
            userRepository.save(user);
            if (archivedRequestRepository.findArchiveRequestBySessionId(sessionId.getSessionId()) != null) {
                archivedRequestRepository.delete(archivedRequest);
            }
            return "OK";
        }
        return null;
    }

    @Override
    public List<RequestDto> getAllRequests(UserDto userDto) {
        User user = userRepository.findUserByUserName(userDto.getUsername());
        if (user != null) {
            List<ClientRequest> requests = requestRepository.findAll().stream()
                    .filter(r -> r.getCreatedDate().isAfter(user.getCreatedDate())).collect(Collectors.toList());
            List<RequestDto> requestDtos = listMapper(requests, user);

            return requestDtos;
        }
        return null;
    }

    @Override
    public List<ArchivedRequest> getAllArchivedRequests(UserDto userDto) {
        User user = userRepository.findUserByUserName(userDto.getUsername());
        if (user != null) {
            return user.getArchivedRequests();
        }
        return null;
    }

    private List<RequestDto> listMapper(List<ClientRequest> clientRequests, User user) {
        List<RequestDto> requestDtos = new ArrayList<>();
        Optional<ArchivedRequest> archivedRequest;
        for (ClientRequest c : clientRequests) {
            archivedRequest = user.getArchivedRequests().stream()
                    .filter(a -> a.getSessionId().equals(c.getSessionId())).findAny();
            if (archivedRequest.isEmpty()) {
                RequestDto requestDto = new RequestDto();
                requestDto.setLang(c.getLang());
                requestDto.setChatId(c.getChatId());
                requestDto.setClientId(c.getClientId());
                requestDto.setSessionId(c.getSessionId());
                requestDto.getUserAnswers().put("Address From", c.getAnswers().getAddressFrom());
                requestDto.getUserAnswers().put("Address To", c.getAnswers().getAddressTo());
                requestDto.getUserAnswers().put("Budget", c.getAnswers().getBudget());
                requestDto.getUserAnswers().put("Tour Type", c.getAnswers().getTourType());
                requestDto.getUserAnswers().put("Travel Date", c.getAnswers().getTravelDate());
                requestDto.getUserAnswers().put("Traveller Count", c.getAnswers().getTravellerCount());
                requestDtos.add(requestDto);
            }
        }
        return requestDtos;
    }

}
