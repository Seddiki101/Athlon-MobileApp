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

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import entity.ServiceUtilisateur;
import java.util.Vector;

import util.PwdHasher;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
     
        

        TextField UserName = new TextField("", "First_Name", 20, TextField.ANY);
        TextField UserLname = new TextField("", "Last_Name", 20, TextField.ANY);
        TextField Email = new TextField("", "Email", 20, TextField.EMAILADDR);
        TextField Password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        /*
        TextField separate = new TextField("", "", 2, TextField.ANY);
        TextField separate2 = new TextField("", "", 2, TextField.ANY);
        separate.getDisabledStyle();
        separate2.getDisabledStyle();
        */    
        
        UserName.setSingleLineTextArea(false);
        UserLname.setSingleLineTextArea(false);
        Email.setSingleLineTextArea(false);
        Password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        
        Password.setConstraint(TextField.PASSWORD);
        
        Button next = new Button("SignUp");
        
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> new loginForm(res).show());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                
                createLineSeparator(),
                new FloatingHint(UserName),
                createLineSeparator(),
                new FloatingHint(UserLname),
                createLineSeparator(),
                new FloatingHint(Email),
                createLineSeparator(),
                new FloatingHint(Password),
                createLineSeparator(),
                new FloatingHint(confirmPassword)
                     
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((e) -> {
            
            
            ServiceUtilisateur.getInstance().signup(UserName, UserLname, Email, Password, res);
            Dialog.show("Success","account is saved","OK",null);
            new loginForm(res).show();
        });
    }
    
}
