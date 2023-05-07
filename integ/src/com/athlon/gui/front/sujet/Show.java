/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.front.sujet;

import com.athlon.entities.Article;
import com.athlon.entities.Sujet;
import com.athlon.services.ArticleService;
import com.athlon.utils.Statics;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
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

/**
 *
 * @author Houssem Charef
 */
public class Show extends Form {

    public static Sujet currentSujet = null;
    public static Article currentArticle = null;
    private int nbLike = 0;

    Resources theme = UIManager.initFirstTheme("/theme2");
    SpanLabel descriptionLabel;
    Button likeBtn, dislikeBtn;
    Container btnsContainer;

    public Show(Form previous) {
        super(currentSujet.getNom(), new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        nbLike = currentArticle.getLike();
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        this.add(makeSujetModel(currentSujet));
    }

    private Component makeSujetModel(Sujet sujet) {

        ImageViewer imageIV;

        Container exerciceItemModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exerciceItemModel.setUIID("containerRounded");

        descriptionLabel = new SpanLabel(sujet.getDescription());
        descriptionLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        likeBtn = new Button("like(" + currentArticle.getLike() + ")");
        likeBtn.setUIID("buttonDanger");
        dislikeBtn = new Button("dislike(" + currentArticle.getDislike() + ")");
        dislikeBtn.setUIID("buttonDanger");
        likeBtn.addActionListener(l -> {
            currentArticle.setLike(currentArticle.getLike() + 1);

            likeBtn.setText("like(" + currentArticle.getLike() + ")");
            ArticleService.getInstance().likeDislike(currentArticle.getId(), "like");
        });

        dislikeBtn.addActionListener(l -> {
            currentArticle.setDislike(currentArticle.getDislike() + 1);
            dislikeBtn.setText("dislike(" + currentArticle.getDislike() + ")");

            ArticleService.getInstance().likeDislike(currentArticle.getId(), "dislike");
        });

        btnsContainer.add(BorderLayout.EAST, likeBtn);
        btnsContainer.add(BorderLayout.WEST, dislikeBtn);

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

        exerciceItemModel.addAll(
                imageIV,
                descriptionLabel,
                btnsContainer
        );

        return exerciceItemModel;
    }
}
