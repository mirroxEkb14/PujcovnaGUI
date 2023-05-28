package cz.upce.fei.boop.pujcovna.gui.dialogy;

/**
 * Tato výčtová třída obsahuje názvy tlačítek a lablů v každém z dialogů.
 */
public enum DialogovySpravce {

    TLACITKO_FAJN("Fajn"),
    TLACITKO_ZRUSIT("Zrusit"),
    GENERATOR_DEFAULT_TEXT("10"),
    GENERATOR_TITULEK("Generátor"),
    GENERATOR_DEFAULTNI_HLAVICKA("Generování Motorek"),
    GENERATOR_KONTEXT("Zadejte celočíselný počet:"),
    TLACITKO_DAL("Dál"),
    TLACITKO_ZPET("Zpět"),
    EDITOR_TITULEK("Editace"),
    EDITOR_HLAVICKA("Editace Motorek"),
    EDITOR_VYBER_TYPU("Podle typu:"),
    EDITOR_VYBER_ZNACKY("Znacka"),
    EDITOR_VYBER_ZNACKY_NONE("nic"),
    EDITOR_VYBER_SPZ("SPZ"),
    EDITOR_SPZ_PROMPT("0A0 0000"),
    EDITOR_VYBER_CENY24h("Cena24h"),
    EDITOR_CENA24h_PROMPT("1000.0"),
    EDITOR_VYBER_RETRO("Hmotnost"),
    EDITOR_HMOTNOST_PROMPT("120.0"),
    EDITOR_VYBER_SPORTOVNI("Počet válců"),
    EDITOR_POCET_VALCU_PROMPT("3"),
    EDITOR_VYBER_STANDARDNI("Počet rychlostí"),
    EDITOR_POCET_RYCHLOSTI_PROMPT("5"),
    EDITOR_VYBER_TERENNI("Spotřeba paliva"),
    EDITOR_SPOTREBA_PALIVA_PROMPT("7.0"),
    TLACITKO_OK("OK"),
    TLACITKO_CANCEL("Cancel"),
    TVURCE_TITULEK("Tvůrce Motorek"),
    TVURCE_ID_LABEL("ID: %s"),
    TVURCE_VYBER_ZNACKY("Znacka:"),
    TVURCE_VYBER_SPZ("SPZ:"),
    TVURCE_SPZ_PROMPT("0A0 0000"),
    TVURCE_VYBER_CENY24h("Cena:"),
    TVURCE_CENA24h_PROMPT("1000.0"),
    TVURCE_VYBER_HMOTNOSTI("Hmotnost:"),
    TVURCE_HMOTNOST_PROMPT("120.0"),
    TVURCE_VYBER_POCTU_VALCU("Počet válců:"),
    TVURCE_POCET_VALCU_PROMPT("3"),
    TVURCE_VYBER_POCTU_RYCHLOSTI("Počet rychlostí:"),
    TVURCE_POCET_RYCHLOSTI_PROMPT("5"),
    TVURCE_VYBER_SPOTREBY_PALIVA("Spotřeba paliva:"),
    TVURCE_SPOTREBA_PALIVA_PROMPT("7.0"),
    JMENO_TXT_SOUBORU("data"),
    INPUT_DIALOG_TITULEK("Text Input Dialog"),
    INPUT_DIALOG_HLAVICKA("Okno pro uložení dat do textového souboru"),
    INPUT_DIALOG_LABEL("Zadejte jméno souboru:"),
    NAJDI_ID_TITULEK("Vstupní ID Dialog"),
    NAJDI_ID_VARIANTA("id"),
    NAJDI_ID_HLAVICKA("Najít podle ID"),
    NAJDI_ID_CONTENT("Zadejte ID Motorky:");

    private final String text;

    DialogovySpravce(String text) { this.text = text; }

    public String text() { return text; }
}
