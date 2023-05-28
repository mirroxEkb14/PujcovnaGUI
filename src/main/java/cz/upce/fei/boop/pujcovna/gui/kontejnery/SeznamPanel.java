package cz.upce.fei.boop.pujcovna.gui.kontejnery;

import cz.upce.fei.boop.pujcovna.data.model.Motorka;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Font;

import java.util.stream.Stream;

/**
 * Třída je potomkem {@code ListView} a je určena pro zobrazení prvků seznamu.
 * <br>
 * Obsahuje všechny potřebné metody pro vytroření a obnovení seznamu dat.
 */
public class SeznamPanel extends ListView<Motorka> {

    private static final String NAZEV_SEZNAM_FONTU = "Monospaced";
    private static final int DIMENZE_SEZNAM_FONTU = 13;
    private static final int MENSITEL_SIRKY_SEZNAMU = 220;
    private final int DIMENZE_SEZNAMU;

    public SeznamPanel(int sirka) {
        DIMENZE_SEZNAMU = sirka - MENSITEL_SIRKY_SEZNAMU;
        createSeznam();
    }

    public void obnovSeznam(Stream<Motorka> datovod) {
        this.getItems().clear();
        datovod.forEach(this.getItems()::add);
    }

    public void obnovSeznam(Motorka editovanaMotorka) {
        final int index = this.getSelectionModel().getSelectedIndex();
        this.getItems().set(index, editovanaMotorka);
    }

    public void pridejNovy(Motorka novaMotorka) { this.getItems().add(novaMotorka); }

    private void createSeznam() {
        this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.setMinWidth(DIMENZE_SEZNAMU);
        this.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Motorka item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item.toString());
                    setFont(Font.font(NAZEV_SEZNAM_FONTU, DIMENZE_SEZNAM_FONTU));
                } else {
                    setText("");
                }
            }
        });
    }
}
