package com.example.telegramagentapi.dtos;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto implements Serializable {
    private String clientId;
    private String chatId;
    private UUID sessionId;
    private String lang;
    private Map<String, String> userAnswers = new HashMap<>();
}
