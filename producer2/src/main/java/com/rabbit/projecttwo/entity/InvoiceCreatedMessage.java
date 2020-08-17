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
public class InvoiceCreatedMessage {

    private double amount;
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate createdDate;

    private String currency;

    private String invoiceNumber;
}
