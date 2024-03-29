package com.athon;

import com.athlon.gui.back.AccueilBack;
import com.athlon.gui.front.AccueilFront;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import static com.codename1.ui.CN.getCurrentForm;
import static com.codename1.ui.CN.updateNetworkThreadCount;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class MainApp {

    private Resources theme;
    private Form current;

    public void init(Object context) {
        updateNetworkThreadCount(3);
        Resources theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }

        new AccueilFront().show();
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}
