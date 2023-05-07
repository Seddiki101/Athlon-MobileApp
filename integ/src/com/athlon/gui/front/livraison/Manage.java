/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.front.livraison;

import com.athlon.entities.Livraison;
import com.athlon.services.LivraisonService;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Houssem Charef
 */
public class Manage extends Form {

    public static Livraison currentLivraison = null;

    Label addresseLabel, emailLabel;
    TextField addresseTF, emailTF;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(com.athlon.gui.front.mesCommande.ShowAll.currentCommande == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

//        currentLivraison = com.athlon.gui.front.mesCommande.ShowAll.currentCommande;
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        addresseLabel = new Label("Adresse : ");
        addresseLabel.setUIID("labelDefault");
        addresseTF = new TextField();
        addresseTF.setHint("Tapez le type");
        emailLabel = new Label("email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le type");
        System.out.println(com.athlon.gui.front.mesCommande.ShowAll.currentCommande);
        if (com.athlon.gui.front.mesCommande.ShowAll.currentCommande.isLivraison()) {

            manageButton = new Button("Ajouter");
        } else {

            manageButton = new Button("Modifier");
            Livraison livaraison = LivraisonService.getInstance().getbyIdCommande(com.athlon.gui.front.mesCommande.ShowAll.currentCommande.getIdC());
            addresseTF.setText(livaraison.getAdresse());
            emailTF.setText(livaraison.getEmail());
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                emailLabel, emailTF,
                addresseLabel, addresseTF,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        if (com.athlon.gui.front.mesCommande.ShowAll.currentCommande.isLivraison()) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Livraison livraison = new Livraison();
                    livraison.setEmail(emailTF.getText());
                    livraison.setAdresse(addresseTF.getText());

                    int responseCode = LivraisonService.getInstance().add(livraison);
                    if (responseCode == 200) {
                        Dialog.show("Succés", "livraison ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de livraison. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Livraison livraison = new Livraison();
                    livraison.setEmail(emailTF.getText());
                    livraison.setAdresse(addresseTF.getText());
                    int responseCode = LivraisonService.getInstance().edit(livraison, com.athlon.gui.front.mesCommande.ShowAll.currentCommande.getIdC());

                    if (responseCode == 200) {
                        Dialog.show("Succés", "livraison modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de livraison. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        previous.showBack();
        ((com.athlon.gui.front.mesCommande.ShowAll) previous).refresh();
        previous.refreshTheme();
    }

    private boolean controleDeSaisie() {

        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le email", new Command("Ok"));
            return false;
        }
        if (addresseTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le addresse", new Command("Ok"));
            return false;
        }

        return true;
    }
}
