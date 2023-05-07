/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.athlon.entities.Conge;
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
public class CongeService {

    public static CongeService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Conge> listConge;

    private CongeService() {
        cr = new ConnectionRequest();
    }

    public static CongeService getInstance() {
        if (instance == null) {
            instance = new CongeService();
        }
        return instance;
    }

    public ArrayList<Conge> getAll() {
        listConge = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/conge");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listConge = getList();
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

        return listConge;
    }

    private ArrayList<Conge> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Conge conge = new Conge(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("dateD"),
                        (String) obj.get("dateF"),
                        (String) obj.get("type")
                );

                listConge.add(conge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listConge;
    }

    public int add(Conge conge) {
        return manage(conge, false);
    }

    public int edit(Conge conge) {
        return manage(conge, true);
    }

    public int manage(Conge conge, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/conge/" + conge.getId() + "/edit");
            cr.addArgument("id", String.valueOf(conge.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/conge/new");
        }
        cr.addArgument("dateD", conge.getDateD());
        cr.addArgument("DateF", conge.getDateF());
        cr.addArgument("type", conge.getType() + "");
        cr.addArgument("idEmployee", conge.getIdEmployee() + "");

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

    public int delete(int congeId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/conge/" + congeId);
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
