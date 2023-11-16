package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.repository.CommandeRepository;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService{

    private final CommandeRepository commandeRepository;


    @Override
    public Commande save(CommandeRegistrationDto commandeRegistrationDto) {

        Commande commande = new Commande(null,
                        commandeRegistrationDto.getName(),
                        commandeRegistrationDto.getWilaya(),
                        commandeRegistrationDto.getCommune(),
                        commandeRegistrationDto.getNumero(),
                        commandeRegistrationDto.getDate(),
                        commandeRegistrationDto.getRefLandP());
        return commandeRepository.save(commande);
    }

    @Override
    public List<Commande> lire() {
        return commandeRepository.findAll();
    }

    @Override
    public Commande modifier(Long id, Commande commande) {
        return commandeRepository.findById(id)
                .map(c-> {
                    c.setNumero(commande.getNumero());
                    c.setName(commande.getName());
                    c.setWilaya(commande.getWilaya());
                    return commandeRepository.save(c);
                }).orElseThrow(()-> new RuntimeException("Commande non trouvé !"));
    }

    @Override
    public String supprimer(Long id) {
        commandeRepository.deleteById(id);
        return "commande supprimé ! ";
    }
}
