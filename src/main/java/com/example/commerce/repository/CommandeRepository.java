package com.example.commerce.repository;

import com.example.commerce.modele.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande,Long> {

}
