package com.athlon.gui.back;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import gui.ProfileForm;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme2");
    Label label;

    public AccueilBack() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        ImageViewer userImage = new ImageViewer(theme.getImage("default.jpg").fill(200, 200));
        userImage.setUIID("candidatImage");
        label = new Label("Admin"/*MainApp.getSession().getEmail()*/);
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setUIID("buttonLogout");
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        
        
        
        btnDeconnexion.addActionListener(actionConf -> {
                
                       theme = UIManager.initFirstTheme("/theme");     
                    
                         new ProfileForm(theme).show() ;
        });
        
        
        
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
                makeCongeButton(),
                makeArticleButton(),
                makeSujetButton(),
                makeCommandeButton(),
                makeLivraisonButton()
        );

        this.add(menuContainer);
    }

    private Button makeExerciceButton() {
        Button button = new Button("Exercice");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.exercice.ShowAll(this).show());
        return button;
    }

    private Button makeCourButton() {
        Button button = new Button("Cour");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.cour.ShowAll(this).show());
        return button;
    }

    private Button makeEmployeeButton() {
        Button button = new Button("Employee");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.employee.ShowAll(this).show());
        return button;
    }

    private Button makeCongeButton() {
        Button button = new Button("Congee");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.conge.ShowAll(this).show());
        return button;
    }

    private Button makeArticleButton() {
        Button button = new Button("Article");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.article.ShowAll(this).show());
        return button;
    }

    private Button makeSujetButton() {
        Button button = new Button("Sujet");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.sujet.ShowAll(this).show());
        return button;
    }

    private Button makeCommandeButton() {
        Button button = new Button("Commande");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.commande.ShowAll(this).show());
        return button;
    }

    private Button makeLivraisonButton() {
        Button button = new Button("Livraison");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.athlon.gui.back.livraison.ShowAll(this).show());
        return button;
    }

}
