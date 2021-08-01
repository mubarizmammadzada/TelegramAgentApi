package com.example.telegramagentapi.controllers;

import com.example.telegramagentapi.dtos.RequestDto;
import com.example.telegramagentapi.dtos.SessionIdDto;
import com.example.telegramagentapi.dtos.UserDto;
import com.example.telegramagentapi.models.ArchivedRequest;
import com.example.telegramagentapi.models.User;
import com.example.telegramagentapi.services.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/request")
public class RequestController {
    RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/archived")
    public ResponseEntity<String> archivedRequest(@RequestAttribute("user") UserDto userDto,
                                                  @RequestBody SessionIdDto sessionId) {
        requestService.toArchiveRequest(userDto, sessionId);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RequestDto>> getAll(@RequestAttribute("user") UserDto userDto) {
        return new ResponseEntity<>(requestService.getAllRequests(userDto), HttpStatus.OK);
    }

        @GetMapping("/getAllArchived")
    public ResponseEntity<List<ArchivedRequest>> getAllArchivedRequests(@RequestAttribute("user") UserDto userDto) {
        return new ResponseEntity<>(requestService.getAllArchivedRequests(userDto), HttpStatus.OK);
    }
}
