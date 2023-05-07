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
public class Exercice {

    private int id;

    private String nom;

    private int duree_exercices;

    private int nombre_repetitions;

    private String desc_exercices;

    private String machine;

    private Cour cour = new Cour();
    private String image_exercice;

    public Exercice() {
    }

    public Exercice(String nom, int duree_exercices, int nombre_repetitions, String desc_exercices, String machine, String image_exercice) {
        this.nom = nom;
        this.duree_exercices = duree_exercices;
        this.nombre_repetitions = nombre_repetitions;
        this.desc_exercices = desc_exercices;
        this.machine = machine;
        this.image_exercice = image_exercice;
    }

    public Exercice(int id, String nom, int duree_exercices, int nombre_repetitions, String desc_exercices, String machine, String image_exercice) {
        this.id = id;
        this.nom = nom;
        this.duree_exercices = duree_exercices;
        this.nombre_repetitions = nombre_repetitions;
        this.desc_exercices = desc_exercices;
        this.machine = machine;
        this.image_exercice = image_exercice;
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

    public int getDuree_exercices() {
        return duree_exercices;
    }

    public void setDuree_exercices(int duree_exercices) {
        this.duree_exercices = duree_exercices;
    }

    public int getNombre_repetitions() {
        return nombre_repetitions;
    }

    public void setNombre_repetitions(int nombre_repetitions) {
        this.nombre_repetitions = nombre_repetitions;
    }

    public String getDesc_exercices() {
        return desc_exercices;
    }

    public void setDesc_exercices(String desc_exercices) {
        this.desc_exercices = desc_exercices;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getImage_exercice() {
        return image_exercice;
    }

    public void setImage_exercice(String image_exercice) {
        this.image_exercice = image_exercice;
    }

    public Cour getCour() {
        return cour;
    }

    public void setCour(Cour cour) {
        this.cour = cour;
    }

    @Override
    public String toString() {
        return "Exercice{" + "id=" + id + ", nom=" + nom + ", duree_exercices=" + duree_exercices + ", nombre_repetitions=" + nombre_repetitions + ", desc_exercices=" + desc_exercices + ", machine=" + machine + ", image_exercice=" + image_exercice + '}';
    }

}
