/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.article;

import com.athlon.entities.Article;
import com.athlon.services.ArticleService;
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

    public static Article currentArticle = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Label titreLabel, auteurLabel, descriptonLabel;
    Button addBtn, deleteBtn, editBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Article", new BoxLayout(BoxLayout.Y_AXIS));
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
            currentArticle = null;
            new Manage(this).show();
        });

    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");

        this.add(addBtn);
        ArrayList<Article> listArticle = ArticleService.getInstance().getAll();
        if (listArticle.size() > 0) {
            for (Article article : listArticle) {
                this.add(makeArticleModel(article));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeArticleModel(Article article) {

        ImageViewer imageIV;

        Container articleModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        articleModel.setUIID("containerRounded");
        titreLabel = new Label("titre : " + article.getTitre());
        titreLabel.setUIID("labelDefault");
        auteurLabel = new Label("auteur : " + article.getAuteur());
        auteurLabel.setUIID("labelDefault");
        descriptonLabel = new Label("desription : " + article.getDescripton());
        descriptonLabel.setUIID("labelDefault");

        if (article.getImgArticle() != null) {
            String url = article.getImgArticle();
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
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonMain");
        editBtn.addActionListener(action -> {
            currentArticle = article;
            new Manage(this).show();
        });
        deleteBtn = new Button("delete");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce Article ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ArticleService.getInstance().delete(article.getId());
                if (responseCode == 200) {
                    currentArticle = null;
                    dlg.dispose();
                    articleModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du Article. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });
        btnsContainer.add(BorderLayout.CENTER, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        articleModel.addAll(
                imageIV,
                auteurLabel,
                titreLabel,
                descriptonLabel,
                btnsContainer
        );

        return articleModel;
    }
}
