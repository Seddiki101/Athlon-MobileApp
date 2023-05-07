/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Form;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import entity.ServiceUtilisateur;

/**
 *
 * @author k
 */
public class loginForm extends BaseForm {
    
    //start
    public loginForm(Resources res) {
        super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
        add(BorderLayout.NORTH, new Label(res.getImage("Logo.png"), "LogoLabel"));
        
        TextField Email = new TextField("", "Email", 20, TextField.ANY);
        TextField Password = new TextField("", "Password", 20, TextField.PASSWORD);
        Email.setSingleLineTextArea(false);
        Password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        

        
        
        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Vous n'avez aucune compte?");
        
        
        
        
        
        
        Container content = BoxLayout.encloseY(
                new FloatingHint(Email),
                createLineSeparator(),
                new FloatingHint(Password),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        
        signIn.addActionListener(e -> 
        {
               ServiceUtilisateur.getInstance().signin(Email, Password, res);
               //new WalkthruForm(res).show();
               //show list des reclamation
               new ProfileForm(res).show();

           
        });
        
        
        
    }
    
    
    
    
    
    
    
    
    
  //end  
}
