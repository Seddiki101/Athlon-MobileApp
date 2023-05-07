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
public class Article {

    private int id;
    private String titre;
    private String auteur;
    private String descripton;
    private String imgArticle;
    private Sujet Sujet;
    private int like;
    private int dislike;

    public Article() {
    }

    public Article(String titre, String auteur, String descripton, String imgArticle) {
        this.titre = titre;
        this.auteur = auteur;
        this.descripton = descripton;
        this.imgArticle = imgArticle;
    }

    public Article(int id, String titre, String auteur, String descripton, String imgArticle) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.descripton = descripton;
        this.imgArticle = imgArticle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public String getImgArticle() {
        return imgArticle;
    }

    public void setImgArticle(String imgArticle) {
        this.imgArticle = imgArticle;
    }

    public Sujet getSujet() {
        return Sujet;
    }

    public void setSujet(Sujet Sujet) {
        this.Sujet = Sujet;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", titre=" + titre + ", auteur=" + auteur + ", descripton=" + descripton + ", imgArticle=" + imgArticle + ", Sujet=" + Sujet + '}';
    }

}
