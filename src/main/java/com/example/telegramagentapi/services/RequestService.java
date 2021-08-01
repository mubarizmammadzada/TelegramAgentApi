package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.ReplyDto;
import com.example.telegramagentapi.dtos.RequestDto;
import com.example.telegramagentapi.dtos.SessionIdDto;
import com.example.telegramagentapi.dtos.UserDto;
import com.example.telegramagentapi.models.ArchivedRequest;

import java.util.List;

public interface RequestService {
    String toArchiveRequest(UserDto userDto, SessionIdDto sessionId);
    List<RequestDto> getAllRequests(UserDto userDto);
    List<ArchivedRequest> getAllArchivedRequests(UserDto userDto);


}
