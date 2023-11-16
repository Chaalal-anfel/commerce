package com.example.commerce.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProduitRegistrationDto {

    private String nomProduit ="creme hydratante";
    private String descProduit;
    private Integer prixProduit;
    private String url;
    private Integer quantity=1;

}
