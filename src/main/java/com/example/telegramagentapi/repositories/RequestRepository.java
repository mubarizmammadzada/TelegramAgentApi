package com.example.telegramagentapi.repositories;

import com.example.telegramagentapi.models.ClientRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface RequestRepository extends JpaRepository<ClientRequest, Long> {
    @Query("select r from ClientRequest r where r.sessionId=:sessionId")
    ClientRequest findClientRequestBySessionId(String sessionId);
}
