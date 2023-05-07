/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.entities;

/**
 *
 * @author Houssem Charef
 */
public class Sujet {

    private int id;

    private String nom;

    private String description;

    private String imgSujet;

    public Sujet() {
    }

    public Sujet(String nom, String description, String imgSujet) {
        this.nom = nom;
        this.description = description;
        this.imgSujet = imgSujet;
    }

    public Sujet(int id, String nom, String description, String imgSujet) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.imgSujet = imgSujet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSujet() {
        return imgSujet;
    }

    public void setImgSujet(String imgSujet) {
        this.imgSujet = imgSujet;
    }

    @Override
    public String toString() {
        return nom;
    }

}
