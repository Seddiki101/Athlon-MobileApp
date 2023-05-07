/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

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
public class SujetService {

    public static SujetService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Sujet> listSujet;

    private SujetService() {
        cr = new ConnectionRequest();
    }

    public static SujetService getInstance() {
        if (instance == null) {
            instance = new SujetService();
        }
        return instance;
    }

    public ArrayList<Sujet> getAll() {
        listSujet = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/sujet");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listSujet = getList();
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

        return listSujet;
    }

    private ArrayList<Sujet> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Sujet sujet = new Sujet(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("nom"),
                        (String) obj.get("descr"),
                        (String) obj.get("imgSujet")
                );

                listSujet.add(sujet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listSujet;
    }

    public int add(Sujet sujet) {
        return manage(sujet, false);
    }

    public int edit(Sujet sujet) {
        return manage(sujet, true);
    }

    public int manage(Sujet sujet, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/sujet/" + sujet.getId() + "/edit");
            cr.addArgument("id", String.valueOf(sujet.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/sujet/new");
        }
        cr.addArgument("nom", sujet.getNom());
        cr.addArgument("description", sujet.getDescription());
        cr.addArgument("img", sujet.getImgSujet() + "");

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

    public int delete(int sujetId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/sujet/" + sujetId);
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
