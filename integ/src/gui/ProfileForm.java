/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entity.ServiceUtilisateur;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    public ProfileForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(true);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Button modiff = new Button("Modifier");


        Label facebook = new Label("4", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("4", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3, 
                            facebook,
                            FlowLayout.encloseCenter(
                                new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond")),
                            twitter
                    )
                )
        ));
        
        String us = SessionManager.getUserName();
        System.out.println(us);

        TextField UserName = new TextField(us);
        UserName.setUIID("TextFieldBlack");
        addStringValue("nom", UserName);
        
        TextField UserLname = new TextField(us);
        UserLname.setUIID("TextFieldBlack");
        addStringValue("prenom", UserLname);
        
        
        TextField Email = new TextField(SessionManager.getEmail(), "E-Mailo "+SessionManager.getEmail() , 20, TextField.EMAILADDR);
        Email.setUIID("TextFieldBlack");
        addStringValue("E-Maila", Email);
        
        TextField Password = new TextField(SessionManager.getPassowrd(), "Password", 20, TextField.PASSWORD);
        Password.setUIID("TextFieldBlack");
        addStringValue("Password", Password);
        

        modiff.setUIID("Edit");
        addStringValue("",modiff);
        
        modiff.addActionListener((ActionEvent edit)->{
        InfiniteProgress ip = new InfiniteProgress();
        //final Dialog ipDlg = ip.showInifinieteBlooking();
        ServiceUtilisateur.EditUser(SessionManager.getId(),UserName.getText(),UserLname.getText(), Password.getText(), Email.getText());
        SessionManager.setUserName(UserName.getText());
        SessionManager.setPassowrd(Password.getText());
        SessionManager.setEmail(Email.getText());
        Dialog.show("Succes","Modifications des coordonnees avec succes","OK",null);
       // ipDlg.dispose();
        refreshTheme();
         
    });
        

        
        

       
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
