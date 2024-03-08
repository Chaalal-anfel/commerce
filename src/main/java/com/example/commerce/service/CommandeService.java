package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.web.dto.CommandeRegistrationDto;

import java.util.List;

public interface  CommandeService {

    Commande save(CommandeRegistrationDto commandeRegistrationDto, String refLandP,String situation);


    List<Commande> lire();

    List<Commande> lireAll();
    List<Commande> lireConfirmed();

    List<Commande> lireCommandesConfirme();
    Commande getById(Long id);



    Commande mettreAjourStatusCommande(Long id, String nouveauStatus, String username);

    Commande modifier(Long id, Commande commande);

    String supprimer(Long id);

    void confirmOrder(Long orderId);
    void deleteOrder(Long orderId);
    long calculerNombreTotalCommandes();

}
