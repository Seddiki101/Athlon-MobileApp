/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.sujet;

import com.athlon.entities.Employee;
import com.athlon.entities.Sujet;
import com.athlon.services.SujetService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author Houssem Charef
 */
public class Manage extends Form {

    Employee currentsujet = null;

    Label nomLabel, descriptionLabel, imageLabel;
    TextField nomTF, descriptionTF;

    Button manageButton;

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme2");
    String selectedImage;
    boolean imageEdited = false;
    ImageViewer imageIV;
    Button selectImageButton;

    public Manage(Form previous) {
        super("Ajouter");
        this.previous = previous;
        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        nomLabel = new Label("nom ");
        descriptionLabel = new Label("desription ");

        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentsujet == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));

            manageButton = new Button("Ajouter");
        } else {

//            
            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                nomLabel, nomTF,
                descriptionLabel, descriptionTF,
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
                System.out.println("tzqt");
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });

        if (currentsujet == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Sujet s = new Sujet(
                            nomTF.getText(),
                            descriptionTF.getText(),
                            selectedImage
                    );
                    int responseCode = SujetService.getInstance().add(s);
                    if (responseCode == 200) {
                        Dialog.show("Succés", "sujet ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "errur d'ajout de sujet. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
//                    int responseCode = ExcerciceService.getInstance().edit(
//                            new Exercice(
//                                    nomTF.getText(),
//                                    (int) Float.parseFloat(duree_exercicesTF.getText()),
//                                    (int) Float.parseFloat(nombre_repetitionsTF.getText()),
//                                    desc_exercicesTF.getText(),
//                                    machineTF.getText(),
//                                    image_exerciceTF.getText()
//                            )
//                    );
//                    if (responseCode == 200) {
//                        Dialog.show("Succés", "cour modifié avec succes", new Command("Ok"));
//                        showBackAndRefresh();
//                    } else {
//                        Dialog.show("Erreur", "Erreur de modification de cour. Code d'erreur : " + responseCode, new Command("Ok"));
//                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((com.athlon.gui.back.sujet.ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le nom", new Command("Ok"));
            return false;
        }
        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le description", new Command("Ok"));
            return false;
        }
        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }

        return true;
    }
}
