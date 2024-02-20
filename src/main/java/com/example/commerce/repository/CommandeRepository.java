package com.example.commerce.repository;

import com.example.commerce.modele.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande,Long> {


    List<Commande> findByStatus(String status);


    @Query("UPDATE Commande c SET c.status = :status WHERE c.idCmd = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);
}
