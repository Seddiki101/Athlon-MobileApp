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
public class Conge {

    private int id;

    private String dateD;

    private String dateF;

    private String type;
    private int idEmployee;

    public Conge() {
    }

    public Conge(String dateD, String dateF, String type) {
        this.dateD = dateD;
        this.dateF = dateF;
        this.type = type;
    }

    public Conge(int id, String dateD, String dateF, String type) {
        this.id = id;
        this.dateD = dateD;
        this.dateF = dateF;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateF() {
        return dateF;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Override
    public String toString() {
        return "Conge{" + "id=" + id + ", $dateD=" + dateD + ", dateF=" + dateF + ", type=" + type + '}';
    }

}
