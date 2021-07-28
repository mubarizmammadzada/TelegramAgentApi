package com.example.telegramagentapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class RequestAnswers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String tourType;
    private String travelDate;
    private String addressFrom;
    private String addressTo;
    private String travellerCount;
    private String budget;

}
