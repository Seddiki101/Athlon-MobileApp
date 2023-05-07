package com.athlon.gui.back.cour;

import com.athlon.entities.Cour;
import com.athlon.services.CourService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

public class Manage extends Form {

    Cour currentCour;

    Label nomLabel, description_coursLabel, Niveau_coursLabel, duree_coursLabel, capacityLabel, imageLabel;
    TextField nomTF, description_coursTF, Niveau_coursTF, duree_coursTF, capacityTF;
    ComboBox<Cour> courComboBox = new ComboBox<>();

    Button manageButton;

    Form previous;
    Resources theme = UIManager.initFirstTheme("/theme2");
    String selectedImage;
    boolean imageEdited = false;
    ImageViewer imageIV;
    Button selectImageButton;

    public Manage(Form previous) {
        super(ShowAll.currentCour == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        currentCour = ShowAll.currentCour;
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
        description_coursLabel = new Label("description ");
        Niveau_coursLabel = new Label("Niveau ");
        duree_coursLabel = new Label("duree ");
        capacityLabel = new Label("capacity ");
        nomLabel = new Label("nom ");
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        description_coursTF = new TextField();
        description_coursTF.setHint("Tapez le type");
        Niveau_coursTF = new TextField();
        Niveau_coursTF.setHint("Tapez le type");
        duree_coursTF = new TextField();
        duree_coursTF.setHint("Tapez le type");
        capacityTF = new TextField();
        capacityTF.setHint("Tapez le type");

        nomTF = new TextField();
        nomTF.setHint("Tapez le type");

        if (currentCour == null) {
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));

            manageButton = new Button("Ajouter");
        } else {

            description_coursTF.getText();
            Niveau_coursTF.getText();
            duree_coursTF.getText();
            capacityTF.getText();
            nomTF.getText();

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                nomLabel, nomTF,
                description_coursLabel, description_coursTF,
                Niveau_coursLabel, Niveau_coursTF,
                duree_coursLabel, duree_coursTF,
                capacityLabel, capacityTF,
                imageLabel, imageIV,
                selectImageButton,
                manageButton
        );
        this.addAll(container);
    }

    private void addActions() {

        if (currentCour == null) {
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
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Cour c = new Cour(
                            description_coursTF.getText(),
                            Niveau_coursTF.getText(),
                            (int) Float.parseFloat(duree_coursTF.getText()),
                            (int) Float.parseFloat(capacityTF.getText()),
                            selectedImage,
                            nomTF.getText()
                    );
                    int responseCode = CourService.getInstance().add(c);
                    if (responseCode == 200) {
                        Dialog.show("Succés", "cour ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "errur d'ajout de cour. Code d'erreur : " + responseCode, new Command("Ok"));
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
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (description_coursTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le description", new Command("Ok"));
            return false;
        }
        if (Niveau_coursTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le niveau", new Command("Ok"));
            return false;
        }

        try {
            Float.parseFloat(duree_coursTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", duree_coursTF.getText() + " n'est pas un nombre valide (duree)", new Command("Ok"));
            return false;
        }

        try {
            Float.parseFloat(capacityTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", capacityTF.getText() + " n'est pas un nombre valide (nombre)", new Command("Ok"));
            return false;
        }

        return true;
    }
}
