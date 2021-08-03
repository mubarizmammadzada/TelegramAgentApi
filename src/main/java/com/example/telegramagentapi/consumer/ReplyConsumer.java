package com.example.telegramagentapi.consumer;

import com.example.telegramagentapi.dtos.ClientReplyDto;
import com.example.telegramagentapi.models.Offer;
import com.example.telegramagentapi.models.Reply;
import com.example.telegramagentapi.repositories.OfferRepository;
import com.example.telegramagentapi.repositories.ReplyRepositories;
import com.example.telegramagentapi.repositories.RequestRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplyConsumer {
    //    RequestRepository requestRepository;
    ReplyRepositories replyRepositories;
    OfferRepository offerRepository;

    public ReplyConsumer(ReplyRepositories replyRepositories, OfferRepository offerRepository) {
        this.replyRepositories = replyRepositories;
        this.offerRepository = offerRepository;
    }

    @RabbitListener(queues = "reply_queue")
    public void getReplyFromQueue(ClientReplyDto replyDto) {
        Offer offer = offerRepository.findOfferById(replyDto.getOfferId());
        if (offer!=null) {
            Reply reply = new Reply();
            reply.setContactInfo(replyDto.getContactInfo());
            reply.setOffer(offer);
            replyRepositories.save(reply);
        }

    }
}
