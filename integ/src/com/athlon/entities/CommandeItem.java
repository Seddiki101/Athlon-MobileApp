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
public class CommandeItem {

    private int id;
    private int qantity;
    private String ProduitBrand;

    public CommandeItem() {
    }

    public CommandeItem(String ProduitBrand, int qantity) {
        this.ProduitBrand = ProduitBrand;
        this.qantity = qantity;
    }

    public CommandeItem(int id, String ProduitBrand, int qantity) {
        this.id = id;
        this.ProduitBrand = ProduitBrand;
        this.qantity = qantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQantity() {
        return qantity;
    }

    public void setQantity(int qantity) {
        this.qantity = qantity;
    }

    public String getProduitBrand() {
        return ProduitBrand;
    }

    public void setProduitBrand(String ProduitBrand) {
        this.ProduitBrand = ProduitBrand;
    }

    @Override
    public String toString() {
        return "CommandeItem{" + "id=" + id + ", qantity=" + qantity + ", ProduitBrand=" + ProduitBrand + '}';
    }

}
