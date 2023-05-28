package cz.upce.fei.boop.pujcovna.gui.filtry;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;

import java.util.function.Predicate;

/**
 * Filtrovací třída zajistí filtraci seznamu dat podle typu.
 */
public class FiltrTyp implements Predicate<TypMotorky> {

    private final TypMotorky typMotorky;

    public FiltrTyp(TypMotorky typMotorky) { this.typMotorky = typMotorky; }

    @Override
    public boolean test(TypMotorky t) {
        return typMotorky == TypMotorky.NONE_FILTR || t == typMotorky;
    }
}
