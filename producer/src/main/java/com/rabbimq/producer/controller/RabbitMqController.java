package com.rabbimq.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RabbitMqController {
    private RabbitTemplate rabbitTemplate;
    private static ObjectMapper mapper ;

    public RabbitMqController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper =  new ObjectMapper();
    }

    public boolean isValidJson(String message){
        try {
            mapper.readTree(message);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
    @PostMapping(path={"/api/publish/{exchange}/{routingkey}",
            "/api/publish/{exchange}",
    },consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> publishMessage(@PathVariable(name = "exchange") String exchange,
                                                 @PathVariable(name = "routingkey", required = false)Optional<String> routingkey,
                                                 @RequestBody String message){
        if(!isValidJson(message)){
            return ResponseEntity.badRequest().body(Boolean.FALSE.toString());
        }

        try {
            rabbitTemplate.convertAndSend(exchange, routingkey.orElse(""), message);
            return ResponseEntity.ok().body(Boolean.TRUE.toString());
        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());

        }

    }
}
