package com.example.telegramagentapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyGetDto {
    private Long id;
    private String contactInfo;
}
