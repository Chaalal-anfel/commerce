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



    private String refLandP;
    private String refLandPA;
    private String status="non traitée";
    private String nomProduit;

    private String lastModifyBy;

    public Date getDate() {
        return new Date();
    }


    public CommandeRegistrationDto(String name, String numero, String wilaya, String commune, String refLandP, String status, String nomProduit) {
        this.name = name;
        this.numero = numero;
        this.wilaya = wilaya;
        this.commune = commune;
        this.refLandP = refLandP;
        this.status = status;
        this.nomProduit = nomProduit;
        this.lastModifyBy= lastModifyBy;
    }
}