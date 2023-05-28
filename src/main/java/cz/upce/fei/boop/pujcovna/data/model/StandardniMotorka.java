package cz.upce.fei.boop.pujcovna.data.model;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;

import java.util.Locale;

public final class StandardniMotorka extends Motorka {

    private final int pocetRychlosti;

    public StandardniMotorka(Znacka znacka, String spz, double cena24h, int pocetRychlosti) {
        super(TypMotorky.STANDARDNI_MOTORKA, znacka, spz, cena24h);

        this.pocetRychlosti = pocetRychlosti;
    }

    @Override
    public String toString() {
        return "id=" + super.getId() +
                ", typ=" + super.getTyp().nazev() +
                ", znacka=" + super.getZnacka().nazev() +
                ", SPZ=" + super.getSpz() +
                ", cena24h=" + String.format(Locale.ENGLISH, "%5.1f", super.getCena24h()) +
                ", pocetRychlosti=" + this.pocetRychlosti;
    }

    public int getPocetRychlosti() { return this.pocetRychlosti; }
}
