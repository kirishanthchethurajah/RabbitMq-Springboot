package com.rabbitmq.consumer.rabbitmq;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
@Data
@EqualsAndHashCode
public class RabbitmqHeaderXDeath {
    private int count;
    private String exchange;
    private String queue;
    private String reason;
    private List<String> routingKeys;
    private Date time;
}
