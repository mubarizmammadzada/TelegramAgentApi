package com.example.telegramagentapi.consumer;

import com.example.telegramagentapi.dtos.RequestDto;
import com.example.telegramagentapi.models.ClientRequest;
import com.example.telegramagentapi.models.RequestAnswers;
import com.example.telegramagentapi.repositories.RequestRepository;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AgentConsumer {
    RequestRepository requestRepository;
    ModelMapper mapper;

    public AgentConsumer(RequestRepository requestRepository, ModelMapper mapper) {
        this.requestRepository = requestRepository;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "bot_queue")
    public void getRequestFromQueue(RequestDto requestDto) {
        ClientRequest request = new ClientRequest();
        request.setChatId(requestDto.getChatId());
        request.setClientId(requestDto.getClientId());
        request.setLang(requestDto.getLang());
        request.setSessionId(requestDto.getSessionId().toString());
        request.setCreatedDate(LocalDateTime.now());
        RequestAnswers answers=new RequestAnswers();
        answers.setAddressFrom(requestDto.getUserAnswers().get("addressFrom"));
        answers.setTourType(requestDto.getUserAnswers().get("tourType"));
        answers.setAddressTo(requestDto.getUserAnswers().get("addressTo"));
        answers.setTravelDate(requestDto.getUserAnswers().get("travelDate"));
        answers.setTravellerCount(requestDto.getUserAnswers().get("travellercount"));
        answers.setBudget(requestDto.getUserAnswers().get("budget"));
        request.setAnswers(answers);
        requestRepository.save(request);
    }
}
