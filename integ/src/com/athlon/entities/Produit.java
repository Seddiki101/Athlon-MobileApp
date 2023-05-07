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
public class Produit {

    private int id;
    private String brand;
    private int qantite;
    private String description;
    private float prix;
    private String image;
    private String nom;

    public Produit() {
    }

    public Produit(String brand, String description, float prix, String image, String nom) {
        this.brand = brand;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.nom = nom;
    }

    public Produit(int id, String brand, String description, float prix, String image, String nom) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQantite() {
        return qantite;
    }

    public void setQantite(int qantite) {
        this.qantite = qantite;
    }

}
