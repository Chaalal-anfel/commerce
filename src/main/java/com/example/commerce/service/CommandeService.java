package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.web.dto.CommandeRegistrationDto;

import java.util.List;

public interface  CommandeService {

    Commande save(CommandeRegistrationDto commandeRegistrationDto);

    List<Commande> lire();

    Commande modifier(Long id, Commande commande);

    String supprimer(Long id);
}
