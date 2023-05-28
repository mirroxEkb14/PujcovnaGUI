package cz.upce.fei.boop.pujcovna.gui.kontejnery;

/**
 * Výčtová třída {@code Tlacitko} obsahuje názvy všech tlačítek lablů v programu.
 */
public enum Tlacitko {

    TITULEK_PROCHAZENI("Procházení"),
    TLACITKO_PRVNI("První"),
    TLACITKO_DALSI("Další"),
    TLACITKO_PREDCHOZI("Předchozí"),
    TLACITKO_POSLEDNI("Poslední"),
    TITULEK_PRIKAZY("Příkazy"),
    TLACITKO_GENERUJ("Generuj"),
    TLACITKO_EDITUJ("Edituj"),
    TLACITKO_VYJMI("Vyjmi"),
    TLACITKO_ZRUS("Zruš"),
    TITULEK_PROHLIZENI("Prohlížení"),
    CHOICEBOX_NOVY("Nový"),
    TITULEK_SOUBORY("Soubory"),
    TLACITKO_ZALOHUJ("Zalohuj"),
    TLACITKO_OBNOV("Obnov"),
    TLACITKO_ULOZ("Uloz"),
    TLACITKO_NACTI("Nacti"),
    CHOICEBOX_NAJDI("Najdi");

    public static final int DIMENZE_BUTTON = 70;
    public static final int HODNOTA_HORIZONTALNIHO_SPACING = 20;

    private final String titulek;

    Tlacitko(String titulek) { this.titulek = titulek; }

    public String titulek() { return titulek; }
}
