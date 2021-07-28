package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.OfferDto;
import com.example.telegramagentapi.dtos.ReplyDto;

public interface OfferService {
    void sendOffer(ReplyDto offerDto);

}
