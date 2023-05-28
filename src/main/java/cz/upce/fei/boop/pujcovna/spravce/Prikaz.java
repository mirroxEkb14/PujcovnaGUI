package cz.upce.fei.boop.pujcovna.spravce;

public enum Prikaz {

    HELP("help, h"),
    NOVY("novy, no"),
    NAJDI("najdi, na, n"),
    ODEBER("odeber, od"),
    DEJ("dej"),
    EDITUJ("edituj, edit"),
    VYJMI("vyjmi"),
    PRVNI("prvni, pr"),
    DALSI("dalsi, da"),
    POSLEDNI("posledni, po"),
    POCET("pocet"),
    OBNOV("obnov"),
    ZALOHUJ("zalohuj"),
    VYPIS("vypis"),
    NACTI_TEXT("nactitext, nt"),
    ULOZ_TEXT("uloztext, ut"),
    GENERUJ("generuj, g"),
    ZRUS("zrus"),
    EXIT("exit");

    private static final String ODDELOVAC = ", ";
    private final String varianta;

    Prikaz(String varianta) { this.varianta = varianta; }

    public static String getOddelovac() { return ODDELOVAC; }

    @Override
    public String toString() { return varianta; }
}
