package cz.upce.fei.boop.pujcovna.gui.dialogy;

/**
 * Tato výčtová třída obsahuje hodnoty řádků a sloupců pro {@code GridPane}
 * dialogu {@code Nový}.
 */
public enum TvurceRozmer {

    PRVNI_SLOUPEC(0),
    DRUHY_SLOUPEC(1),
    SLOUPEC_LABEL_ID(0),
    RADEK_LABEL_ID(0),
    RADEK_ZNACKY(1),
    RADEK_SPZ(2),
    RADEK_CENY24H(3);

    private final int index;

    TvurceRozmer(int index) { this.index = index; }

    public int index() { return index; }
}
