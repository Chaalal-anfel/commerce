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

    @GetMapping("/pages/page2/{refLandP}")
    public String showRegistrationForm2(@PathVariable String refLandP, Model model) {
        model.addAttribute("refLandP", refLandP);
        return "huille/huilleHouloul2";  // Assurez-vous que le nom du répertoire est correct ici
    }

    @PostMapping("/pages/{refLandP}")
    public String handlePostRequest(@PathVariable String refLandP, @ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto) {
        // Traitez ici la logique de gestion de la requête POST
        System.out.println(commandeRegistrationDto.toString());
        commandeService.save(commandeRegistrationDto, commandeRegistrationDto.getRefLandP(),"huille romatisme");
        return "redirect:/huilleHouloul/pages/page/success";
    }


    @PostMapping("/pages/page2/{refLandP}")
    public String handlePostRequest2(@PathVariable String refLandP, @ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto) {
        // Traitez ici la logique de gestion de la requête POST
        System.out.println(commandeRegistrationDto.toString());
        commandeService.save(commandeRegistrationDto, commandeRegistrationDto.getRefLandP(),"huille romatisme");
        return "redirect:/huilleHouloul/pages/page2/success";
    }


    @PostMapping("/generate")
    public String generateURL(@RequestParam String refLandP) {
        // Traitement des paramètres et génération de l'URL en fonction du choix du client
        // Vous pouvez utiliser refLandP comme vous le souhaitez

        // Exemple : Redirection vers la page dynamique avec le paramètre
        return "redirect:/huilleHouloul/pages/page?refLandP=" + refLandP;
    }

    @PostMapping("/generate2/{generateId}")
    public String generateURL2(@RequestParam String refLandP, @RequestParam String generateId) {
        // Traitement des paramètres et génération de l'URL en fonction du choix du client
        // Vous pouvez utiliser refLandP comme vous le souhaitez

        // Exemple : Redirection vers la page dynamique avec le paramètre
        if (generateId.equals("1")) {
            return "redirect:/huilleHouloul/pages/page?refLandP=" + refLandP;
        } else if (generateId.equals("2")) {
            return "redirect:/huilleHouloul/pages/page2/page?refLandP=" + refLandP;
        } else {
            // Gérer les autres cas si nécessaire
            return "rien"; // Redirection vers une page par défaut
        }
    }

}

