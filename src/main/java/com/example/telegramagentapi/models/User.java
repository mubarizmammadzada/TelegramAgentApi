package com.example.telegramagentapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String fullName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String agentName;
    private String companyName;
    private Long VOEN;
    private LocalDateTime createdDate;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<UserRequest> userRequests;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Offer> offers = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_archived_requests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "archived_request_id"))
    List<ArchivedRequest> archivedRequests = new ArrayList<>();

}
