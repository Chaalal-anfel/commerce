package com.example.commerce.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name= "PRODUIT")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLandP;
    @Column(length=50 )
    private String nomProduit ="creme hydratante";
    @Column(length=150)
    private String descProduit;
    private String url;
    private Integer prixProduit;
    private Integer quantity;




}