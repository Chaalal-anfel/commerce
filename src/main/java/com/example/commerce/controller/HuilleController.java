package com.example.commerce.controller;



import com.example.commerce.modele.Commande;
import com.example.commerce.modele.StatisticsModel;
import com.example.commerce.repository.CommandeRepository;
import com.example.commerce.service.CommandeService;
import com.example.commerce.service.CommandeServiceImpl;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@ComponentScan
@RequestMapping("/huilleHouloul")
public class HuilleController {
    private CommandeService commandeService;

    public HuilleController(CommandeService commandeService) {
        super();
        this.commandeService = commandeService;
    }

    @ModelAttribute("commande")
    public CommandeRegistrationDto commandeRegistrationDto(){
        return new CommandeRegistrationDto();
    }

    @GetMapping("/pages/{refLandP}")
    public String showRegistrationForm(@PathVariable String refLandP, Model model) {
        model.addAttribute("refLandP", refLandP);
        return "huille/huilleHouloul";  // Assurez-vous que le nom du répertoire est correct ici
    }
    @PostMapping("/pages/{refLandP}")
    public String handlePostRequest(@PathVariable String refLandP, @ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto) {
        // Traitez ici la logique de gestion de la requête POST
        System.out.println(commandeRegistrationDto.toString());
        commandeService.save(commandeRegistrationDto, commandeRegistrationDto.getRefLandP(),"huille romatisme");
        return "redirect:/huilleHouloul/pages/page/success";
    }

    @GetMapping("/success")
    public String showRegistrationForm1(){
        return "huille/pageProduit";
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
        return "redirect:/huilleHouloul/commandes"; // Redirect back to the orders page
    }


    /*
    @PostMapping("/pages/{refLandP}")
    public String handlePostRequest(@PathVariable String refLandP, @ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto) {
        // Traitez ici la logique de gestion de la requête POST
        System.out.println(commandeRegistrationDto.toString());
        commandeService.save(commandeRegistrationDto);
        return "pageProduit";
    }*/
    /*
    @PostMapping("/pages/{refLandP}")
    public String handlePostRequest(@ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto) {
        // Traitez ici la logique de gestion de la requête POST
        System.out.println(commandeRegistrationDto.toString());
        commandeService.save(commandeRegistrationDto);
        return "pageProduit";
    }*/

    @GetMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        commandeService.supprimer(orderId); // Implement this method in your service
        return "redirect:/huilleHouloul/commandes"; // Redirect back to the orders page
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
    public String generateURL(@RequestParam String refLandP) {
        // Traitement des paramètres et génération de l'URL en fonction du choix du client
        // Vous pouvez utiliser refLandP comme vous le souhaitez

        // Exemple : Redirection vers la page dynamique avec le paramètre
        return "redirect:/huilleHouloul/pages/page?refLandP=" + refLandP;
    }
    /*
    @GetMapping
    public String showRegistrationForm2(){
        return "huilleHouloul";
    }*/

    /*
    @PostMapping
    public String registrationCommandeAccount(@ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto ){

        commandeService.save(commandeRegistrationDto);
        return "pageProduit";
    }*/


    /*
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        // Récupérez la liste des commandes à exporter
        List<Commande> orders = this.commandeService.lire();

        // Créez un classeur Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Commandes");

        // Ajoutez les en-têtes à la ligne d'en-têtes
        Row headerRow = sheet.createRow(0);
        String[] headers = {"رقم الطلبية", "صاحب الطلب", "الولاية", "رقم الهاتف", "تاريخ الطلب", "اسم المنتوج"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Remplissez les données dans les lignes suivantes
        int rowNum = 1;
        for (Commande commande : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(commande.getIdCmd());
            row.createCell(1).setCellValue(commande.getName());
            row.createCell(2).setCellValue(commande.getWilaya());
            row.createCell(3).setCellValue(commande.getNumero());
            row.createCell(4).setCellValue(commande.getDateCommande().toString());
            row.createCell(5).setCellValue(commande.getRefLandPage());
            // Ajoutez d'autres colonnes selon votre modèle Commande
        }

        // Paramètres de l'en-tête pour forcer le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=commands.xlsx");

        // Écrivez le contenu du classeur dans la réponse
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    */

}

