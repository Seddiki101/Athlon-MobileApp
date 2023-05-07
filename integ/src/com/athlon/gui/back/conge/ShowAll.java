/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.conge;

import com.athlon.entities.Conge;
import static com.athlon.gui.back.exercice.ShowAll.currentExercice;
import com.athlon.services.CongeService;
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

    public static Conge currentConge = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Button addBtn;
    Label dateDLabel, dateFLabel, typeLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("congee", new BoxLayout(BoxLayout.Y_AXIS));
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
        ArrayList<Conge> listConge = CongeService.getInstance().getAll();
        if (listConge.size() > 0) {
            for (Conge cour : listConge) {
                this.add(makeCongeModel(cour));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentConge = null;
            new Manage(this).show();
        });

    }

    private Component makeCongeModel(Conge conge) {

        Container congeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        congeModel.setUIID("containerRounded");

        dateDLabel = new Label("date debut : " + conge.getDateD());
        dateDLabel.setUIID("labelDefault");
        dateFLabel = new Label("date Fin : " + conge.getDateF());
        dateFLabel.setUIID("labelDefault");
        typeLabel = new Label("type : " + conge.getType());
        typeLabel.setUIID("labelDefault");

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
                int responseCode = CongeService.getInstance().delete(conge.getId());
                if (responseCode == 200) {
                    currentExercice = null;
                    dlg.dispose();
                    congeModel.remove();
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
        congeModel.addAll(
                dateDLabel,
                dateFLabel,
                typeLabel,
                btnsContainer
        );

        return congeModel;
    }
}
