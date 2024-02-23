package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.web.dto.CommandeRegistrationDto;

import java.util.List;

public interface  CommandeService {

    Commande save(CommandeRegistrationDto commandeRegistrationDto, String refLandP,String situation);
    Commande saveA(CommandeRegistrationDto commandeRegistrationDto);


    List<Commande> lire();

    List<Commande> lireAll();
    List<Commande> lireConfirmed();

    Commande getById(Long id);

    Commande modifier(Long id, Commande commande);

    String supprimer(Long id);

    void confirmOrder(Long orderId);
    void deleteOrder(Long orderId);
    long calculerNombreTotalCommandes();

    void updateOrderStatus(Long orderId, String newStatus);
}
