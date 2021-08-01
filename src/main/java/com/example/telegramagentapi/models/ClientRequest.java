package com.example.telegramagentapi.models;

import com.example.telegramagentapi.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private RequestAnswers answers;
    @OneToMany(mappedBy = "clientRequest",cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<UserRequest> userRequests;
    @Enumerated(EnumType.STRING)
    private Status status;


}
