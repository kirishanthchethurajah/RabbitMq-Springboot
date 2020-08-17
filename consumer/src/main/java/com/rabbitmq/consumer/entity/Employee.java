package com.rabbitmq.consumer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rabbitmq.consumer.deserializer.CustomDateDeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    @JsonProperty("employee_id")
    private String employeeId;
    private String name;
    @JsonProperty("birth_date")
    @JsonDeserialize(using = CustomDateDeSerializer.class)
    private LocalDate birthDate;
}
