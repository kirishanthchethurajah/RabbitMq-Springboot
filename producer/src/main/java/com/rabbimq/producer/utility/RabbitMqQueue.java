package com.rabbimq.producer.utility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitMqQueue {
    @JsonProperty
    private String name;
    @JsonProperty
    private long messages;
    public boolean isDirty(){
        return messages>0;

    }

}
