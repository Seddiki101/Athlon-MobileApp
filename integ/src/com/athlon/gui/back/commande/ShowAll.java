package com.athlon.gui.back.commande;

import com.athlon.entities.Commande;
import com.athlon.services.CommandeService;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Commande currentCommande = null;
    Label idLabel, UserLabel, dateLabel, statutLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Commande", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Commande> listCommandes = CommandeService.getInstance().getAll();
        if (listCommandes.size() > 0) {
            for (Commande commande : listCommandes) {
                this.add(makeCommandeModel(commande));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeCommandeModel(Commande commande) {
        Container commandeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeModel.setUIID("containerRounded");

        idLabel = new Label("id : " + commande.getIdC());
        idLabel.setUIID("labelDefault");
        dateLabel = new Label("date : " + commande.getDate());
        dateLabel.setUIID("labelDefault");
        UserLabel = new Label("user : " + commande.getIdUser());
        UserLabel.setUIID("labelDefault");
        statutLabel = new Label("statut : " + commande.getStatut());
        statutLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        itemsBtn = new Button("Items");
        itemsBtn.setUIID("buttonMain");
        itemsBtn.addActionListener(action -> {
            currentCommande = commande;
            new com.athlon.back.commandeItems.ShowAll(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce commande ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CommandeService.getInstance().delete(commande.getIdC());
                if (responseCode == 200) {
                    currentCommande = null;
                    dlg.dispose();
                    commandeModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du commande. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.CENTER, itemsBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        commandeModel.addAll(
                idLabel,
                dateLabel,
                UserLabel,
                statutLabel,
                btnsContainer
        );

        return commandeModel;
    }
}
