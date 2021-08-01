package com.example.telegramagentapi.controllers;

import com.example.telegramagentapi.dtos.ReplyDto;
import com.example.telegramagentapi.dtos.UserDto;
import com.example.telegramagentapi.models.Offer;
import com.example.telegramagentapi.services.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class OfferController {
    OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/sendOffer")
    public ResponseEntity<String> sendOffer(@RequestAttribute("user") UserDto userDto, @RequestBody ReplyDto replyDto) {
        return new ResponseEntity<>(offerService.sendOffer(replyDto, userDto), HttpStatus.OK);
    }

    @GetMapping("/offers")
    public ResponseEntity<List<ReplyDto>> getAllOffers(@RequestAttribute("user") UserDto userDto) {
        return new ResponseEntity<>(offerService.getOfferByUser(userDto), HttpStatus.OK);
    }
}
