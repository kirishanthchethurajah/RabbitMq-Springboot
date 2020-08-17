package com.rabbit.projecttwo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rabbit.projecttwo.serializer.CustomDateSerializer;
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
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate paidDate;

    private String paymentNumber;


}
