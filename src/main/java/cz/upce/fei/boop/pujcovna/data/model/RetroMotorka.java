package cz.upce.fei.boop.pujcovna.data.model;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;

import java.util.Locale;

public final class RetroMotorka extends Motorka {

    private final double hmotnost;

    public RetroMotorka(Znacka znacka, String spz, double cena24h, double hmotnost) {
        super(TypMotorky.RETRO_MOTORKA, znacka, spz, cena24h);

        this.hmotnost = hmotnost;
    }

    @Override
    public String toString() {
        return "id=" + super.getId() +
                ", typ=" + super.getTyp().nazev() +
                ", znacka=" + super.getZnacka().nazev() +
                ", SPZ=" + super.getSpz() +
                ", cena24h=" + String.format(Locale.ENGLISH, "%5.1f", super.getCena24h()) +
                ", hmotnost=" + String.format(Locale.ENGLISH, "%5.1f", this.hmotnost);
    }

    public double getHmotnost() { return this.hmotnost; }
}
