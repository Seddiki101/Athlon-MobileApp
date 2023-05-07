/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.employee;

import com.athlon.entities.Employee;
import com.athlon.services.EmployeeService;
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

    Employee currentEmployee;

    Label cinLabel, nomLabel, prenomLabel, etatLabel, SalaireLabel, imageLabel;
    TextField cinTF, nomTF, prenomTF, etatTF, SalaireTF;

    Button manageButton;

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme2");
    String selectedImage;
    boolean imageEdited = false;
    ImageViewer imageIV;
    Button selectImageButton;

    public Manage(Form previous) {
        super(com.athlon.gui.back.employee.ShowAll.currentEmployee == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        currentEmployee = com.athlon.gui.back.employee.ShowAll.currentEmployee;
        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        cinLabel = new Label("CIN ");
        nomLabel = new Label("nom ");
        prenomLabel = new Label("prenom ");
        etatLabel = new Label("etat ");
        SalaireLabel = new Label("salaire ");
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        cinTF = new TextField();
        cinTF.setHint("Tapez le CIN");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");
        prenomTF = new TextField();
        prenomTF.setHint("Tapez le prenom");
        etatTF = new TextField();
        etatTF.setHint("Tapez le etat");
        SalaireTF = new TextField();
        SalaireTF.setHint("Tapez le salaire");

        if (currentEmployee == null) {
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
                cinLabel, cinTF,
                nomLabel, nomTF,
                prenomLabel, prenomTF,
                SalaireLabel, SalaireTF,
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
        if (currentEmployee == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Employee e = new Employee(
                            (int) Float.parseFloat(cinTF.getText()),
                            nomTF.getText(),
                            prenomTF.getText(),
                            selectedImage,
                            etatTF.getText(),
                            (int) Float.parseFloat(SalaireTF.getText())
                    );
                    int responseCode = EmployeeService.getInstance().add(e);
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Employee ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "errur d'ajout de Employee. Code d'erreur : " + responseCode, new Command("Ok"));
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
        ((com.athlon.gui.back.employee.ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le nom", new Command("Ok"));
            return false;
        }
        if (prenomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le prenom", new Command("Ok"));
            return false;
        }

        try {
            Float.parseFloat(SalaireTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", SalaireTF.getText() + " n'est pas un nombre valide (salaire)", new Command("Ok"));
            return false;
        }

        try {
            Float.parseFloat(cinTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", cinTF.getText() + " n'est pas un nombre valide (cin)", new Command("Ok"));
            return false;
        }

        return true;
    }
}
