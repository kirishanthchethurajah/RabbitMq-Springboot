package com.rabbit.projecttwo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rabbit.projecttwo.deserializer.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancelStatus {

    private Boolean status;
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate cancelDate;

    private String invoiceNumber;


}

