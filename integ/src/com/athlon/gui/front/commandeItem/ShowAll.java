package com.athlon.gui.front.commandeItem;

import com.athlon.entities.Commande;
import com.athlon.entities.CommandeItem;
import com.athlon.services.CommandeItemService;
import com.athlon.utils.Statics;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Commande currentCommande = null;
    public static CommandeItem currentCommandeItem = null;

    Label idLabel, produitLabel, quantiteLabel;
    Resources theme = UIManager.initFirstTheme("/theme2");

    public ShowAll(Form previous) {
        super("commande items", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<CommandeItem> listCommandeitem = CommandeItemService.getInstance().getAllByID(com.athlon.gui.front.mesCommande.ShowAll.currentCommande.getIdC());
        if (listCommandeitem.size() > 0) {
            for (CommandeItem commandeitem : listCommandeitem) {
                this.add(makeCommandeModel(commandeitem));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeCommandeModel(CommandeItem commandeItem) {
        ImageViewer imageIV;

        Container commandeItemModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeItemModel.setUIID("containerRounded");

        idLabel = new Label("id : " + commandeItem.getId());
        idLabel.setUIID("labelDefault");
        quantiteLabel = new Label("qantité : " + commandeItem.getQantity());
        quantiteLabel.setUIID("labelDefault");
        produitLabel = new Label("produit : " + commandeItem.getProduitBrand());
        produitLabel.setUIID("labelDefault");

        if (false) {
            String url = Statics.PRODUIT_IMAGE_URL;
//                    + cour.getImage_cours();
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

        commandeItemModel.addAll(
                imageIV,
                idLabel,
                produitLabel,
                quantiteLabel
        );

        return commandeItemModel;
    }
}
