package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.repository.CommandeRepository;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeServiceImpl implements CommandeService{

    private final CommandeRepository commandeRepository;


    @Autowired
    public CommandeServiceImpl(CommandeRepository commandeRepository){
        this.commandeRepository = commandeRepository;
    }

    /*
    @Override
    public Commande save(CommandeRegistrationDto commandeRegistrationDto) {

        Commande commande = new Commande(null,
                        commandeRegistrationDto.getName(),
                        commandeRegistrationDto.getWilaya(),
                        commandeRegistrationDto.getCommune(),
                        commandeRegistrationDto.getNumero(),
                        commandeRegistrationDto.getDate(),
                        commandeRegistrationDto.getRefLandP(),
                        commandeRegistrationDto.getStatus(),
                        commandeRegistrationDto.getNomProduit()
                );
        return commandeRepository.save(commande);
    }
    */
    @Override
    public Commande save(CommandeRegistrationDto commandeRegistrationDto, String refLandP,String situation) {
        String nomProduit = "huille romatisme".equals(situation) ? "huille romatisme" : "huille AntiVarice";

        Commande commande = new Commande(
                null,
                commandeRegistrationDto.getName(),
                commandeRegistrationDto.getWilaya(),
                commandeRegistrationDto.getCommune(),
                commandeRegistrationDto.getNumero(),
                commandeRegistrationDto.getDate(),
                refLandP,
                commandeRegistrationDto.getStatus(),
                situation
        );
        return commandeRepository.save(commande);
    }
    @Override
    public Commande saveA(CommandeRegistrationDto commandeRegistrationDto) {
        Commande commande = new Commande(null,
                commandeRegistrationDto.getName(),
                commandeRegistrationDto.getWilaya(),
                commandeRegistrationDto.getCommune(),
                commandeRegistrationDto.getNumero(),
                commandeRegistrationDto.getDate(),
                commandeRegistrationDto.getRefLandPA(),
                commandeRegistrationDto.getStatus(),
                commandeRegistrationDto.getNomProduit()
        );
        return commandeRepository.save(commande);
    }

    @Override
    public List<Commande> lire() {
        return commandeRepository.findByStatus("non confirmé");
    }

    @Override
    public List<Commande> lireAll() {
        return commandeRepository.findAll();
    }


    @Override
    public List<Commande> lireConfirmed() {
        return commandeRepository.findByStatus("confirmé");
    }


    @Override
    public Commande getById(Long id) {
        Commande cmd = commandeRepository.getReferenceById(id);

        cmd.setStatus("confirmé");

        commandeRepository.save(cmd);
        return cmd ;

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

    @Override
    public void confirmOrder(Long orderId) {
        Commande cmd = commandeRepository.getReferenceById(orderId);

        cmd.setStatus("confirmé");

        commandeRepository.save(cmd);
        //  commandeRepository.updateStatusById(orderId, "confirmed");
    }

    @Override
    public void deleteOrder(Long orderId) {
        commandeRepository.deleteById(orderId);
    }

    @Override
    public long calculerNombreTotalCommandes() {
        return commandeRepository.count();
    }
}
