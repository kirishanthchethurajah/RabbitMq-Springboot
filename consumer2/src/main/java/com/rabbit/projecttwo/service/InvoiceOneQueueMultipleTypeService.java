package com.rabbit.projecttwo.service;

import com.rabbit.projecttwo.entity.InvoiceCancelledMessage;
import com.rabbit.projecttwo.entity.InvoiceCreatedMessage;
import com.rabbit.projecttwo.entity.InvoicePaidMessage;
import com.rabbit.projecttwo.entity.PaymentCancelStatus;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@RabbitListener(queues = "q.invoice")
@Service
public class InvoiceOneQueueMultipleTypeService {


    @RabbitHandler
    public void handleInvoiceCreated(InvoiceCreatedMessage  invoiceCreatedMessage){

        System.out.println(invoiceCreatedMessage);
    }

    @RabbitHandler
    public void handleInvoicePaid(InvoicePaidMessage invoicePaidMessage){
        System.out.println(invoicePaidMessage);
    }

    @RabbitHandler
    @SendTo("x.invoice.cancel/")
    public PaymentCancelStatus handleCancelledMessage(InvoiceCancelledMessage cancelledMessage){

                return new PaymentCancelStatus(ThreadLocalRandom.current().nextBoolean(),
                        LocalDate.now(),
                        cancelledMessage.getInvoiceNumber());

    }

    @RabbitHandler(isDefault = true)
    public void handleAllOtherMsg(Object o){
        System.out.println(o);
    }
}
