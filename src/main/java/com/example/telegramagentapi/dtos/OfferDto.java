package com.example.telegramagentapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private String agentNumber;
    private File image;
    private String sessionId;
    private Long offerId;
    private Integer messageId;
}
