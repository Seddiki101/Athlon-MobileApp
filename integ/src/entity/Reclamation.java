/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author k
 */
public class Reclamation {
    
    int id;
    String titre;
    String desipticon;
    int idu;

    public Reclamation() {
    }
    
    
    
    public Reclamation(int id, String titre, String desipticon, int idu) {
        this.id = id;
        this.titre = titre;
        this.desipticon = desipticon;
        this.idu = idu;
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

    public String getDesipticon() {
        return desipticon;
    }

    public void setDesipticon(String desipticon) {
        this.desipticon = desipticon;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }
    
    
    
    
    
}
