package com.example.commerce.controller;

import com.example.commerce.modele.Commande;
import com.example.commerce.modele.StatisticsModel;
import com.example.commerce.service.CommandeService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ComponentScan
@RequestMapping
public class page_officiel {


    private final CommandeService commandeService;

    public page_officiel(CommandeService commandeService) {
        super();
        this.commandeService = commandeService;
    }

    @GetMapping
    public String showRegistrationFormhome(){
        return "dashboard" ;
    }

    @GetMapping("/huilleHouloul/pages/page/success")
    public String showRegistrationForm(){
        return "" +
                "huille/pageProduit" ;
    }
    @GetMapping("/AntiVarice/pages/page/success")
    public String showRegistrationFormA(){
        return "Anti/AntiVariceSucces" ;
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
        return "redirect:/commandes"; // Redirect back to the orders page
    }



    @GetMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        commandeService.supprimer(orderId); // Implement this method in your service
        return "redirect:/commandes"; // Redirect back to the orders page
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


    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@ModelAttribute("order") Commande order) {
        commandeService.updateOrderStatus(order.getIdCmd(), order.getStatus());
        return "redirect:/commandes";
    }



}
