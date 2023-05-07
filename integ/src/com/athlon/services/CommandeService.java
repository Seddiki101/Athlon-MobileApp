/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.athlon.entities.Commande;
import com.athlon.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Houssem Charef
 */
public class CommandeService {

    public static CommandeService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Commande> listCommande;

    private CommandeService() {
        cr = new ConnectionRequest();
    }

    public static CommandeService getInstance() {
        if (instance == null) {
            instance = new CommandeService();
        }
        return instance;
    }

    public ArrayList<Commande> getAll() {
        listCommande = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande");
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

    private ArrayList<Commande> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Commande commande = new Commande(
                        (int) Float.parseFloat(obj.get("idC").toString()),
                        (String) obj.get("date"),
                        (int) Float.parseFloat(obj.get("user").toString()),
                        (String) obj.get("statue")
                );

                commande.setLivraison(obj.get("livraison") == null);
                listCommande.add(commande);
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
    public int delete(int commandeId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande/delete/" + commandeId);
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

    public ArrayList<Commande> getMesCommande() {
        listCommande = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande/mes/Commandes");
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

    public int order() {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande/orderCommande");
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
