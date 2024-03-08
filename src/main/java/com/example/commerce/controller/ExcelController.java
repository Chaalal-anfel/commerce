package com.example.commerce.controller;

import com.example.commerce.modele.Commande;
import com.example.commerce.service.CommandeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@ComponentScan
@RequestMapping
public class ExcelController {
    private CommandeService commandeService;

    public ExcelController(CommandeService commandeService) {
        super();
        this.commandeService = commandeService;
    }


    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        // Récupérez la liste des commandes à exporter
        List<Commande> orders = this.commandeService.lireAll();

        // Créez un classeur Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Commandes");

        // Ajoutez les en-têtes à la ligne d'en-têtes
        Row headerRow = sheet.createRow(0);
        String[] headers = {"numéro cmd", "client", "wilaya","commune", "numero téléphone", "date cmd", "nom produit","url","status Cmd","traité par"};
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
            row.createCell(3).setCellValue(commande.getCommune());
            row.createCell(4).setCellValue(commande.getNumero());
            row.createCell(5).setCellValue(commande.getDateCommande().toString());
            row.createCell(6).setCellValue(commande.getNomProduit());
            row.createCell(7).setCellValue(commande.getRefLandPage());
            row.createCell(8).setCellValue(commande.getStatus());
            row.createCell(9).setCellValue(commande.getLastModifiedBy());

            // Ajoutez d'autres colonnes selon votre modèle Commande
        }

        // Paramètres de l'en-tête pour forcer le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=commands.xlsx");

        // Écrivez le contenu du classeur dans la réponse
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
