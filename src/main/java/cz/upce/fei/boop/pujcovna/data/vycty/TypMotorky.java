package cz.upce.fei.boop.pujcovna.data.vycty;

public enum TypMotorky {

    RETRO_MOTORKA("retro"),
    SPORTOVNI_MOTORKA("sportbike"),
    STANDARDNI_MOTORKA("standardni"),
    TERENNI_MOTORKA("terenni"),
    NONE_FILTR("Filtr");

    /**
     * Identifikátory pro každý druh motorky.
     */
    public static final int RETRO_CISLO = 1;
    public static final int SPORTOVNI_CISLO = 2;
    public static final int STANDARDNI_CISLO = 3;
    public static final int TERENNI_CISLO = 4;

    private final String nazev;

    TypMotorky(String nazev){ this.nazev = nazev; }

    public String nazev() { return nazev; }

    public static TypMotorky dejPodleNazvu(String nazev) {
        for (TypMotorky typ : TypMotorky.values()) {
            if (typ.nazev.equalsIgnoreCase(nazev))
                return typ;
        }
        return null; // nedosažitelný kod
    }

    @Override
    public String toString() {
        return this.nazev;
    }
}
