/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.athlon.entities.Cour;
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
public class CourService {

    public static CourService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Cour> listCour;

    private CourService() {
        cr = new ConnectionRequest();
    }

    public static CourService getInstance() {
        if (instance == null) {
            instance = new CourService();
        }
        return instance;
    }

    public ArrayList<Cour> getAll() {
        listCour = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/cours/");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCour = getList();
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

        return listCour;
    }

    private ArrayList<Cour> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Cour cour = new Cour(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("description_cours"),
                        (String) obj.get("Niveau_cours"),
                        (int) Float.parseFloat(obj.get("duree_cours").toString()),
                        (int) Float.parseFloat(obj.get("capacity").toString()),
                        (String) obj.get("image_cours"),
                        (String) obj.get("nom")
                );
                listCour.add(cour);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCour;
    }

    public int add(Cour cour) {
        return manage(cour, false);
    }

    public int edit(Cour cour) {
        return manage(cour, true);
    }

    public int manage(Cour cour, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/cours/edit");
            cr.addArgument("id", String.valueOf(cour.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/cours/new");
        }
        cr.addArgument("description_cours", cour.getDescription_cours());
        cr.addArgument("Niveau_cours", cour.getNiveau_cours());
        cr.addArgument("duree_cours", cour.getDuree_cours() + "");
        cr.addArgument("capacity", cour.getCapacity() + "");
        cr.addArgument("image_cours", cour.getImage_cours());
        cr.addArgument("nom", cour.getNom());

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

    public int delete(int exerciceId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/cours/" + exerciceId);
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
