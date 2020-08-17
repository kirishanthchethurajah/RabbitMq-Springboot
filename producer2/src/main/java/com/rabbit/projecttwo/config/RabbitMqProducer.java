package com.rabbit.projecttwo.config;

import com.rabbit.projecttwo.entity.DummyMessage;
import com.rabbit.projecttwo.entity.InvoiceCancelledMessage;
import com.rabbit.projecttwo.entity.InvoiceCreatedMessage;
import com.rabbit.projecttwo.entity.InvoicePaidMessage;
import com.rabbit.projecttwo.service.DummyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RabbitMqProducer implements CommandLineRunner {
    private DummyService dummyProducerService;

    public RabbitMqProducer(DummyService dummyProducerService) {
        this.dummyProducerService = dummyProducerService;
    }

    @Override
    public void run(String... args) throws Exception {
//        for (int i=0; i<=500; i++) {
//            dummyProducerService.sendDummy(new DummyMessage("lalala", 1));
//        }
//        dummyProducerService.sendHiSpeedTransactionMsg();
//        dummyProducerService.sendLowSpeedSchedulerMsg();

//        var randomInvoiceNumber = "INV-"+ ThreadLocalRandom.current().nextInt(100,200);
//        dummyProducerService.sendInvoiceCreated(new InvoiceCreatedMessage(190, LocalDate.now(), "EUR", randomInvoiceNumber ));
//        var paymentNumber = "PAY-"+ ThreadLocalRandom.current().nextInt(200,300);
//        dummyProducerService.sendInvoicePaid(new InvoicePaidMessage(randomInvoiceNumber,LocalDate.now(),paymentNumber ));

        //Request reply
        for (int i = 0; i < 10 ; i++) {
            dummyProducerService.sendInvoiceCancelled(new InvoiceCancelledMessage(LocalDate.now(), "Inv " + i, "cancelInvoice"+i));

        }
    }
}
