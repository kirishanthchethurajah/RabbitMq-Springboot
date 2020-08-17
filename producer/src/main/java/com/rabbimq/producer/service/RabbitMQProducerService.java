package com.rabbimq.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbimq.producer.entity.Employee;
import com.rabbimq.producer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerService {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    private int i =0 ;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public RabbitMQProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message){

        rabbitTemplate.convertAndSend("sampletopic"," It's working" +message+Math.random());
    }

    // milliseconds
//    @Scheduled(fixedRate = 500)
    public void sendScheduledMessage(){
        i++;
        logger.info(String.valueOf(i));
        rabbitTemplate.convertAndSend("scheduledTopic",i+ " It's working " + Math.random());
    }

    public void sendJsonMessage(Employee employee){
        try {
//            rabbitTemplate.convertAndSend("employeeJson",objectMapper.writeValueAsString(employee));
            // Fan out Message
            rabbitTemplate.convertAndSend("x.hr","",objectMapper.writeValueAsString(employee));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void sendDirectMessage(Picture picture){
        try {
            rabbitTemplate.convertAndSend("x.picture",picture.getType(),objectMapper.writeValueAsString(picture));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendTopicMessage(Picture picture){
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(picture.getSource()).append(".");
            if(picture.getSize()>400){
                builder.append("large").append(".");
            }else{
                builder.append("small").append(".");
            }
            builder.append(picture.getType());
            rabbitTemplate.convertAndSend("x.picture2",builder.toString(),objectMapper.writeValueAsString(picture));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendDirectMessageWithRetryMechanism(Picture picture){
        try {
            rabbitTemplate.convertAndSend("x.guideline.work",picture.getType(),objectMapper.writeValueAsString(picture));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendFanoutMsgWithRetryMechanism(Employee employee){
        try {
//            rabbitTemplate.convertAndSend("employeeJson",objectMapper.writeValueAsString(employee));
            // Fan out Message
            rabbitTemplate.convertAndSend("x.guideline2.work","",objectMapper.writeValueAsString(employee));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
