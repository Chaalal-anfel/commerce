package com.example.commerce.controller;

import com.example.commerce.modele.Commande;
import com.example.commerce.service.CommandeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping
public class EspaceTravailController {
    private final CommandeService commandeService;

    public EspaceTravailController(CommandeService commandeService) {
        super();
        this.commandeService = commandeService;
    }

    @GetMapping("/choix-ids")
    public String showCommandesForm(Model model) {

        List<Commande> orders = commandeService.lireAll();
        Collections.reverse(orders); // Inverser l'ordre des commandes

        model.addAttribute("orders", orders);
        return "EspaceDeTravail/commandes-form";
    }



    @PostMapping("/commandesByIdRange")
    public String getCommandesByIdRange(
            @RequestParam Long firstId,
            @RequestParam Long range,
            @RequestParam String delegue,
            Model model) {
        Long lastId = firstId + range;

        List<Commande> commandes = commandeService.getCommandesByIdRange(firstId, lastId);
        System.out.println(commandes);
        List<Commande> orders = commandeService.lireAll();
        Collections.reverse(orders); // Inverser l'ordre des commandes
        model.addAttribute("commandes", commandes);
        model.addAttribute("orders" , orders);
        model.addAttribute("firstcmd",firstId);
        model.addAttribute("lastcmd",lastId);

        List<Long> idCmds = new ArrayList<>();


        for (long i = firstId; i <= lastId; i++) {
            idCmds.add(i);  }




        commandeService.updateAffectedTo(idCmds, delegue);

        System.out.println("Liste des idCmds : " + idCmds);

        return "redirect:/choix-ids";
    }


    @GetMapping("/userCommandes")
    public String getUserCommandes(Model model) {
        List<Commande> userCommandes = commandeService.getCommandesForCurrentUser();
        model.addAttribute("userCommandes", userCommandes);
        return "EspaceDeTravail/userCommandes"; // le nom du fichier Thymeleaf à afficher
    }

    @ModelAttribute("etatList")
    public List<String> getEtatList() {
        List<String> etats = Arrays.asList(
                "Pas sérieux", "Annulé", "Attente", "Injoignable", "Répond pas",
                "Faux numéro", "Autre (Remarque)", "Confirmé", "Reporté",
                "Doublon", "Expédié", "Fin stock", "Test", "Trop loin"
        );

        // Triez la liste alphabétiquement
        Collections.sort(etats);

        return etats;
    }






}

