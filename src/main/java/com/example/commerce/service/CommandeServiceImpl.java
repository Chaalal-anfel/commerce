package com.example.commerce.service;

import com.example.commerce.modele.Commande;
import com.example.commerce.repository.CommandeRepository;
import com.example.commerce.web.dto.CommandeRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                commandeRegistrationDto.getAffectedTo(),
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

    @Override
    public long calculerNombreTotalCommandesConfirmes() {
        return commandeRepository.findByStatus("confirmé").size();
    }


    @Override
    public List<Commande> getCommandesByIdRange(Long firstId, Long lastId) {
        return commandeRepository.findCommandesByIdRange(firstId, lastId);
    }

    @Override
    public void updateAffectedTo(List<Long> idCmds, String delegue) {
        // Appeler la méthode du repository pour mettre à jour affectedTo
        commandeRepository.updateAffectedToByIds(idCmds, delegue);
    }

    @Override
    public List<Commande> getCommandesForCurrentUser() {
        // Récupérer les informations sur l'utilisateur connecté depuis le contexte de sécurité
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        String status = "non traitée";
        // Utiliser le nom d'utilisateur pour filtrer les commandes
        return commandeRepository.findByAffectedTo(username);
    }

    /*
    @Override
    public Map<String, Long> calculerStatistiquesParDelegue() {
        List<Object[]> results = commandeRepository.countCommandesByAffectedTo();
        Map<String, Long> statistiques = new HashMap<>();
        for (Object[] result : results) {
            statistiques.put((String) result[0], (Long) result[1]);
        }
        return statistiques;
    }


    @Override
    public Map<String, Long> calculerStatistiquesParDelegue() {
        List<Commande> commandes = commandeRepository.findAll();
        return commandes.stream()
                .filter(commande -> commande.getAffectedTo() != null)
                .collect(Collectors.groupingBy(Commande::getAffectedTo, Collectors.counting()));
    }

    @Override
    public Map<String, Long> calculerStatistiquesParModifieur() {
        List<Commande> commandes = commandeRepository.findAll();
        Map<String, Long> statistiquesParModifieur = new HashMap<>();
        for (Commande commande : commandes) {
            String modifieur = (commande.getLastModifiedBy() != null) ? commande.getLastModifiedBy() : "0";
            statistiquesParModifieur.put(modifieur, statistiquesParModifieur.getOrDefault(modifieur, 0L) + 1);
        }
        return statistiquesParModifieur;
    }

     */
    @Override
    public Map<String, Long> calculerStatistiquesParDelegue() {
        List<Object[]> result = commandeRepository.countOrdersByAffectedTo();
        return convertResultToMap(result);
    }

    @Override
    public Map<String, Long> calculerStatistiquesParLastModifiedBy() {
        List<Object[]> result = commandeRepository.countOrdersByLastModifiedBy();
        return convertResultToMap(result);
    }

    // Méthode pour convertir le résultat en une map
    private Map<String, Long> convertResultToMap(List<Object[]> result) {
        Map<String, Long> statistics = new HashMap<>();
        for (Object[] row : result) {
            String key = (String) row[0];
            Long value = (Long) row[1];
            statistics.put(key, value);
        }
        return statistics;
    }

    @Override
    public List<Object[]> countDistinctCommandsByDateAndRefLandPage() {
        return commandeRepository.countDistinctCommandsByDateAndRefLandPage();
    }
}
