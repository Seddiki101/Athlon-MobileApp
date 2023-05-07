/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author k
 */
public class uzer {
 
    private int id;
    private String email;
    private String password;
    
    private String nom;
    private String prenom;
    private Date dateins;

    public int getId() {
        return id;
    }

    // not useful
    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public Date getDateins() {
        return dateins;
    }

    public void setDateins(Date dateins) {
        this.dateins = dateins;
    }
    
    
    
    
    
    
    
    
}
