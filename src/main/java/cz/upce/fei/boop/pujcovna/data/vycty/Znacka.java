package cz.upce.fei.boop.pujcovna.data.vycty;

public enum Znacka {

    BMW("bmw"),
    HONDA("honda"),
    NONE_FILTR("najdi");

    /**
     * Identifikátory pro každou značku.
     */
    public static final int BMW_CISLO = 1;
    public static final int HONDA_CISLO = 2;

    private final String nazev;

    Znacka(String nazev){ this.nazev = nazev; }

    public String nazev() { return nazev; }

    public static Znacka dejPodleNazvu(String nazev) {
        for (Znacka zn : Znacka.values()) {
            if (zn.nazev.equalsIgnoreCase(nazev))
                return zn;
        }
        return null; // nedosažitelný kod
    }

    @Override
    public String toString() {
        return this.nazev;
    }
}
