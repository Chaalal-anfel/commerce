package com.example.commerce.controller;



import com.example.commerce.service.CommandeService;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@ComponentScan
@RequestMapping("/arvea_creme1")
public class Arveacreme1Controller {
    private CommandeService commandeService;

    public Arveacreme1Controller(CommandeService commandeService) {
        super();
        this.commandeService = commandeService;
    }

    @ModelAttribute("commande")
    public CommandeRegistrationDto commandeRegistrationDto(){
        return new CommandeRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "arveacreme1";
    }

    @GetMapping("/success")
    public String showRegistrationForm1(){
        return "pageProduit";
    }





    @PostMapping
    public String registrationCommandeAccount(@ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto ){

        commandeService.save(commandeRegistrationDto);
        return "pageProduit";
    }
}

