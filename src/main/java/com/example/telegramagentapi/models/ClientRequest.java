package com.example.telegramagentapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "client_requests")
public class ClientRequest {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String chatId;
    private String clientId;
    private String sessionId;
    private String lang;
    private LocalDateTime createdDate;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private RequestAnswers answers;


}
