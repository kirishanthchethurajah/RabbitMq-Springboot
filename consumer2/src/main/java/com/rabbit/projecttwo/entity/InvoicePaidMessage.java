package com.rabbit.projecttwo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rabbit.projecttwo.deserializer.CustomDateDeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoicePaidMessage {
    private String invoiceNumber;
    @JsonDeserialize(using = CustomDateDeSerializer.class)
    private LocalDate paidDate;

    private String paymentNumber;


}
