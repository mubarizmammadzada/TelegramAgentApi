package com.example.telegramagentapi.models;

import com.example.telegramagentapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "users_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ClientRequest clientRequest;
    @Enumerated(EnumType.STRING)
    private Status status;
}
