package com.example.telegramagentapi.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private String sessionId;
    private String tourDestination;
    private String tourPeriod;
    private String tourDate;
    private String budget;
    private String travellerCount;


}
