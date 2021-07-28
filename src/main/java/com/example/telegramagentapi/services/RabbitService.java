package com.example.telegramagentapi.services;

import com.example.telegramagentapi.config.RabbitConfig;
import com.example.telegramagentapi.dtos.OfferDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {
    RabbitTemplate rabbitTemplate;

    public RabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void offerSender(OfferDto offerDto) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, offerDto);
    }
}
