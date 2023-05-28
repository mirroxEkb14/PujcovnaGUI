package cz.upce.fei.boop.pujcovna.data.vycty;

/**
 * Identifikátory pro každý atribut každé Motorky.
 * Příkaz "Edituj".
 */
public enum Atribut {

    ID(1),
    TYP(2),
    ZNACKA(3),
    SPZ(4),
    CENA24H(5),
    HMOTNOST(6),
    POCET_VALCU(7),
    POCET_RYCHLOSTI(8),
    SPOTREBA_PALIVA(9);

    private final int identifikator;

    Atribut(int identifikator) { this.identifikator = identifikator; }

    public static int getPocetAtributu() { return Atribut.values().length; }

    public int identifikator() { return identifikator; }
}
