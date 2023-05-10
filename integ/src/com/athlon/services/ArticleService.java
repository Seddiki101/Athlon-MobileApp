/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.athlon.entities.Article;
import com.athlon.entities.Sujet;
import com.athlon.utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Houssem Charef
 */
public class ArticleService {

    public static ArticleService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Article> listArticle;

    private ArticleService() {
        cr = new ConnectionRequest();
    }

    public static ArticleService getInstance() {
        if (instance == null) {
            instance = new ArticleService();
        }
        return instance;
    }

    public ArrayList<Article> getAll() {
        listArticle = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/article");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listArticle = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listArticle;
    }

    private ArrayList<Article> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Article article = new Article(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("titre"),
                        (String) obj.get("auteur"),
                        (String) obj.get("descripton"),
                        (String) obj.get("imgArticle")
                );
//                article.setLike((int) Float.parseFloat(obj.get("likes").toString()));
//                article.setDislike((int) Float.parseFloat(obj.get("dislikes").toString()));
                Map<String, Object> mapSujet = (Map<String, Object>) obj.get("SujetX");
                if (mapSujet != null) {
                    Sujet sujet = new Sujet(
                            (int) Float.parseFloat(mapSujet.get("id").toString()),
                            (String) mapSujet.get("nom"),
                            (String) mapSujet.get("descr"),
                            (String) mapSujet.get("imgSujet")
                    );

                    article.setSujet(sujet);
                }

                listArticle.add(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listArticle;
    }

    public int add(Article article) {
        return manage(article, false);
    }

    public int edit(Article article) {
        return manage(article, true);
    }

    public int manage(Article article, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/article/" + article.getId() + "/edit");
        } else {
            cr.setUrl(Statics.BASE_URL + "/article/new");
        }
        cr.addArgument("auteur", article.getAuteur());
        cr.addArgument("description", article.getDescripton());
        cr.addArgument("sujetId", article.getSujet().getId() + "");
        cr.addArgument("titre", article.getTitre() + "");
        cr.addArgument("img", article.getImgArticle() + "");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int articleId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/article/" + articleId);
        cr.setHttpMethod("POST");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }

    public int likeDislike(int articleId, String type) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/article/like/" + articleId + "/" + type);
        cr.setHttpMethod("POST");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }

}
