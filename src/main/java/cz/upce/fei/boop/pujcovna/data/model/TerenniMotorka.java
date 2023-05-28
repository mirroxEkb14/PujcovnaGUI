package cz.upce.fei.boop.pujcovna.data.model;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;

import java.util.Locale;

public final class TerenniMotorka extends Motorka {

    private final double spotrebaPaliva;

    public TerenniMotorka(Znacka znacka, String spz, double cena24h, double spotrebaPaliva) {
        super(TypMotorky.TERENNI_MOTORKA, znacka, spz, cena24h);

        this.spotrebaPaliva = spotrebaPaliva;
    }

    @Override
    public String toString() {
        return "id=" + super.getId() +
                ", typ=" + super.getTyp().nazev() +
                ", znacka=" + super.getZnacka().nazev() +
                ", SPZ=" + super.getSpz() +
                ", cena24h=" + String.format(Locale.ENGLISH, "%5.1f", super.getCena24h()) +
                ", spotrebaPaliva=" + String.format(Locale.ENGLISH, "%5.1f", this.spotrebaPaliva);
    }

    public double getSpotrebaPaliva() { return this.spotrebaPaliva; }
}
