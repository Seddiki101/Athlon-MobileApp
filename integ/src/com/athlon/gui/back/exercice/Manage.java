package com.athlon.gui.back.exercice;

import com.athlon.entities.Cour;
import com.athlon.entities.Exercice;
import com.athlon.services.CourService;
import com.athlon.services.ExcerciceService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

public class Manage extends Form {

    Exercice currentExercice;

    Label nomLabel, duree_exercicesLabel, nombre_repetitionsLabel, desc_exercicesLabel, machineLabel, courLabel, imageLabel;
    TextField nomTF, duree_exercicesTF, nombre_repetitionsTF, desc_exercicesTF, machineTF, courTF, image_exerciceTF;
    ComboBox<Cour> courComboBox = new ComboBox<>();

    Button manageButton;

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme2");
    String selectedImage;
    boolean imageEdited = false;
    ImageViewer imageIV;
    Button selectImageButton;

    public Manage(Form previous) {
        super(ShowAll.currentExercice == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        currentExercice = ShowAll.currentExercice;
        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        ArrayList<Cour> listCour = new ArrayList<>();
        listCour.addAll(CourService.getInstance().getAll());
        for (int i = 0; i < listCour.size(); i++) {
            courComboBox.addItem(listCour.get(i));
        }
        nomLabel = new Label("Nom ");
        duree_exercicesLabel = new Label("duree exercices ");
        nombre_repetitionsLabel = new Label("nombre repetitions ");
        desc_exercicesLabel = new Label("desc ");
        machineLabel = new Label("machine ");
        courLabel = new Label("cour ");
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");

        nomTF = new TextField();
        nomTF.setHint("Tapez le type");
        duree_exercicesTF = new TextField();
        duree_exercicesTF.setHint("Tapez le type");
        nombre_repetitionsTF = new TextField();
        nombre_repetitionsTF.setHint("Tapez le type");
        desc_exercicesTF = new TextField();
        desc_exercicesTF.setHint("Tapez le type");
        machineTF = new TextField();
        machineTF.setHint("Tapez le type");
        courTF = new TextField();
        courTF.setHint("Tapez le type");

        selectImageButton = new Button("Ajouter une image");

        if (currentExercice == null) {
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));

            manageButton = new Button("Ajouter");
        } else {

            nomTF.getText();
            duree_exercicesTF.getText();
            nombre_repetitionsTF.getText();
            desc_exercicesTF.getText();
            machineTF.getText();
            image_exerciceTF.getText();
            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                nomLabel, nomTF,
                duree_exercicesLabel, duree_exercicesTF,
                nombre_repetitionsLabel, nombre_repetitionsTF,
                desc_exercicesLabel, desc_exercicesTF,
                machineLabel, machineTF,
                courLabel, courComboBox,
                imageLabel, imageIV,
                selectImageButton,
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
        if (currentExercice == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Exercice e = new Exercice(
                            nomTF.getText(),
                            (int) Float.parseFloat(duree_exercicesTF.getText()),
                            (int) Float.parseFloat(nombre_repetitionsTF.getText()),
                            desc_exercicesTF.getText(),
                            machineTF.getText(),
                            selectedImage
                    );
                    e.setCour(courComboBox.getSelectedItem());
                    int responseCode = ExcerciceService.getInstance().add(e);
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Exercice ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de categorie. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
//            manageButton.addActionListener(action -> {
//                if (controleDeSaisie()) {
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
//                        Dialog.show("Succés", "Exercice modifié avec succes", new Command("Ok"));
//                        showBackAndRefresh();
//                    } else {
//                        Dialog.show("Erreur", "Erreur de modification de categorie. Code d'erreur : " + responseCode, new Command("Ok"));
//                    }
//                }
//            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le type", new Command("Ok"));
            return false;
        }
        if (desc_exercicesTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le type", new Command("Ok"));
            return false;
        }
        if (machineTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le type", new Command("Ok"));
            return false;
        }

        try {
            Float.parseFloat(duree_exercicesTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", duree_exercicesTF.getText() + " n'est pas un nombre valide (duree)", new Command("Ok"));
            return false;
        }

        try {
            Float.parseFloat(nombre_repetitionsTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nombre_repetitionsTF.getText() + " n'est pas un nombre valide (nombre)", new Command("Ok"));
            return false;
        }

        return true;
    }
}
