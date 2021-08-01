package com.example.telegramagentapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "archived_requests")
public class ArchivedRequest {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String tourType;
    private String travelDate;
    private String addressFrom;
    private String addressTo;
    private String sessionId;
    private String travellerCount;
    private String budget;
}
