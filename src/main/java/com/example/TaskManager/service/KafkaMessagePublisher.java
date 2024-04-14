package com.example.TaskManager.service;

import com.example.TaskManager.DTO.TaskResponseDTO;
import com.example.TaskManager.model.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    public void sendMessage(Task message) {
        try {
            String jsonTask = objectMapper.writeValueAsString(message);
            final CompletableFuture<SendResult<String, String>> taskCreated = kafkaTemplate.send("task_created", jsonTask);
                taskCreated.whenComplete((result,ex)->{
                    if (ex == null) {
                        System.out.println("Sent message=[" + message +
                                "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    } else {
                        System.out.println("Unable to send message=[" +
                                message + "] due to : " + ex.getMessage());
                    }
                } );

        } catch (JsonProcessingException ex) {
            System.out.println("Error serializing TaskResponseDTO to JSON: " + ex.getMessage());
        }
    }
}
