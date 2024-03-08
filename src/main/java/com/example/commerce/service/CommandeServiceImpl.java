package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.repository.CommandeRepository;
import com.example.commerce.web.dto.CommandeRegistrationDto;
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
                situation,
                commandeRegistrationDto.getLastModifyBy()
        );
        return commandeRepository.save(commande);
    }


    @Override
    public List<Commande> lire() {
        return commandeRepository.findByStatus("non traitée");
    }

    @Override
    public List<Commande> lireAll() {
        return commandeRepository.findAll();
    }

    @Override
    public  List<Commande> lireCommandesConfirme(){
        return commandeRepository.findByStatus("confirmé");
    }

    @Override
    public List<Commande> lireConfirmed() {
        return commandeRepository.findAllByStatusNot("Non traitée","confirmé");
    }


    @Override
    public Commande getById(Long id) {
        Commande cmd = commandeRepository.getReferenceById(id);

        cmd.setStatus("confirmé");

        commandeRepository.save(cmd);
        return cmd ;

    }



    /*
    @Override
    public Commande mettreAjourStatusCommande(Long id, String nouveauStatus) {
        return commandeRepository.findById(id)
                .map(c -> {
                    c.setStatus(nouveauStatus);
                    return commandeRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Commande non trouvée !"));
    }

*/

    @Override
    public Commande mettreAjourStatusCommande(Long id, String nouveauStatus, String username) {
        return commandeRepository.findById(id)
                .map(c -> {
                    // Vous pouvez maintenant utiliser le nom d'utilisateur comme vous le souhaitez, par exemple, le stocker dans la commande mise à jour
                    c.setStatus(nouveauStatus);
                    c.setLastModifiedBy(username);
                    return commandeRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Commande non trouvée !"));
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
