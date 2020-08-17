package com.rabbit.projecttwo.scheduler;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class RabbitMqScheduler {

    private RabbitListenerEndpointRegistry endpointRegistry;
    @Scheduled(cron = "0 0 23 * * ?")
    public void stopAll(){
        endpointRegistry.getListenerContainers().forEach(c-> c.stop());
    }
    @Scheduled(cron = "1 0 0 * * ?")
    public void startAll(){
        endpointRegistry.getListenerContainers().forEach(c-> c.start());
    }
}
