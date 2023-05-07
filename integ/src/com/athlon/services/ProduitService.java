/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.athlon.entities.Produit;
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
public class ProduitService {

    public static ProduitService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Produit> listProduit;

    private ProduitService() {
        cr = new ConnectionRequest();
    }

    public static ProduitService getInstance() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    public ArrayList<Produit> getAll() {
        listProduit = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/Produitaff");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listProduit = getList();
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

        return listProduit;
    }

    private ArrayList<Produit> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Produit p = new Produit();
                p.setNom((String) obj.get("nom"));
                p.setId((int) Float.parseFloat(obj.get("id").toString()));
                listProduit.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listProduit;
    }

//    public int add(Produit article) {
//        return manage(article, false);
//    }
//
//    public int edit(Produit article) {
//        return manage(article, true);
//    }
//
//    public int manage(Produit article, boolean isEdit) {
//
//        cr = new ConnectionRequest();
//
//        cr.setHttpMethod("POST");
//        if (isEdit) {
//            cr.setUrl(Statics.BASE_URL + "/article/" + article.getId() + "/edit");
//            cr.addArgument("id", String.valueOf(article.getId()));
//        } else {
//            cr.setUrl(Statics.BASE_URL + "/article/new");
//        }
//        cr.addArgument("auteur", article.getAuteur());
//        cr.addArgument("description", article.getDescripton());
//        cr.addArgument("sujetId", article.getSujet().getId() + "");
//        cr.addArgument("titre", article.getTitre() + "");
//        cr.addArgument("img", article.getImgProduit() + "");
//
//        cr.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultCode = cr.getResponseCode();
//                cr.removeResponseListener(this);
//            }
//        });
//        try {
//            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
//            NetworkManager.getInstance().addToQueueAndWait(cr);
//        } catch (Exception ignored) {
//
//        }
//        return resultCode;
//    }
//
//    public int delete(int articleId) {
//        cr = new ConnectionRequest();
//        cr.setUrl(Statics.BASE_URL + "/article/" + articleId);
//        cr.setHttpMethod("POST");
//
//        cr.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                cr.removeResponseListener(this);
//            }
//        });
//
//        try {
//            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
//            NetworkManager.getInstance().addToQueueAndWait(cr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cr.getResponseCode();
//    }
//
//    public int likeDislike(int articleId, String type) {
//        cr = new ConnectionRequest();
//        cr.setUrl(Statics.BASE_URL + "/article/like/" + articleId + "/" + type);
//        cr.setHttpMethod("POST");
//
//        cr.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                cr.removeResponseListener(this);
//            }
//        });
//
//        try {
//            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
//            NetworkManager.getInstance().addToQueueAndWait(cr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cr.getResponseCode();
//    }
}
