package cz.upce.fei.boop.pujcovna.generator;

import cz.upce.fei.boop.pujcovna.data.model.*;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import cz.upce.fei.boop.pujcovna.kolekce.Seznam;

public final class Generator implements Gen<Motorka> {

    @Override
    public void generuj(Seznam<Motorka> motorky, int pocet) {
        for (int i = 0; i < pocet; i++) {
            final int nahodneCislo = Gen.super.dejNahodneCislo();
            switch (nahodneCislo) {
                case TypMotorky.RETRO_CISLO -> motorky.vlozPosledni(
                    new RetroMotorka(Znacka.BMW, Skladiste.RETRO_SPZ, Skladiste.RETRO_CENA, Skladiste.RETRO_HMOTNOST)
                );
                case TypMotorky.SPORTOVNI_CISLO -> motorky.vlozPosledni(
                        new SportovniMotorka(Znacka.BMW, Skladiste.SPORTOVNI_SPZ, Skladiste.SPORTOVNI_CENA, Skladiste.SPORTOVNI_POCET_VALCU)
                );
                case TypMotorky.STANDARDNI_CISLO -> motorky.vlozPosledni(
                        new StandardniMotorka(Znacka.HONDA, Skladiste.STANDARDNI_SPZ, Skladiste.STANDARDNI_CENA, Skladiste.STANDARDNI_POCET_RYCHLOSTI)
                );
                case TypMotorky.TERENNI_CISLO -> motorky.vlozPosledni(
                        new TerenniMotorka(Znacka.HONDA, Skladiste.TERENNI_SPZ, Skladiste.TERENNI_CENA, Skladiste.TERENNI_SPOTREBA_PALIVA)
                );
            }
        }
    }

    /**
     * Skladiště inicializačných hodnot pro generování {@code Motorek}.
     */
    private static final class Skladiste {
        static final String RETRO_SPZ = "3H0 3333";
        static final String SPORTOVNI_SPZ = "1AS 1111";
        static final String STANDARDNI_SPZ = "4B5 4588";
        static final String TERENNI_SPZ = "2Z9 7777";

        static final int RETRO_CENA = 1300;
        static final int SPORTOVNI_CENA = 1200;
        static final int STANDARDNI_CENA = 100;
        static final int TERENNI_CENA = 1100;

        static final double RETRO_HMOTNOST = 120;
        static final int SPORTOVNI_POCET_VALCU = 1;
        static final int STANDARDNI_POCET_RYCHLOSTI = 5;
        static final double TERENNI_SPOTREBA_PALIVA = 4;
    }
}
