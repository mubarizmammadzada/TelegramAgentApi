package com.example.telegramagentapi.repositories;

import com.example.telegramagentapi.models.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest,Long> {
}
