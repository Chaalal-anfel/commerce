package com.example.commerce.web.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommandeRegistrationDto {
    private String name;
    private String numero;
    private String wilaya;
    private String commune;


    private long refLandP=1;


    public Date getDate() {
        return new Date();
    }



}