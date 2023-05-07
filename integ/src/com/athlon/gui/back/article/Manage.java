/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.article;

import com.athlon.entities.Article;
import com.athlon.entities.Sujet;
import com.athlon.services.ArticleService;
import com.athlon.services.SujetService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Houssem Charef
 */
public class Manage extends Form {

    Article curentArticle;
    String selectedImage;
    Resources theme = UIManager.initFirstTheme("/theme2");

    Label titreLabel, auteurLabel, descriptonLabel, imageLabel;
    TextField titreTF, auteurTF, descriptonTF;
    ComboBox<Sujet> sujetComboBox = new ComboBox<>();
    boolean imageEdited = false;

    Button manageButton;
    Button selectImageButton;
    ImageViewer imageIV;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentArticle == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        curentArticle = ShowAll.currentArticle;

        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        ArrayList<Sujet> listSujet = new ArrayList<>();
        listSujet.addAll(SujetService.getInstance().getAll());
        for (int i = 0; i < listSujet.size(); i++) {
            sujetComboBox.addItem(listSujet.get(i));
        }

        titreLabel = new Label("titre : ");
        titreLabel.setUIID("labelDefault");

        descriptonLabel = new Label("description : ");
        descriptonLabel.setUIID("labelDefault");

        auteurLabel = new Label("Auteur : ");
        auteurLabel.setUIID("labelDefault");

        titreTF = new TextField();
        titreTF.setHint("Tapez le titre");
        auteurTF = new TextField();
        auteurTF.setHint("Tapez le auteur");
        descriptonTF = new TextField();
        descriptonTF.setHint("Tapez le descrption");

        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (curentArticle == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));

            manageButton = new Button("Ajouter");
        } else {
            if (curentArticle.getImgArticle() != null) {
                selectedImage = curentArticle.getImgArticle();
                String url = curentArticle.getImgArticle();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);

            titreTF.setText(curentArticle.getTitre());
            auteurTF.setText(curentArticle.getAuteur());
            descriptonTF.setText(curentArticle.getDescripton());
            manageButton = new Button("Modifier");
        }

        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                titreLabel, titreTF,
                descriptonLabel, auteurTF,
                auteurLabel, descriptonTF,
                imageLabel, imageIV,
                selectImageButton,
                sujetComboBox,
                manageButton
        );
        this.addAll(container);
    }

    private void addActions() {
        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });

        if (curentArticle == null) {

            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Article a = new Article(
                            titreTF.getText(),
                            auteurTF.getText(),
                            descriptonTF.getText(),
                            selectedImage
                    );
                    a.setSujet(sujetComboBox.getSelectedItem());
                    int responseCode = ArticleService.getInstance().add(a);

                    if (responseCode == 200) {
                        Dialog.show("Succés", "article ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "errur d'ajout de article. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Article a = new Article(
                            curentArticle.getId(),
                            titreTF.getText(),
                            auteurTF.getText(),
                            descriptonTF.getText(),
                            selectedImage
                    );
                    a.setSujet(sujetComboBox.getSelectedItem());
                    int responseCode = ArticleService.getInstance().edit(
                            a);

                    if (responseCode == 200) {
                        Dialog.show("Succés", "cour modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de cour. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (titreLabel.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le titre", new Command("Ok"));
            return false;
        }
        if (auteurLabel.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le auteur", new Command("Ok"));
            return false;
        }
        if (descriptonLabel.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le description", new Command("Ok"));
            return false;
        }
        return true;
    }

}
