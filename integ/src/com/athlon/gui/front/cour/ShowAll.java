/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.front.cour;

import com.athlon.entities.Cour;
import com.athlon.services.CourService;
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
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author Houssem Charef
 */
public class ShowAll extends Form {

    public static Cour currentCour = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Label idLabel, description_coursLabel, Niveau_coursLabel, duree_coursLabel, capacityLabel, image_coursLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Cour", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();

        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Cour> listCommandeitem = CourService.getInstance().getAll();
        if (listCommandeitem.size() > 0) {
            for (Cour commandeitem : listCommandeitem) {
                this.add(makeCommandeModel(commandeitem));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeCommandeModel(Cour cour) {

        ImageViewer imageIV;

        Container exerciceItemModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exerciceItemModel.setUIID("containerRounded");

        idLabel = new Label("id : " + cour.getId());
        idLabel.setUIID("labelDefault");
        description_coursLabel = new Label("description : " + cour.getDescription_cours());
        description_coursLabel.setUIID("labelDefault");
        Niveau_coursLabel = new Label("Niveau : " + cour.getNiveau_cours());
        Niveau_coursLabel.setUIID("labelDefault");

        duree_coursLabel = new Label("duree : " + cour.getDuree_cours());
        duree_coursLabel.setUIID("labelDefault");

        capacityLabel = new Label("capacité : " + cour.getCapacity());
        capacityLabel.setUIID("labelDefault");

        if (cour.getImage_cours() != null) {
            String url = cour.getImage_cours();
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
                description_coursLabel,
                Niveau_coursLabel,
                duree_coursLabel,
                capacityLabel
        );

        return exerciceItemModel;
    }
}
