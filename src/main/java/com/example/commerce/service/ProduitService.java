package com.example.commerce.service;

import com.example.commerce.modele.Produit;
import com.example.commerce.web.dto.ProduitRegistrationDto;

public interface  ProduitService {
    Produit save(ProduitRegistrationDto produitRegistrationDto);
}
