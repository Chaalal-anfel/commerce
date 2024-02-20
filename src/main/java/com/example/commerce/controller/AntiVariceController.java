package com.example.commerce.controller;

import com.example.commerce.modele.Commande;
import com.example.commerce.modele.StatisticsModel;
import com.example.commerce.service.CommandeService;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@ComponentScan
@RequestMapping("/AntiVarice")
public class AntiVariceController {

    private CommandeService commandeService;

    public AntiVariceController(CommandeService commandeService) {
        super();
        this.commandeService = commandeService;
    }
    @ModelAttribute("commande")
    public CommandeRegistrationDto commandeRegistrationDto(){
        return new CommandeRegistrationDto();
    }

    @GetMapping("/pages/{refLandPA}")
    public String showRegistrationForm(@PathVariable String refLandPA, Model model) {
        model.addAttribute("refLandPA", refLandPA);
        return "Anti/AntiVarice";  // Assurez-vous que le nom du répertoire est correct ici
    }
    @PostMapping("/pages/{refLandPA}")
    public String handlePostRequest(@PathVariable String refLandPA, @ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto) {
        // Traitez ici la logique de gestion de la requête POST
        System.out.println(commandeRegistrationDto.toString());
        commandeService.save(commandeRegistrationDto, commandeRegistrationDto.getRefLandPA(),"huille AntiVarice");

        return "redirect:/AntiVarice/pages/page/success";
    }








    @GetMapping("/commandes")
    public String showOrders(Model model) {
        List<Commande> orders = this.commandeService.lire();
        System.out.printf(orders.toString());

        // Décaler les dates d'une heure dans la liste des commandes
        List<Commande> shiftedOrders = orders.stream()
                .map(commande -> {
                    commande.setDateCommande(Date.from(commande.getDateCommande().toInstant().plusSeconds(3600)));
                    return commande;
                })
                .collect(Collectors.toList());

        model.addAttribute("orders", shiftedOrders);
        return "orders";
    }


    @GetMapping("/confirmedOrders")
    public String showConfirmedOrders(Model model){
        List<Commande> orders= this.commandeService.lireConfirmed();
        model.addAttribute("confirmedOrders", orders);
        return "confirmedOrders";
    }


    @GetMapping("/confirmOrder/{orderId}")
    public String confirmOrder(@PathVariable Long orderId) {

        commandeService.confirmOrder(orderId);
        return "redirect:/AntiVarice/commandes"; // Redirect back to the orders page
    }


    @GetMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        commandeService.supprimer(orderId); // Implement this method in your service
        return "redirect:/AntiVarice/commandes"; // Redirect back to the orders page
    }
    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        long nombreTotalCommandes = commandeService.calculerNombreTotalCommandes();
        List<Commande> confirmedOrders = commandeService.lireConfirmed();
        long nombreCommandesConfirmees = confirmedOrders.size();

        // Créez une instance de la classe de modèle et définissez les données
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.setNombreTotalCommandes(nombreTotalCommandes);
        statisticsModel.setConfirmedOrders(confirmedOrders);
        statisticsModel.setNombreCommandesConfirmees(nombreCommandesConfirmees);

        // Ajoutez la classe de modèle au modèle Thymeleaf
        model.addAttribute("statistics", statisticsModel);

        // Retournez le nom de la vue Thymeleaf
        return "statistics";
    }



    @GetMapping("/choixParametre")
    public String showParametreSelectionPage() {
        return "choixParametre";
    }

    @PostMapping("/generate")
    public String generateURL(@RequestParam String refLandPA) {
        // Traitement des paramètres et génération de l'URL en fonction du choix du client
        // Vous pouvez utiliser refLandP comme vous le souhaitez

        // Exemple : Redirection vers la page dynamique avec le paramètre
        return "redirect:/AntiVarice/pages/page?refLandPA=" + refLandPA;
    }

}
