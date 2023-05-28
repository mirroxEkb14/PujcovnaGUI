package cz.upce.fei.boop.pujcovna.data.model;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;

import java.io.ObjectStreamClass;
import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;

/**
 * KASI Souhlasim
 */
public abstract class Motorka implements Serializable {

    @Serial
    private static final long serialVersionUID = ObjectStreamClass.lookup(Motorka.class).getSerialVersionUID();

    private static final int INICIZLIZACNI_HODNOTA_CITACE = 0;
    private static final int SNIZOVACI_HODNOTA_CITACE = 1;

    private static int citac = INICIZLIZACNI_HODNOTA_CITACE;
    private final int id;
    
    private final TypMotorky typ;
    private final Znacka znacka;
    private final String spz;
    private final double cena24h;

    public Motorka(TypMotorky typ, Znacka znacka, String spz, double cena24h) {
        this.id = ++citac;
        this.typ = typ;
        this.znacka = znacka;
        this.spz = spz;
        this.cena24h = cena24h;
    }

    @Override
    public String toString() {
        return "id=" + this.id +
                ", typ=" + this.typ.nazev() +
                ", znacka=" + this.znacka +
                ", SPZ=" + this.spz +
                ", cena24h=" + String.format(Locale.ENGLISH, "%5.1f", this.cena24h);
    }

    public TypMotorky getTyp() { return typ; }

    public Znacka getZnacka() { return znacka; }

    public String getSpz() { return spz; }

    public double getCena24h() { return cena24h; }

    public int getId() {
        return id;
    }

    public static void resetPocetMotorek() { citac = INICIZLIZACNI_HODNOTA_CITACE; }

    /**
     * Příkaz {@code edituj}: když musíme vytrořit nový prvek s upravenou hodnotou
     * atributu, tak čítač se automaticky zvyšuje, ale pro uživatele by to mělo vypadat
     * tak, že on opravdu jenom upravil aktuální prvek, a proto atribut {@code id} musí
     * zustat stejný.
     * <p>
     * Příkaz {@code najdi}: během příkazu vytváříme novou {@code Motorku} s uživatelskou
     * hodnotou v nějakém atributu, a tato nová {@code Motorka} po vyhledávání není
     * již potřebná, a proto bude zahazena, jenomže {@code čítač} již bude zvětšen o
     * jedničku, což nepotřebujeme, a právě proto musíme znovu nastavit {@code čítač}.
     */
    public static void setCitac(int hodnota) { citac = hodnota; }

    /**
     * Příkaz {@code najdi}: když při vytváření nové {@code Motorky} nastavujeme uživatelskou
     * hodnotu jako {@code id}, tak {@code čítač} se automaticky zvětšuje o jednu, kvůli čemuž
     * takové porovnání nebude správné, a proto musímě zajistit, že při vytváření nové {@code Motorky}
     * její {@code id} bude to samé, které dáváme jako argument této metodě.
     */
    public static void setZmensenyID(int hodnota) { citac = --hodnota; }

    public static int getSnizovaciHodnotaCitace() { return SNIZOVACI_HODNOTA_CITACE; }
}
