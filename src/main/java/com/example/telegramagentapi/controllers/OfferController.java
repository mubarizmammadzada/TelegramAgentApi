package com.example.telegramagentapi.controllers;

import com.example.telegramagentapi.dtos.ReplyDto;
import com.example.telegramagentapi.services.OfferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OfferController {
    OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }
    @PostMapping("/offer")
    public String sendOffer(@RequestBody ReplyDto replyDto) {
        offerService.sendOffer(replyDto);
        return "ok";
    }
}
