/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.athlon.entities.Commande;
import com.athlon.entities.CommandeItem;
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
public class CommandeItemService {

    public static CommandeItemService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<CommandeItem> listCommande;

    private CommandeItemService() {
        cr = new ConnectionRequest();
    }

    public static CommandeItemService getInstance() {
        if (instance == null) {
            instance = new CommandeItemService();
        }
        return instance;
    }

    public ArrayList<CommandeItem> getAllByID(int idCommande) {
        listCommande = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commandeItem/showByCommande/" + idCommande);
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommande = getList();
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

        return listCommande;
    }

    private ArrayList<CommandeItem> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                CommandeItem commandeItem = new CommandeItem(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        "test",
                        (int) Float.parseFloat(obj.get("quantity").toString())
                );

                listCommande.add(commandeItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCommande;
    }

//    public int add(Categorie categorie) {
//        return manage(categorie, false);
//    }
//
//    public int edit(Categorie categorie) {
//        return manage(categorie, true);
//    }
//
//    public int manage(Categorie categorie, boolean isEdit) {
//
//        cr = new ConnectionRequest();
//
//        cr.setHttpMethod("POST");
//        if (isEdit) {
//            cr.setUrl(Statics.BASE_URL + "/categorie/edit");
//            cr.addArgument("id", String.valueOf(categorie.getId()));
//        } else {
//            cr.setUrl(Statics.BASE_URL + "/categorie/add");
//        }
//        cr.addArgument("type", categorie.getType());
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
    public int delete(int commandeItemId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commandeItem/" + commandeItemId);
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

    public ArrayList<CommandeItem> getAll(int idC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
