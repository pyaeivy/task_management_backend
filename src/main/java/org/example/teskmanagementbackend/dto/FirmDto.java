package org.example.teskmanagementbackend.dto;


import lombok.ToString;

@ToString
public class FirmDto {

    public record FirmCreate(
            Long id,
           String firmType,
           String firmName,
           String firmAddress,
           String firmPhone,
           String firmEmail,
           String firmCity,
           String firmState,
           String firmZipCode,
           String firmCountry
    ){}



}
