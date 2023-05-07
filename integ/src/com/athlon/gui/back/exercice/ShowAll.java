/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.exercice;

import com.athlon.entities.Exercice;
import com.athlon.services.ExcerciceService;
import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author Houssem Charef
 */
public class ShowAll extends Form {

    public static Exercice currentExercice = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Button addBtn;
    Label idLabel, nomLabel, duree_exercicesLabel, nombre_repetitionsLabel, desc_exercicesLabel, machineLabel, courLabel, image_exerciceLabel;
    Button deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Exercice", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();

        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");

        this.add(addBtn);
        ArrayList<Exercice> listExerciceitem = ExcerciceService.getInstance().getAll();
        if (listExerciceitem.size() > 0) {
            for (Exercice exercice : listExerciceitem) {
                this.add(makeExerciceModel(exercice));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentExercice = null;
            new Manage(this).show();
        });

    }

    private Component makeExerciceModel(Exercice exercice) {

        ImageViewer imageIV;

        Container exerciceModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exerciceModel.setUIID("containerRounded");

        idLabel = new Label("id : " + exercice.getId());
        idLabel.setUIID("labelDefault");
        nomLabel = new Label("Nom : " + exercice.getNom());
        nomLabel.setUIID("labelDefault");
        duree_exercicesLabel = new Label("duree exercices : " + exercice.getDuree_exercices());
        duree_exercicesLabel.setUIID("labelDefault");

        nombre_repetitionsLabel = new Label("nombre repetitions : " + exercice.getNombre_repetitions());
        nombre_repetitionsLabel.setUIID("labelDefault");

        desc_exercicesLabel = new Label("desc exercices : " + exercice.getDesc_exercices());
        desc_exercicesLabel.setUIID("labelDefault");

        machineLabel = new Label("machine : " + exercice.getMachine());
        machineLabel.setUIID("labelDefault");
        courLabel = new Label("cour : " + exercice.getCour().getDescription_cours());
        courLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce exercice ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ExcerciceService.getInstance().delete(exercice.getId());
                if (responseCode == 200) {
                    currentExercice = null;
                    dlg.dispose();
                    exerciceModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du Exercice. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        if (exercice.getImage_exercice() != null) {
            String url = exercice.getImage_exercice();
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

        exerciceModel.addAll(
                imageIV,
                idLabel,
                nomLabel,
                duree_exercicesLabel,
                nombre_repetitionsLabel,
                desc_exercicesLabel,
                machineLabel,
                courLabel,
                btnsContainer
        );

        return exerciceModel;
    }
}
