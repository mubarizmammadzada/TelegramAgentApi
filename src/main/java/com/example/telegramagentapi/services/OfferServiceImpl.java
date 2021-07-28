package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.OfferDto;
import com.example.telegramagentapi.dtos.ReplyDto;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class OfferServiceImpl implements OfferService {
    RabbitService rabbitService;

    public OfferServiceImpl(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @Override
    public void sendOffer(ReplyDto replyDto) {
        OfferDto offerDto = new OfferDto();
        File file = generateImage(replyDto);
        offerDto.setImage(file);
        offerDto.setSessionId(replyDto.getSessionId());
        rabbitService.offerSender(offerDto);
    }

    private File generateImage(ReplyDto replyDto) {
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
        File file = new File("C:\\Users\\Acer\\Desktop\\FirebaseFolder\\salam.png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
