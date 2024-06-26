package com.example.commerce.modele;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity @Data
@Table(name= "COMMANDE")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCmd;

    @Column(length=100)
    private String name;

    private String wilaya;

    private String commune;

    @Column( length = 50)
    private String numero;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;

    private String refLandPage;
    private String status;
    private String nomProduit;

    private  String affectedTo ;
    private String lastModifiedBy;




    @Override
    public String toString() {
        return "Commande{" +
                "dateCommande=" + dateCommande +
                '}';
    }
}