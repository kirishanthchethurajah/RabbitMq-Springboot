package com.rabbit.projecttwo.service;

import com.rabbit.projecttwo.entity.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

    private RabbitTemplate rabbitTemplate;

    public DummyService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDummy(DummyMessage msg){
        rabbitTemplate.convertAndSend("x.dummy","",msg);
    }

    public void sendHiSpeedTransactionMsg(){
        for (int i = 0; i < 10000; i++) {
            rabbitTemplate.convertAndSend("x.transaction","",new DummyMessage("scheduler: "+i, i));
        }

    }

    public void sendLowSpeedSchedulerMsg(){
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend("x.scheduler","",new DummyMessage("scheduler: "+i, i));
        }

    }

    public  void sendInvoiceCreated(InvoiceCreatedMessage invoiceCreatedMessage){
        rabbitTemplate.convertAndSend("x.invoice","",invoiceCreatedMessage);
    }

    public  void sendInvoicePaid(InvoicePaidMessage paidMessage){
        rabbitTemplate.convertAndSend("x.invoice","",paidMessage);
    }
    public  void sendInvoiceCancelled(InvoiceCancelledMessage cancelledMessage){
        rabbitTemplate.convertAndSend("x.invoice","",cancelledMessage);
    }

    public  void sendInvoiceRejected(InvoiceRejectedMessage rejectedMessage){
        rabbitTemplate.convertAndSend("x.invoice","",rejectedMessage);
    }
}
