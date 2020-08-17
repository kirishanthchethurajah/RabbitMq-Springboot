package com.rabbimq.producer.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class RabbitMqScheduler {
    private static final Logger log = LoggerFactory.getLogger(RabbitMqScheduler
    .class);

    private RabbitMqProxyService rabbitMqProxyService;

    public RabbitMqScheduler(RabbitMqProxyService rabbitMqProxyService) {
        this.rabbitMqProxyService = rabbitMqProxyService;
    }
    @Scheduled(fixedDelay = 900000)
    public void sweepDirtyQueues(){
    try{
        var dirtyQueues= rabbitMqProxyService.getAllQueues()
                .stream()
                .filter(p -> p.isDirty()).collect(Collectors.toList());
        dirtyQueues.forEach(q-> System.out.println(q.getMessages()));
    }catch (Exception e){
        System.out.println(e.getLocalizedMessage());
    }
}
}