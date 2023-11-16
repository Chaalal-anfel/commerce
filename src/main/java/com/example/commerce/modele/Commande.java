package com.example.commerce.modele;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity @Data
@Table(name= "COMMANDE")
@Getter
@Setter
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

    /*
    @ManyToOne(targetEntity = Produit.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idLand",referencedColumnName = "idLandP")
    */

    private long refLandPage;




    @Override
    public String toString() {
        return "Commande{" +
                "dateCommande=" + dateCommande +
                '}';
    }
}