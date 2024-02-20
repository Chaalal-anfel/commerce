package com.example.commerce.modele;


import java.util.List;

public class StatisticsModel {
    private long nombreTotalCommandes;
    private List<Commande> confirmedOrders;
    private long nombreCommandesConfirmees;

    // Ajoutez les getters et setters

    public long getNombreTotalCommandes() {
        return nombreTotalCommandes;
    }

    public List<Commande> getConfirmedOrders() {
        return confirmedOrders;
    }

    public void setConfirmedOrders(List<Commande> confirmedOrders) {
        this.confirmedOrders = confirmedOrders;
    }

    public void setNombreTotalCommandes(long nombreTotalCommandes) {
        this.nombreTotalCommandes = nombreTotalCommandes;
    }

    public long getNombreCommandesConfirmees() {
        return nombreCommandesConfirmees;
    }

    public void setNombreCommandesConfirmees(long nombreCommandesConfirmees) {
        this.nombreCommandesConfirmees = nombreCommandesConfirmees;
    }
}