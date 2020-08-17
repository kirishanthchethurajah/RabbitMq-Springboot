package com.rabbimq.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    private String name;
    private String type;
    private String source;
    private Integer size;
}
