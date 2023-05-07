/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.employee;

import com.athlon.entities.Employee;
import com.athlon.services.EmployeeService;
import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author Houssem Charef
 */
public class ShowAll extends Form {

    public static Employee currentEmployee = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Button addBtn;
    Label cinLabel, nomLabel, Niveau_coursLabel, prenomLabel, etatLabel, SalaireLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Employee", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();

        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");

        this.add(addBtn);
        ArrayList<Employee> listEmployee = EmployeeService.getInstance().getAll();
        if (listEmployee.size() > 0) {
            for (Employee employee : listEmployee) {
                this.add(makeEmployeeModel(employee));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentEmployee = null;
            new Manage(this).show();
        });

    }

    private Component makeEmployeeModel(Employee employee) {

        ImageViewer imageIV;

        Container employeeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        employeeModel.setUIID("containerRounded");

        cinLabel = new Label("cin : " + employee.getCin());
        cinLabel.setUIID("labelDefault");
        nomLabel = new Label("nom : " + employee.getNom());
        nomLabel.setUIID("labelDefault");
        prenomLabel = new Label("prenom : " + employee.getPrenom());
        prenomLabel.setUIID("labelDefault");
        etatLabel = new Label("etat : " + employee.getEtat());
        SalaireLabel = new Label("salaire : " + employee.getSalaire());
        SalaireLabel.setUIID("labelDefault");

        if (employee.getImg_employe() != null) {
            String url = employee.getImg_employe();
            Image image = URLImage.createToStorage(
                    EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                    url,
                    url,
                    URLImage.RESIZE_SCALE
            );
            imageIV = new ImageViewer(image);
        } else {
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
        }
        imageIV.setFocusable(false);
        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce exercice ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = EmployeeService.getInstance().delete(employee.getId());
                if (responseCode == 200) {
                    currentEmployee = null;
                    dlg.dispose();
                    employeeModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du Exercice. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        employeeModel.addAll(
                imageIV,
                cinLabel,
                nomLabel,
                prenomLabel,
                etatLabel,
                SalaireLabel,
                btnsContainer
        );

        return employeeModel;
    }
}
