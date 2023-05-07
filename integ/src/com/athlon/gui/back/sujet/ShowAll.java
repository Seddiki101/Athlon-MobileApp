/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.sujet;

import com.athlon.entities.Sujet;
import com.athlon.services.SujetService;
import com.athlon.utils.Statics;
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

    public static Sujet currentSujet = null;

    Resources theme = UIManager.initFirstTheme("/theme2");
    Label descriptionLabel, nomLabel;
    Button deleteBtn, addBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Sujet", new BoxLayout(BoxLayout.Y_AXIS));
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

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentSujet = null;
            new Manage(this).show();
        });

    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");

        this.add(addBtn);
        ArrayList<Sujet> listSujet = SujetService.getInstance().getAll();
        if (listSujet.size() > 0) {
            for (Sujet sujet : listSujet) {
                this.add(makeSujetModel(sujet));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeSujetModel(Sujet sujet) {

        ImageViewer imageIV;

        Container sujetModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        sujetModel.setUIID("containerRounded");

        descriptionLabel = new Label("description: " + sujet.getDescription());
        descriptionLabel.setUIID("labelDefault");
        nomLabel = new Label("nom: " + sujet.getNom());
        nomLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        deleteBtn = new Button("delete");
        deleteBtn.setUIID("buttonDanger");

        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce sujet ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = SujetService.getInstance().delete(sujet.getId());
                if (responseCode == 200) {
                    currentSujet = null;
                    dlg.dispose();
                    sujetModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du sujet. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        if (sujet.getImgSujet() != null) {
            String url = sujet.getImgSujet();
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

        sujetModel.addAll(
                imageIV,
                nomLabel,
                descriptionLabel,
                btnsContainer
        );

        return sujetModel;
    }
}
