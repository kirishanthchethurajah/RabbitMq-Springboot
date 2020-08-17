package com.rabbit.projecttwo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqSchemaConfig {

    @Bean
    public FanoutExchange createFanOutExchange(){
        return new FanoutExchange("exchangeName",true,false,null);
    }

    @Bean
    public Queue createQueue(){
        return new Queue("queueName");
    }

    @Bean
    public Binding createBinding(){
//        BindingBuilder.bind(createQueue()).to(createFanOutExchange());
//        BindingBuilder.bind(createQueue()).to(new DirectExchange("DirectExchangeName",true,false,null)).with("routing-key");
        return new Binding("queueName", Binding.DestinationType.QUEUE,"exchangeName","",null);

    }

    @Bean
    public Declarables createRabbitMqSchema(){
        return new Declarables(
                new FanoutExchange("exchangeName1",true,false,null),
                new Queue("queueName1"),
                new Binding("queueName1", Binding.DestinationType.QUEUE,"exchangeName1","",null)
        );
        }


}
