/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.front.produit;

import com.athlon.entities.Produit;
import com.athlon.entities.Livraison;
import com.athlon.services.CartService;
import com.athlon.services.ProduitService;
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

    public static Produit currenrProduit = null;
    Label nomLabel, UserLabel, dateLabel, statutLabel;
    Button addtoCartBtn;
    Container btnsContainer;
    Resources theme = UIManager.initFirstTheme("/theme2");

    public ShowAll(Form previous) {
        super("Produit", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Produit> listProduits = ProduitService.getInstance().getAll();
        if (listProduits.size() > 0) {
            for (Produit produit : listProduits) {
                this.add(makeProduitModel(produit));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeProduitModel(Produit produit) {
        ImageViewer imageIV;

        Container produitModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        produitModel.setUIID("containerRounded");

        nomLabel = new Label("nom : " + produit.getNom());
        nomLabel.setUIID("labelDefault");
//        dateLabel = new Label("date : " + produit.getDate());
//        dateLabel.setUIID("labelDefault");
//        UserLabel = new Label("user : " + produit.getIdUser());
//        UserLabel.setUIID("labelDefault");
//        statutLabel = new Label("statut : " + produit.getStatut());
//        statutLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        if (produit.getImage() != null) {
            String url = produit.getImage();
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

        addtoCartBtn = new Button("Add to Cart");
        addtoCartBtn.setUIID("buttonDanger");
        addtoCartBtn.addActionListener(action -> {
            CartService.getInstance().add(produit.getId());
        });

        btnsContainer.add(BorderLayout.CENTER, addtoCartBtn);

        produitModel.addAll(
                imageIV,
                nomLabel,
                btnsContainer
        );

        return produitModel;
    }
}
