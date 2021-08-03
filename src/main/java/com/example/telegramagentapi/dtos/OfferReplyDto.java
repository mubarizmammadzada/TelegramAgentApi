package com.example.telegramagentapi.dtos;

import com.example.telegramagentapi.models.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferReplyDto {
    private String sessionId;
    private String tourDestination;
    private String tourPeriod;
    private String tourDate;
    private String budget;
    private String travellerCount;
    private ReplyGetDto reply;
}
