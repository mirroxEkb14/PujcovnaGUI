package cz.upce.fei.boop.pujcovna.data.model;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;

import java.util.Locale;

public final class SportovniMotorka extends Motorka {

    private final int pocetValcu;

    public SportovniMotorka(Znacka znacka, String spz, double cena24h, int pocetValcu) {
        super(TypMotorky.SPORTOVNI_MOTORKA, znacka, spz, cena24h);

        this.pocetValcu = pocetValcu;
    }

    @Override
    public String toString() {
        return "id=" + super.getId() +
                ", typ=" + super.getTyp().nazev() +
                ", znacka=" + super.getZnacka().nazev() +
                ", SPZ=" + super.getSpz() +
                ", cena24h=" + String.format(Locale.ENGLISH, "%5.1f", super.getCena24h()) +
                ", pocetValcu=" + this.pocetValcu;
    }

    public int getPocetValcu() { return this.pocetValcu; }
}
