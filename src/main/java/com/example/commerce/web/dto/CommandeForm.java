package com.example.commerce.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommandeForm {
    private String affectedTo;
    private Long firstId;
    private Long range;

    // Ajoutez les getters et setters
}