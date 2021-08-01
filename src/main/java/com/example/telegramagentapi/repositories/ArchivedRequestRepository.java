package com.example.telegramagentapi.repositories;

import com.example.telegramagentapi.models.ArchivedRequest;
import com.example.telegramagentapi.models.ClientRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArchivedRequestRepository extends JpaRepository<ArchivedRequest,Long> {
    @Query("select r from ClientRequest r where r.sessionId=:sessionId")
    ClientRequest findArchiveRequestBySessionId(String sessionId);
}
