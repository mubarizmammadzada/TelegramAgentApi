package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.OfferDto;
import com.example.telegramagentapi.dtos.ReplyDto;
import com.example.telegramagentapi.dtos.UserDto;
import com.example.telegramagentapi.models.Offer;

import java.util.List;

public interface OfferService {
    String sendOffer(ReplyDto offerDto, UserDto userDto);

    List<ReplyDto> getOfferByUser(UserDto userDto);


}
