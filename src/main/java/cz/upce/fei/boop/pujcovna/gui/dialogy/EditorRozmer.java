package cz.upce.fei.boop.pujcovna.gui.dialogy;

/**
 * Tato výčtová třída obsahuje hodnoty řádků a sloupců pro {@code GridPane}
 * dialogu {@code Edituj}.
 */
public enum EditorRozmer {

    PRVNI_SLOUPEC(0),
    DRUHY_SLOUPEC(1),
    RADEK_HORNI_CASTI(0),
    SLOUPEC_LABEL_ZNACKY(0),
    RADEK_LABEL_ZNACKY(1),
    SLOUPEC_FIELD_ZNACKY(1),
    RADEK_FIELD_ZNACKY(1),
    RADEK_SPZ(2),
    RADEK_CENY24H(3),
    RADEK_HMOTNOSTI(4),
    RADEK_POCTU_VALCU(5),
    RADEK_POCTU_RYCHLOSTI(6),
    RADEK_SPOTREBY_PALIVA(7);

    private final int index;

    EditorRozmer(int index) { this.index = index; }

    public int index() { return index; }
}
