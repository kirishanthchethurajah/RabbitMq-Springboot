package com.rabbit.projecttwo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rabbit.projecttwo.deserializer.CustomDateDeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRejectedMessage {
    @JsonDeserialize (using =CustomDateDeSerializer.class)
    private LocalDate rejectedDate;

    private String invoiceNumber;

    private String reason;

}
