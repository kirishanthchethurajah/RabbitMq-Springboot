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
 public class InvoiceCreatedMessage {

     private double amount;
     @JsonDeserialize(using = CustomDateDeSerializer.class)
     private LocalDate createdDate;

     private String currency;

     private String invoiceNumber;
 }
