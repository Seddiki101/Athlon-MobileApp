/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.services;

import com.athlon.entities.Employee;
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
public class EmployeeService {

    public static EmployeeService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Employee> listEmployee;

    private EmployeeService() {
        cr = new ConnectionRequest();
    }

    public static EmployeeService getInstance() {
        if (instance == null) {
            instance = new EmployeeService();
        }
        return instance;
    }

    public ArrayList<Employee> getAll() {
        listEmployee = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/employe/list");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listEmployee = getList();
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

        return listEmployee;
    }

    private ArrayList<Employee> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Employee employe = new Employee(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (int) Float.parseFloat(obj.get("cin").toString()),
                        (String) obj.get("nom"),
                        (String) obj.get("prenom"),
                        (String) obj.get("img_employe"),
                        (String) obj.get("etat"),
                        Float.parseFloat(obj.get("salaire").toString())
                );
                listEmployee.add(employe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listEmployee;
    }

    public int add(Employee cour) {
        return manage(cour, false);
    }

    public int edit(Employee cour) {
        return manage(cour, true);
    }

    public int manage(Employee employe, boolean isEdit) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/employe/" + employe.getId() + "/edit");
        } else {
            cr.setUrl(Statics.BASE_URL + "/employe/new");
        }
        cr.addArgument("nom", employe.getNom());
        cr.addArgument("cin", employe.getCin() + "");
        cr.addArgument("prenom", employe.getPrenom() + "");
        cr.addArgument("salaire", employe.getSalaire() + "");
        cr.addArgument("img", employe.getImg_employe() + "");

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

    public int delete(int employeeId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/employe/" + employeeId);
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
