package com.rabbitmq.consumer.services;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.consumer.entity.Employee;
import com.rabbitmq.consumer.entity.Picture;
import com.rabbitmq.consumer.error.DlxFanOutErrorHandler;
import com.rabbitmq.consumer.error.DlxProcessingErrorHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RabbitConsumerService {
    private static final String DEAD_EXCHANGE_NAME = "x.guideline.dead";
    private static final String DEAD_EXCHANGE_NAME_1 = "x.guideline2.dead";
    private static final String ROUTING_KEY = "accounting";
    private DlxProcessingErrorHandler dlxProcessingErrorHandler;
    private DlxFanOutErrorHandler dlxFanOutErrorHandler;
    private ObjectMapper mapper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public RabbitConsumerService() {
        this.dlxProcessingErrorHandler = new DlxProcessingErrorHandler(DEAD_EXCHANGE_NAME);
        this.dlxFanOutErrorHandler = new DlxFanOutErrorHandler(DEAD_EXCHANGE_NAME_1, ROUTING_KEY);
        this.mapper = new ObjectMapper();
    }

    //    @RabbitListener(queues = {"sampletopic","scheduledTopic"})
    @RabbitListener(queues = {"scheduledTopic"}, concurrency = "3")
    public void getMessage(String message) {
        System.out.println(Thread.currentThread().getName() + "-----" + message);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = {"employeeJson", "q.hr.accounting", "q.hr.marketing"})
    public void getJsonMessage(String message) {
        Employee emp;
        try {
            emp = mapper.readValue(message, Employee.class);
            System.out.println(emp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {"q.picture.image", "q.picture.vector", "q.picture.mobile", "q.picture.log"})
    public void getDirectMessage(String message) {
        Picture pic;
        try {
            pic = mapper.readValue(message, Picture.class);
            System.out.println(pic);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {"q.guideline.image.work", "q.guideline.vector.work"})
    public void listen(Message message, Channel channel)
            throws IOException {
        try {
            var p = mapper.readValue(message.getBody(), Picture.class);
            // process the image
            if (p.getSize() > 9000) {
                // throw exception, we will use DLX handler for retry mechanism
                throw new IOException("Size too large");
            } else {
                log.info("Creating thumbnail & publishing : " + p);
                // you must acknowledge that message already processed
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (IOException e) {
            log.warn("Error processing message : " + new String(message.getBody()) + " : " + e.getMessage());
            dlxProcessingErrorHandler.handleErrorProcessingMessage(message, channel);
        }
    }

    @RabbitListener(queues = "q.guideline2.accounting.work")
    public void listenFanout(Message message, Channel channel)
            throws InterruptedException, JsonParseException, JsonMappingException, IOException {
        try {
            var emp = mapper.readValue(message.getBody(), Employee.class);

            if (StringUtils.isEmpty(emp.getName())) {
                throw new IllegalArgumentException("Name is empty");
            } else {
                log.info("On accounting : {}", emp);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            log.warn("Error processing message : {} : {}", new String(message.getBody()), e.getMessage());
            dlxFanOutErrorHandler.handleErrorProcessingMessage(message, channel);
        }


    }

}