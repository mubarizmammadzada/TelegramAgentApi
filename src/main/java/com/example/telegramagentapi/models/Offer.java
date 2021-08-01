package com.example.telegramagentapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class Offer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String sessionId;
    private String tourDestination;
    private String tourPeriod;
    private String tourDate;
    private String budget;
    private String travellerCount;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
