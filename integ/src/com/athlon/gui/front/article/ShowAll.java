/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.front.article;

import com.athlon.entities.Article;
import com.athlon.services.ArticleService;
import com.athlon.utils.Statics;
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

/**
 *
 * @author Houssem Charef
 */
public class ShowAll extends Form {

    public static Article currentArticle = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Label titreLabel, auteurLabel, descriptonLabel;
    Button showBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Article", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();

        this.refreshTheme();
    }

    private void addGUIs() {

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

        showBtn = new Button("voir Plus>>");
        showBtn.setUIID("buttonDanger");
        showBtn.addActionListener(action -> {
            com.athlon.gui.front.sujet.Show.currentSujet = article.getSujet();
            com.athlon.gui.front.sujet.Show.currentArticle = article;
            new com.athlon.gui.front.sujet.Show(this).show();
        });
        btnsContainer.add(BorderLayout.EAST, showBtn);

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
