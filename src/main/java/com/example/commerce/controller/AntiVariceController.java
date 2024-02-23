package com.example.commerce.controller;

import com.example.commerce.service.CommandeService;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



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

    @PostMapping("/generate")
    public String generateURL(@RequestParam String refLandPA) {
        // Traitement des paramètres et génération de l'URL en fonction du choix du client
        // Vous pouvez utiliser refLandP comme vous le souhaitez

        // Exemple : Redirection vers la page dynamique avec le paramètre
        return "redirect:/AntiVarice/pages/page?refLandPA=" + refLandPA;
    }

}
