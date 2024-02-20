package com.example.commerce.controller;

import com.example.commerce.service.CommandeService;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@ComponentScan
@RequestMapping("/admin/arvea_creme2")
public class Arveacreme2Controller {
    private CommandeService commandeService;

    public Arveacreme2Controller(CommandeService commandeService) {
        super();
        this.commandeService = commandeService;
    }

    @ModelAttribute("commande")
    public CommandeRegistrationDto commandeRegistrationDto(){
        return new CommandeRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm2(){
        return "arveacreme2";
    }

    @GetMapping("/success")
    public String showRegistrationForm1(){
        return "pageProduit";
    }





    @PostMapping
    public String registrationCommandeAccount(@ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto ){

        commandeService.save(commandeRegistrationDto, commandeRegistrationDto.getRefLandP(),"huille romatisme");
        return "pageProduit";
    }
}

