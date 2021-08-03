package com.example.telegramagentapi.repositories;

import com.example.telegramagentapi.models.Offer;
import com.example.telegramagentapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("select s from Offer s where s.user.id=:id")
    List<Offer> findOffersByUser(Long id);
    @Query("select s from Offer s where s.id=:id")
    Offer findOfferById(Long id);
}
