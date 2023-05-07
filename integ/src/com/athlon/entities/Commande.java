/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.entities;

import java.util.Date;

/**
 *
 * @author Houssem Charef
 */
public class Commande {

    private int idC;
    private String date;
    private int idUser;
    private String statut;
    private boolean livraison;

    public Commande(int idC, String date, int idUser, String statut) {
        this.idC = idC;
        this.date = date;
        this.idUser = idUser;
        this.statut = statut;
    }

    public Commande(String date, int idUser, String statut) {
        this.date = date;
        this.idUser = idUser;
        this.statut = statut;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public boolean isLivraison() {
        return livraison;
    }

    public void setLivraison(boolean livraison) {
        this.livraison = livraison;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Commande{" + "idC=" + idC + ", date=" + date + ", idUser=" + idUser + ", statut=" + statut + '}';
    }

}
