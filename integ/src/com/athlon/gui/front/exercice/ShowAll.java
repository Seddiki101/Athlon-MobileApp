/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.front.exercice;

import com.athlon.entities.Exercice;
import com.athlon.services.ExcerciceService;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
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

public class ShowAll extends Form {

    public static Exercice currentExercice = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Button addBtn;
    Label idLabel, nomLabel, duree_exercicesLabel, nombre_repetitionsLabel, desc_exercicesLabel, machineLabel, courLabel, image_exerciceLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Exercice", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Exercice> listCommandeitem = ExcerciceService.getInstance().getAll();
        if (listCommandeitem.size() > 0) {
            for (Exercice commandeitem : listCommandeitem) {
                this.add(makeCommandeModel(commandeitem));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeCommandeModel(Exercice exercice) {

        ImageViewer imageIV;

        Container exerciceItemModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exerciceItemModel.setUIID("containerRounded");

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

        exerciceItemModel.addAll(
                imageIV,
                idLabel,
                nomLabel,
                duree_exercicesLabel,
                nombre_repetitionsLabel,
                desc_exercicesLabel,
                machineLabel,
                courLabel
        );

        return exerciceItemModel;
    }
}
