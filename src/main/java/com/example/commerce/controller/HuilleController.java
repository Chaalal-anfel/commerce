package com.example.commerce.controller;



import com.example.commerce.service.CommandeService;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
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

    @GetMapping
    public String showRegistrationForm(){
        return "huileHouloul";
    }

    @GetMapping("/success")
    public String showRegistrationForm1(){
        return "pageProduit";
    }





    @PostMapping
    public String registrationCommandeAccount(@ModelAttribute("commande") CommandeRegistrationDto commandeRegistrationDto ){
        System.out.println(commandeRegistrationDto.toString());
        commandeService.save(commandeRegistrationDto);
        return "pageProduit";
    }
}

