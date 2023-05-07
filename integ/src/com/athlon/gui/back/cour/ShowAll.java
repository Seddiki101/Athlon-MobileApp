/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.cour;

import com.athlon.entities.Cour;
import static com.athlon.gui.back.exercice.ShowAll.currentExercice;
import com.athlon.services.CourService;
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

    public static Cour currentCour = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Button addBtn;
    Label idLabel, description_coursLabel, Niveau_coursLabel, duree_coursLabel, capacityLabel, image_coursLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Cour", new BoxLayout(BoxLayout.Y_AXIS));
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
        ArrayList<Cour> listCour = CourService.getInstance().getAll();
        if (listCour.size() > 0) {
            for (Cour cour : listCour) {
                this.add(makeCourModel(cour));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCour = null;
            new Manage(this).show();
        });

    }

    private Component makeCourModel(Cour cour) {

        ImageViewer imageIV;

        Container courModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        courModel.setUIID("containerRounded");

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
                int responseCode = CourService.getInstance().delete(cour.getId());
                if (responseCode == 200) {
                    currentExercice = null;
                    dlg.dispose();
                    courModel.remove();
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
        courModel.addAll(
                imageIV,
                idLabel,
                description_coursLabel,
                Niveau_coursLabel,
                duree_coursLabel,
                capacityLabel,
                btnsContainer
        );

        return courModel;
    }
}
