package com.example.telegramagentapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReplyDto {
    private Long offerId;
    private Integer messageId;
    private String contactInfo;
}
