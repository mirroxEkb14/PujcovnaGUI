package cz.upce.fei.boop.pujcovna.gui.kontejnery;

import javafx.scene.layout.HBox;

/**
 * Třída reprezentují hlavní okénko, skládající se ze dvou komponent.
 */
public class Okno extends HBox {

    private final SeznamPanel seznam;
    private final PrikazovyPanel panelPrikazu;

    public Okno(int sirka) {
        seznam = new SeznamPanel(sirka);
        panelPrikazu = new PrikazovyPanel(seznam);
        nastavMenu();
    }

    private void nastavMenu() {
        this.getChildren().addAll(seznam, panelPrikazu);
    }

    public SeznamPanel getSeznam() { return seznam; }
}
