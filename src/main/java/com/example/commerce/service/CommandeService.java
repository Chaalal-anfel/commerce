package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

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
    long calculerNombreTotalCommandesConfirmes();



    List<Commande> getCommandesByIdRange(Long firstId, Long lastId);

    void updateAffectedTo(List<Long> idCmds, String newAffectedTo);

    List<Commande> getCommandesForCurrentUser();

    Map<String, Long> calculerStatistiquesParDelegue();
    Map<String, Long> calculerStatistiquesParLastModifiedBy();

    List<Object[]> countDistinctCommandsByDateAndRefLandPage();


}

