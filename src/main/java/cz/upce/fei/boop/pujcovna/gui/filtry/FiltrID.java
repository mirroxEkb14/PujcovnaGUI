package cz.upce.fei.boop.pujcovna.gui.filtry;

import java.util.function.Predicate;

/**
 * Tato třída filtruje seznam prvků podle id.
 */
public class FiltrID implements Predicate<String> {

    private final String idMotorky;

    public FiltrID(String idMotorky) { this.idMotorky = idMotorky; }

    @Override
    public boolean test(String z) { return z.equalsIgnoreCase(idMotorky); }
}
