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
public class Cour {

    private int id;
    private String nom;

    private String description_cours;

    private String Niveau_cours;

    private int duree_cours;

    private int capacity;

    private String image_cours;

    public Cour() {
    }

    public Cour(String description_cours, String Niveau_cours, int duree_cours, int capacity, String image_cours, String nom) {
        this.description_cours = description_cours;
        this.Niveau_cours = Niveau_cours;
        this.duree_cours = duree_cours;
        this.capacity = capacity;
        this.image_cours = image_cours;
        this.nom = nom;
    }

    public Cour(int id, String description_cours, String Niveau_cours, int duree_cours, int capacity, String image_cours, String nom) {
        this.id = id;
        this.description_cours = description_cours;
        this.Niveau_cours = Niveau_cours;
        this.duree_cours = duree_cours;
        this.capacity = capacity;
        this.image_cours = image_cours;
        this.nom = nom;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription_cours() {
        return description_cours;
    }

    public void setDescription_cours(String description_cours) {
        this.description_cours = description_cours;
    }

    public String getNiveau_cours() {
        return Niveau_cours;
    }

    public void setNiveau_cours(String Niveau_cours) {
        this.Niveau_cours = Niveau_cours;
    }

    public int getDuree_cours() {
        return duree_cours;
    }

    public void setDuree_cours(int duree_cours) {
        this.duree_cours = duree_cours;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getImage_cours() {
        return image_cours;
    }

    public void setImage_cours(String image_cours) {
        this.image_cours = image_cours;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return description_cours;
    }

}
