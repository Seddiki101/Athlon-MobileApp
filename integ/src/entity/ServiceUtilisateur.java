/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.google.gson.Gson;
import entity.Statics;
import gui.SessionManager;
import gui.WalkthruForm;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import util.PwdHasher;

/**
 *
 * @author Lenovo
 */
public class ServiceUtilisateur {
    
  //singleton 
    public static ServiceUtilisateur instance = null ;
    
    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceUtilisateur getInstance() {
        if(instance == null )
            instance = new ServiceUtilisateur();
        return instance ;
    }
    
    
    
    public ServiceUtilisateur() {
        req = new ConnectionRequest();
        
    }
    
    //Signup
    public void signup(TextField firstName,TextField lastName,TextField Email ,TextField Password, Resources res  ) {
        
                    //hashing
                    String pew;
                    pew = PwdHasher.hashPassword(Password.getText());
        
        String url = Statics.BASE_URL+"/addUserJSON/new?nom="+firstName.getText().toString()+"&prenom="+lastName.getText().toString() +"&email="+Email.getText().toString()+"&password="+pew;
        
        req.setUrl(url);
       
        //Control saisi
        if(firstName.getText().equals(" ") && lastName.getText().equals(" ") && Password.getText().equals(" ") && Email.getText().equals(" ")) {
            
            Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
            
        }
        
        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e)-> {
         
            //njib data ly7atithom fi form 
            byte[]data = (byte[]) e.getMetaData(); 
            String responseData = new String(data);
            
            System.out.println("data ===>"+responseData);
        }
        );
        
        
        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        
            
        
    }
    
    
    //SignIn
    
    public void signin(TextField Email,TextField Password, Resources rs ) {
        
        
        String url = Statics.BASE_URL+"/signin?email="+Email.getText().toString()+"&password="+Password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) + "";
            
            
            try {
            
            if(json.equals("failed")) {
                Dialog.show("Echec d'authentification","Email ou mot de passe éronné","OK",null);
                Display.getInstance().exitApplication();
            }
            else {
                System.out.println("he Has ligma ");
                System.out.println("data =="+json);
                
                
                
                
                
                //
                //ObjectMapper objectMapper = new ObjectMapper();
                //uzer user = objectMapper.readValue(json, uzer.class);
                
                
                Gson gson = new Gson();
                uzer user = gson.fromJson(json, uzer.class);
                
                SessionManager.setId( user.getId() );
                SessionManager.setUserName( user.getNom() );
                SessionManager.setEmail( user.getEmail() );
                SessionManager.setPassowrd( user.getPassword() );
                
                
                System.out.println(" aaa " + user );
                
                
                
                //
                
                
                
                
                
                
                 new WalkthruForm(rs).show();
                /*
                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                
             
                //Session 
                float id = Float.parseFloat(user.get("id").toString());
                SessionManager.setId((int)id);
                
                SessionManager.setPassowrd(user.get("password").toString());
                SessionManager.setUserName(user.get("nom").toString());
                SessionManager.setEmail(user.get("email").toString());
                

                System.out.println(" ahi currnt user ==>"+SessionManager.getEmail()+","+SessionManager.getPassowrd()+","+SessionManager.getUserName());
                */

                    }
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public static void EditUser(int id, String firstName, String lastName, String Password, String Email) {
        String url = Statics.BASE_URL+"/updateUsrJSON/mod?id="+id+"&nom="+firstName+"&prenom="+lastName+"&password="+Password+"&email="+Email;
        MultipartRequest req = new MultipartRequest();
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("id",String.valueOf(SessionManager.getId()));
        req.addArgument("nom",firstName);
        req.addArgument("prenom",lastName);
        req.addArgument("password",Password);
        req.addArgument("email",Email);
        System.out.println(Email);
        req.addResponseListener((response)-> {
            
            byte[] data = (byte[]) response.getMetaData();
            String s = new String(data);
            System.out.println(s);
            //if(s.equals("success")){}
            //else {
                //Dialog.show("Erreur","Echec de modification", "Ok", null);
            //}
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

     
    public boolean deleteUser(int id ) {
        String url = Statics.BASE_URL +"/user/deleteUser?id="+(int)id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }

}
