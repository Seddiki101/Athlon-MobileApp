/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.athlon.entities.Exercice;
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
public class ExcerciceService {

    public static ExcerciceService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Exercice> listExercice;

    private ExcerciceService() {
        cr = new ConnectionRequest();
    }

    public static ExcerciceService getInstance() {
        if (instance == null) {
            instance = new ExcerciceService();
        }
        return instance;
    }

    public ArrayList<Exercice> getAll() {
        listExercice = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/exercices/");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listExercice = getList();
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

        return listExercice;
    }

    private ArrayList<Exercice> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Exercice exercice = new Exercice(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("nom"),
                        (int) Float.parseFloat(obj.get("duree_exercices").toString()),
                        (int) Float.parseFloat(obj.get("nombre_repetitions").toString()),
                        (String) obj.get("desc_exercices"),
                        (String) obj.get("machine"),
                        (String) obj.get("image_exercice")
                );

                Map<String, Object> map = (Map<String, Object>) obj.get("Cours");
                double d = (Double.parseDouble(map.get("id") + ""));
                int x = (int) d;
                exercice.getCour().setId(x);
                exercice.getCour().setDescription_cours((String) map.get("description_cours"));
                System.out.println(exercice.getCour());
                listExercice.add(exercice);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listExercice;
    }

    public int add(Exercice exercice) {
        return manage(exercice, false);
    }

    public int edit(Exercice exercice) {
        return manage(exercice, true);
    }

    public int manage(Exercice exercice, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/exercices/edit");
            cr.addArgument("nom", String.valueOf(exercice.getNom()));
            cr.addArgument("duree_exercices", String.valueOf(exercice.getDuree_exercices()));
            cr.addArgument("nombre_repetitions", String.valueOf(exercice.getNombre_repetitions()));
            cr.addArgument("desc_exercices", String.valueOf(exercice.getDesc_exercices()));
            cr.addArgument("machine", String.valueOf(exercice.getMachine()));
//            cr.addArgument("cour", String.valueOf(exercice.getCour()));
            cr.addArgument("image_exercice", String.valueOf(exercice.getImage_exercice()));

        } else {
            cr.setUrl(Statics.BASE_URL + "/exercices/new");
            cr.addArgument("nom", String.valueOf(exercice.getNom()));
            cr.addArgument("duree_exercices", String.valueOf(exercice.getDuree_exercices()));
            cr.addArgument("nombre_repetitions", String.valueOf(exercice.getNombre_repetitions()));
            cr.addArgument("desc_exercices", String.valueOf(exercice.getDesc_exercices()));
            cr.addArgument("machine", String.valueOf(exercice.getMachine()));
            cr.addArgument("cour", String.valueOf(exercice.getCour().getId()));
            cr.addArgument("image_exercice", String.valueOf(exercice.getImage_exercice()));
        }

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
        cr.setUrl(Statics.BASE_URL + "/exercices/" + exerciceId);
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
