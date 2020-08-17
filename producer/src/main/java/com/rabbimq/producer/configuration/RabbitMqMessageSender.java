package com.rabbimq.producer.configuration;

import com.rabbimq.producer.entity.Employee;
import com.rabbimq.producer.entity.Picture;
import com.rabbimq.producer.service.RabbitMQProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RabbitMqMessageSender implements CommandLineRunner {
    private RabbitMQProducerService rabbitMQProducerService;
    public RabbitMqMessageSender(RabbitMQProducerService rabbitMQProducerService) {
        this.rabbitMQProducerService = rabbitMQProducerService;
    }

    @Override
    public void run(String... args) throws Exception {
//        rabbitMQProducerService.sendMessage("as expected");
//        rabbitMQProducerService.sendScheduledMessage();

     //   rabbitMQProducerService.sendJsonMessage(new Employee("1","krish", LocalDate.now()));
//        rabbitMQProducerService.sendDirectMessage(new Picture("mona_lisa","jpg","mobile",100));
//        rabbitMQProducerService.sendDirectMessage(new Picture("run_john","svg","mobile",100));
//        rabbitMQProducerService.sendTopicMessage(new Picture("mona_lisa","jpg","mobile",1000));
//        rabbitMQProducerService.sendTopicMessage(new Picture("run_john","svg","mobile",100));

//        rabbitMQProducerService.sendDirectMessageWithRetryMechanism(new Picture("mona_lisa","jpg","mobile",9300));
//        rabbitMQProducerService.sendDirectMessageWithRetryMechanism(new Picture("run_john","svg","mobile",9200));
//        rabbitMQProducerService.sendDirectMessageWithRetryMechanism(new Picture("lala","jpg","mobile",9100));
//
//        rabbitMQProducerService.sendFanoutMsgWithRetryMechanism(new Employee("1","krish", LocalDate.now()));
    }

}
