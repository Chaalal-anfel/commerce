package com.example.commerce.repository;

import com.example.commerce.modele.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Date;


public interface CommandeRepository extends JpaRepository<Commande,Long> {

    List<Commande> findByStatus(String status);

    @Query("SELECT c FROM Commande c WHERE c.status <> :valeurStatus1 AND c.status <> :valeurStatus2")
    List<Commande> findAllByStatusNot(String valeurStatus1, String valeurStatus2);

    @Query("UPDATE Commande c SET c.status = :status WHERE c.idCmd = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    // Méthode pour récupérer une liste de commandes par plage d'ID
    @Query("SELECT c FROM Commande c WHERE c.idCmd BETWEEN :firstId AND :lastId")
    List<Commande> findCommandesByIdRange(@Param("firstId") Long firstId, @Param("lastId") Long lastId);

    // Requête JPQL pour mettre à jour le champ affectedTo pour les IDs spécifiés
    @Modifying
    @Transactional
    @Query("UPDATE Commande c SET c.affectedTo = :delegue WHERE c.idCmd IN :idCmds")
    void updateAffectedToByIds(@Param("idCmds") List<Long> idCmds, @Param("delegue") String delegue);

    List<Commande> findByAffectedToAndStatus(String affectedTo, String status);
    List<Commande> findByAffectedTo(String affectedTo);

    @Query("SELECT c.affectedTo, COUNT(c) FROM Commande c GROUP BY c.affectedTo")
    List<Object[]> countCommandesByAffectedTo();

    @Query("SELECT c.affectedTo, COUNT(c) FROM Commande c WHERE c.affectedTo IS NOT NULL GROUP BY c.affectedTo")
    List<Object[]> countOrdersByAffectedTo();

    @Query("SELECT c.lastModifiedBy, COUNT(c) FROM Commande c WHERE c.lastModifiedBy IS NOT NULL GROUP BY c.lastModifiedBy")
    List<Object[]> countOrdersByLastModifiedBy();


    @Query("SELECT FUNCTION('DATE', c.dateCommande) AS jour, c.refLandPage, COUNT(DISTINCT c.idCmd) AS nombre_commandes FROM Commande c GROUP BY FUNCTION('DATE', c.dateCommande), c.refLandPage ORDER BY FUNCTION('DATE', c.dateCommande) , c.refLandPage")
    List<Object[]> countDistinctCommandsByDateAndRefLandPage();

}