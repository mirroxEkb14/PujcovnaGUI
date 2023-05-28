package cz.upce.fei.boop.pujcovna.data.model;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;

/**
 * Třída je určena pro vytváření nové instanci {@code Motorky} s vlastní
 * zadanými atributy. Používá se během příkazu {@code najdi}.
 */
public final class Klic extends Motorka {

    public Klic(TypMotorky typ, Znacka znacka, String spz, double cena24h) {
        super(typ, znacka, spz, cena24h);
    }

    public static void nastavZpetCitat(int h) { Motorka.setCitac(h);}

    public static void nastavZmensenyCitac(int h) { Motorka.setZmensenyID(h); }
}
