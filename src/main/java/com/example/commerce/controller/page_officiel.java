package com.example.commerce.controller;

import com.example.commerce.modele.Commande;
import com.example.commerce.modele.StatisticsModel;
import com.example.commerce.service.CommandeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.lang.Object;
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
    @GetMapping("/huilleHouloul/pages/page2/success")
    public String showRegistrationForm2(){
        return "" +
                "huille/pageProduit2" ;
    }
    @GetMapping("/AntiVarice/pages/page/success")
    public String showRegistrationFormA(){
        return "Anti/AntiVariceSucces" ;
    }

    @GetMapping("/commandes")
    public String showOrders(Model model) {
        List<Commande> orders = this.commandeService.lire();
        // Décaler les dates d'une heure dans la liste des commandes
        List<Commande> shiftedOrders = orders.stream()
                .map(commande -> {
                    commande.setDateCommande(Date.from(commande.getDateCommande().toInstant().plusSeconds(3600)));
                    return commande;
                })
                .collect(Collectors.toList());
        // Reverse the list of orders
        Collections.reverse(shiftedOrders);
        model.addAttribute("orders", shiftedOrders);
        return "orders";
    }


    @GetMapping("/confirmedOrders")
    public String showConfirmedOrders(Model model){
        List<Commande> orders= this.commandeService.lireConfirmed();
        // Reverse the list of confirmed orders
        Collections.reverse(orders);
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
        // Calcul des statistiques par délégué et par LastModifiedBy
        Map<String, Long> statisticsByDelegate = commandeService.calculerStatistiquesParDelegue();
        Map<String, Long> statisticsByLastModifiedBy = commandeService.calculerStatistiquesParLastModifiedBy();

        // Fusion des deux maps en une seule
        Map<String, Object[]> combinedStatistics = new LinkedHashMap<>();
        statisticsByDelegate.forEach((delegate, numberOfOrders) ->
                combinedStatistics.put(delegate, new Object[]{numberOfOrders, statisticsByLastModifiedBy.getOrDefault(delegate, 0L)})
        );

        // Ajout de la map combinée au modèle
        model.addAttribute("combinedStatistics", combinedStatistics);

        // Calcul des autres statistiques
        long nombreTotalCommandes = commandeService.calculerNombreTotalCommandes();
        List<Commande> confirmedOrders = commandeService.lireCommandesConfirme();
        long nombreCommandesConfirmees = confirmedOrders.size();

        // Création de l'objet StatisticsModel et définition des données
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.setNombreTotalCommandes(nombreTotalCommandes);
        statisticsModel.setConfirmedOrders(confirmedOrders);
        statisticsModel.setNombreCommandesConfirmees(nombreCommandesConfirmees);

        // Ajout de l'objet StatisticsModel au modèle
        model.addAttribute("statistics", statisticsModel);

        // Retour du nom de la vue Thymeleaf
        return "statistics";
    }



    /*
    @GetMapping("/countDistinctCommandsByDateAndRefLandPage")
    public String countDistinctCommandsByDateAndRefLandPage(Model model) {
        List<Object[]> data = commandeService.countDistinctCommandsByDateAndRefLandPage();
        // Tri des données par date dans l'ordre inverse
        data.sort(Comparator.comparing(row -> (Date) ((Object[]) row)[0]).reversed());
        // Regroupement des données par date avec tri à l'intérieur de chaque groupe
        Map<Date, List<Object[]>> groupedData = data.stream()
                .collect(Collectors.groupingBy(row -> (Date) ((Object[]) row)[0],
                        LinkedHashMap::new,
                        Collectors.toList()));
        // Tri à l'intérieur de chaque groupe par date dans l'ordre inverse
        groupedData.forEach((date, group) -> group.sort(Comparator.comparing(row -> (Date) ((Object[]) row)[0]).reversed()));
        model.addAttribute("groupedData", groupedData);
        System.out.println(groupedData);
        groupedData.forEach((date, group) -> {
            System.out.println("Date: " + date);
            long sum = 0; // Utiliser long pour stocker la somme
            for (Object[] row : group) {
                long value = (long) row[2]; // Convertir en long
                sum += value; // Ajouter la valeur à la somme
                System.out.println("Valeurs: " + Arrays.toString(row));
            }
            System.out.println("Somme pour " + date + ": " + sum); // Afficher la somme pour ce jour
        });




        return "countDistinctCommandsByDateAndRefLandPage";
    }



     */


    @GetMapping("/countDistinctCommandsByDateAndRefLandPage")
    public String countDistinctCommandsByDateAndRefLandPage(Model model) {
        List<Object[]> data = commandeService.countDistinctCommandsByDateAndRefLandPage();
        // Tri des données par date dans l'ordre inverse
        data.sort(Comparator.comparing(row -> (Date) ((Object[]) row)[0]).reversed());
        // Regroupement des données par date avec tri à l'intérieur de chaque groupe
        Map<Date, List<Object[]>> groupedData = data.stream()
                .collect(Collectors.groupingBy(row -> (Date) ((Object[]) row)[0],
                        LinkedHashMap::new,
                        Collectors.toList()));
        // Tri à l'intérieur de chaque groupe par date dans l'ordre inverse
        groupedData.forEach((date, group) -> group.sort(Comparator.comparing(row -> (Date) ((Object[]) row)[0]).reversed()));

        // Calculer et stocker la somme pour chaque jour dans une map
        Map<Date, Long> sums = new LinkedHashMap<>();
        groupedData.forEach((date, group) -> {
            long sum = 0;
            for (Object[] row : group) {
                long value = (long) row[2];
                sum += value;
            }
            sums.put(date, sum); // Ajouter la somme à la map
        });

        // Ajouter groupedData et sums au modèle
        model.addAttribute("groupedData", groupedData);
        model.addAttribute("sums", sums); // Ajouter la map de sommes au modèle

        return "countDistinctCommandsByDateAndRefLandPage";
    }




    @GetMapping("/choixParametre")
    public String showParametreSelectionPage() {
        return "choixParametre";
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



    @PostMapping("/updateOrderStatus/{orderId}")
    public String updateOrderStatus(@PathVariable Long orderId, @RequestParam String etat, Principal principal, HttpServletRequest request) {
        String username = principal.getName();
        // Utilisez le nom d'utilisateur pour identifier l'utilisateur qui a effectué l'action
        commandeService.mettreAjourStatusCommande(orderId, etat, username);
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }

    @GetMapping("/onlyConfirmedOrders")
    public String showOnlyConfirmedOrders(Model model){
        List<Commande> orders= this.commandeService.lireCommandesConfirme();
        // Reverse the list of confirmed orders
        Collections.reverse(orders);
        model.addAttribute("onlyConfirmedOrders", orders);
        return "onlyConfirmedOrders";
    }







}
