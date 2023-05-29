package cz.upce.fei.boop.pujcovna.generator;

import cz.upce.fei.boop.pujcovna.kolekce.Seznam;

import java.util.Random;

@FunctionalInterface
public interface Gen<E> {

    int IDENTIFIKATOR_MIN = 1;
    int POCET_DRUHU_MOTOREK = 4;

    /**
     * Metoda náhodně generuje data podle počátečné kapacity seznamu.
     */
    void generuj(Seznam<E> seznam, int pocet);

    /**
     * @return náhodné číslo od 1 včetně do 5 nevčetně.
     */
    default int dejNahodneCislo() {
        return new Random().nextInt(IDENTIFIKATOR_MIN, POCET_DRUHU_MOTOREK + 1);
    }
}
