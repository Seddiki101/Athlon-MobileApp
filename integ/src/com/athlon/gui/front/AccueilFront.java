package com.athlon.gui.front;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import gui.ProfileForm;

public class AccueilFront extends Form {

    Resources theme = UIManager.initFirstTheme("/theme2");
    Label label;

    public AccueilFront() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        ImageViewer userImage = new ImageViewer(theme.getImage("default.jpg").fill(200, 200));
        userImage.setUIID("candidatImage");
        label = new Label("Front");
        label.setUIID("links");
        
        Button btnDeconnexion = new Button();
        btnDeconnexion.setUIID("buttonLogout");
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        
                btnDeconnexion.addActionListener(actionConf -> {
                
                       theme = UIManager.initFirstTheme("/theme");     
                    
                         new ProfileForm(theme).show() ;
        });
        
        /*
        btnDeconnexion.addActionListener(actionConf -> {
            new com.athlon.gui.back.AccueilBack().show();
        });
        */
        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.WEST, userImage);
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeExerciceButton(),
                makeCourButton(),
                makeEmployeeButton(),
                makeArticleButton(),
                makeCommandeButton(),
                makeCartButton(),
                makeProduitButton()
        );

        this.add(menuContainer);
    }

    private Button makeExerciceButton() {
        Button button = new Button("Exercice");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.front.exercice.ShowAll(this).show());
        return button;
    }

    private Button makeCourButton() {
        Button button = new Button("Cour");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.front.cour.ShowAll(this).show());
        return button;
    }

    private Button makeEmployeeButton() {
        Button button = new Button("Employee");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.front.employee.ShowAll(this).show());
        return button;
    }

    private Button makeArticleButton() {
        Button button = new Button("Article");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.front.article.ShowAll(this).show());
        return button;
    }

    private Button makeCommandeButton() {
        Button button = new Button("Mes Commande");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.front.mesCommande.ShowAll(this).show());
        return button;
    }

    private Button makeCartButton() {
        Button button = new Button("Cart");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.front.cart.ShowAll(this).show());
        return button;
    }

    private Button makeProduitButton() {
        Button button = new Button("produit");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.front.produit.ShowAll(this).show());
        return button;
    }

}
