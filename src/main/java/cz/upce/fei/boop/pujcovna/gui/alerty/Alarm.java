package cz.upce.fei.boop.pujcovna.gui.alerty;

import javafx.scene.control.Alert;

import java.util.function.Consumer;

/**
 * Tato výčtová třída obsahuje výpisy pro výjimky a upozornění pro uživatele.
 */
public enum Alarm {

    ALERT_ERR_TITLE("Hlášení chyby"),
    ALERT_ERR_HEADER("Výjimka"),
    ALERT_INF_TITLE("Alert"),
    ALERT_INF_HEADER("Upozornění"),
    CHYBNY_INTEGER("Musíte zadat celé císlo!"),
    CHYBNA_SPZ("Chybný formát SPZ: 0A0 0000"),
    CHYBNA_CENA24h("Chybný formát Cena24h: desetinné číslo 0.0"),
    CHYBNA_HMOTNOST("Chybný formát Hmotnosti: desetinné číslo 0.0"),
    CHYBNA_SPOTREBA_PALIVA("Chybný formát Spotřeby Paliva: desetinné číslo 0.0"),
    VYJIMKA_PRIKAZ_PRVNI("Chyba! Seznam je prázdný"),
    VYJIMKA_PRIKAZ_DALSI("Chyba! Není nastaven aktuální prvek nebo je dosazen poslední prvek v seznamu"),
    VYJIMKA_PRIKAZ_POSLEDNI("Chyba! Seznam je prázdný"),
    VYJIMKA_PRIKAZ_VYJMI("Chyba! Není nastaven aktuální prvek nebo je seznam prázdný"),
    VYJIMKA_PRIKAZ_ZALOHUJ("Seznam nebyl ulozen do binárního souboru"),
    VYJIMKA_PRIKAZ_OBNOV("Seznam nebyl obnoven z binárního souboru"),
    VYJIMKA_PRIKAZ_ULOZ("Seznam nebyl ulozen do textového souboru"),
    VYJIMKA_PRIKAZ_NACTI("Seznam nebyl nacten z textového souboru"),
    VYJIMKA_PRIKAZ_NAJDI("Musíte zadat validní celočíselný id"),
    INFO_PRIKAZ_ZALOHUJ("Seznam byl ulozen do binárního souboru"),
    INFO_PRIKAZ_OBNOV("Seznam byl obnoven z binárního souboru"),
    INFO_PRIKAZ_ULOZ("Seznam byl ulozen do textového souboru"),
    INFO_PRIKAZ_NACTI("Seznam byl nacten z textového souboru");

    public static final Consumer<String> alertErr = t -> {
        Alert alertWindow = new Alert(Alert.AlertType.ERROR, t);
        alertWindow.setTitle(ALERT_ERR_TITLE.zprava());
        alertWindow.setHeaderText(ALERT_ERR_HEADER.zprava());
        alertWindow.showAndWait();
    };

    public static final Consumer<String> alertInf = t -> {
        Alert alertWindow = new Alert(Alert.AlertType.INFORMATION, t);
        alertWindow.setTitle(ALERT_INF_TITLE.zprava());
        alertWindow.setHeaderText(ALERT_INF_HEADER.zprava());
        alertWindow.showAndWait();
    };

    private final String zprava;

    Alarm(String zprava) { this.zprava = zprava; }

    public String zprava() { return zprava; }
}
