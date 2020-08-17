package com.rabbit.projecttwo.service;


import com.rabbit.projecttwo.entity.DummyMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

//@Service
public class DummyService {
    @RabbitListener(queues = "q.dummy", concurrency = "2")
    public void listen(DummyMessage dummyMessage) throws InterruptedException {
        System.out.println(dummyMessage);
        Thread.sleep(20000);

    }
    // will use spring default prefetch value 250
    @RabbitListener(queues = "q.transaction", concurrency = "2")
    public void listenTransaction(DummyMessage dummyMessage) throws InterruptedException {
        System.out.println(dummyMessage);
        Thread.sleep(20);

    }
//    will use prefetch value 1 from the bean
    @RabbitListener(queues = "q.scheduler", concurrency = "2", containerFactory = "prefetchOne")
    public void listenScheduler(DummyMessage dummyMessage) throws InterruptedException {
        System.out.println(dummyMessage);
        Thread.sleep(2000000);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "q.check", durable = "true"),
            exchange = @Exchange(name = "x.check",
                    type = ExchangeTypes.DIRECT,
                    durable = "true"),
            key = "routing-key",
            ignoreDeclarationExceptions = "true"))
    public  void createQueueExchange(){

    }


}
