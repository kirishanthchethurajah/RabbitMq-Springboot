package com.rabbitmq.consumer.error;

import com.rabbitmq.client.Channel;
import com.rabbitmq.consumer.rabbitmq.RabbitMqHeader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

public class DlxProcessingErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(DlxProcessingErrorHandler.class);

    @NonNull
    private String deadExchangeName;

    private int maxRetryCount = 3;

    public DlxProcessingErrorHandler(String deadExchangeName) throws IllegalArgumentException {
        super();

        if (StringUtils.isAnyEmpty(deadExchangeName)) {
            throw new IllegalArgumentException("Must define dlx exchange name");
        }

        this.deadExchangeName = deadExchangeName;
    }

    public DlxProcessingErrorHandler(String deadExchangeName, int maxRetryCount) {
        this(deadExchangeName);
        setMaxRetryCount(maxRetryCount);
    }

    public String getDeadExchangeName() {
        return deadExchangeName;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public boolean handleErrorProcessingMessage(Message message, Channel channel) throws IOException {
        var rabbitMqHeader = new RabbitMqHeader(message.getMessageProperties().getHeaders());

        try {
            if (rabbitMqHeader.getFailedRetryCount() >= maxRetryCount) {
                // publish to dead and ack
                log.warn("[DEAD] Error at " + new Date() + " on retry " + rabbitMqHeader.getFailedRetryCount()
                        + " for message " + message);

                channel.basicPublish(getDeadExchangeName(), message.getMessageProperties().getReceivedRoutingKey(),
                        null, message.getBody());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                // requeue it by rejecting and setting the delivery tag to false
                log.debug("[REQUEUE] Error at " + new Date() + " on retry " + rabbitMqHeader.getFailedRetryCount()
                        + " for message " + message);

                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            }
            return true;
        } catch (IOException e) {
            log.warn("[HANDLER-FAILED] Error at " + new Date() + " on retry " + rabbitMqHeader.getFailedRetryCount()
                    + " for message " + message);
        }

        return false;
    }

    public void setMaxRetryCount(int maxRetryCount) throws IllegalArgumentException {
        if (maxRetryCount > 1000) {
            throw new IllegalArgumentException("max retry must between 0-1000");
        }

        this.maxRetryCount = maxRetryCount;
    }

}


