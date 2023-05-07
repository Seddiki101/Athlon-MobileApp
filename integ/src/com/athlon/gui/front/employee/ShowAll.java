/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.front.employee;

import com.athlon.entities.Cour;
import com.athlon.entities.Employee;
import com.athlon.services.EmployeeService;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author Houssem Charef
 */
public class ShowAll extends Form {

    public static Cour currentEmployee = null;
    Resources theme = UIManager.initFirstTheme("/theme2");
    Label cinLabel, nomLabel, Niveau_coursLabel, prenomLabel, etatLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Employee", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();

        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Employee> listEmployee = EmployeeService.getInstance().getAll();
        if (listEmployee.size() > 0) {
            for (Employee employee : listEmployee) {
                this.add(makeCommandeModel(employee));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeCommandeModel(Employee employee) {

        ImageViewer imageIV;

        Container exerciceItemModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exerciceItemModel.setUIID("containerRounded");

        cinLabel = new Label("cin : " + employee.getCin());
        cinLabel.setUIID("labelDefault");
        nomLabel = new Label("nom : " + employee.getNom());
        nomLabel.setUIID("labelDefault");
        prenomLabel = new Label("prenom : " + employee.getPrenom());
        prenomLabel.setUIID("labelDefault");

        etatLabel = new Label("etat : " + employee.getEtat());
        etatLabel.setUIID("labelDefault");

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

        exerciceItemModel.addAll(
                imageIV,
                cinLabel,
                nomLabel,
                prenomLabel,
                etatLabel
        );

        return exerciceItemModel;
    }
}
