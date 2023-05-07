/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.athlon.gui.back.conge;

import com.athlon.entities.Conge;
import com.athlon.entities.Employee;
import com.athlon.services.CongeService;
import com.athlon.services.EmployeeService;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import java.util.ArrayList;

/**
 *
 * @author Houssem Charef
 */
public class Manage extends Form {

    Conge currentConge;

    Label dateDLabel, dateFLabel, typeLabel;
    TextField typeTF;
    Picker dateFPicker = new Picker();
    Picker dateDPicker = new Picker();
    ComboBox<Employee> employeeComboBox = new ComboBox<>();

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentConge == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        dateFPicker.setType(Display.PICKER_TYPE_DATE);
        dateDPicker.setType(Display.PICKER_TYPE_DATE);

        currentConge = ShowAll.currentConge;
        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        ArrayList<Employee> listEmployee = new ArrayList<>();
        listEmployee.addAll(EmployeeService.getInstance().getAll());
        for (int i = 0; i < listEmployee.size(); i++) {
            employeeComboBox.addItem(listEmployee.get(i));
        }

        dateDLabel = new Label("date debut : ");
        dateDLabel.setUIID("labelDefault");
        dateFLabel = new Label("date Fin : ");
        dateFLabel.setUIID("labelDefault");
        typeLabel = new Label("type : ");

        typeTF = new TextField();
        typeTF.setHint("Tapez le type");

        if (currentConge == null) {

            manageButton = new Button("Ajouter");
        } else {

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                dateDLabel, dateDPicker,
                dateFLabel, dateFPicker,
                typeLabel, typeTF,
                employeeComboBox,
                manageButton
        );
        this.addAll(container);
    }

    private void addActions() {

        if (currentConge == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    Conge c = new Conge(
                            dateDPicker.getDate().getTime() + "",
                            dateFPicker.getDate().getTime() + "",
                            typeTF.getText()
                    );
                    c.setIdEmployee(employeeComboBox.getSelectedItem().getId());
                    int responseCode = CongeService.getInstance().add(c);
                    System.out.println(c);

                    if (responseCode == 200) {
                        Dialog.show("Succés", "congee ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "errur d'ajout de congee. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
//                    int responseCode = ExcerciceService.getInstance().edit(
//                            new Exercice(
//                                    nomTF.getText(),
//                                    (int) Float.parseFloat(duree_exercicesTF.getText()),
//                                    (int) Float.parseFloat(nombre_repetitionsTF.getText()),
//                                    desc_exercicesTF.getText(),
//                                    machineTF.getText(),
//                                    image_exerciceTF.getText()
//                            )
//                    );
//                    if (responseCode == 200) {
//                        Dialog.show("Succés", "cour modifié avec succes", new Command("Ok"));
//                        showBackAndRefresh();
//                    } else {
//                        Dialog.show("Erreur", "Erreur de modification de cour. Code d'erreur : " + responseCode, new Command("Ok"));
//                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        if (typeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le type", new Command("Ok"));
            return false;
        }
        return true;
    }
}
