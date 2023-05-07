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
public class Employee {

    private int id;
    private int cin;
    private String nom;
    private String prenom;
    private String img_employe;
    private String etat;
    private float salaire;

    public Employee() {
    }

    public Employee(int cin, String nom, String prenom, String img_employe, String etat, float salaire) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.img_employe = img_employe;
        this.etat = etat;
        this.salaire = salaire;
    }

    public Employee(int id, int cin, String nom, String prenom, String img_employe, String etat, float salaire) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.img_employe = img_employe;
        this.etat = etat;
        this.salaire = salaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getImg_employe() {
        return img_employe;
    }

    public void setImg_employe(String img_employe) {
        this.img_employe = img_employe;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return nom;
    }

}
