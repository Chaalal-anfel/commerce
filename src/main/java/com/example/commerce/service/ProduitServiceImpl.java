package com.example.commerce.service;

import com.example.commerce.modele.Produit;
import com.example.commerce.repository.ProduitRepository;
import com.example.commerce.web.dto.ProduitRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProduitServiceImpl implements ProduitService{


    private final ProduitRepository produitRepository;


    @Override
    public Produit save(ProduitRegistrationDto produitRegistrationDto) {

        Produit produit = new Produit(null,
                produitRegistrationDto.getNomProduit(),
                produitRegistrationDto.getDescProduit(),
                produitRegistrationDto.getUrl(),
                produitRegistrationDto.getPrixProduit(),
                produitRegistrationDto.getQuantity());
        return produitRepository.save(produit);
    }

}
