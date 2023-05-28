package cz.upce.fei.boop.pujcovna.gui.dialogy;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 * Tato třída reprezentuje okénko generátoru {@code Motorek}, kde uživatel potřebuje
 * zadat počet prvků pro generování.
 */
public class GeneratorDialog extends TextInputDialog {

    public GeneratorDialog() {
        super(DialogovySpravce.GENERATOR_DEFAULT_TEXT.text());
        nastavDialog();
    }

    private void nastavDialog() {
        this.setTitle(DialogovySpravce.GENERATOR_TITULEK.text());
        this.setHeaderText(DialogovySpravce.GENERATOR_DEFAULTNI_HLAVICKA.text());
        this.setContentText(DialogovySpravce.GENERATOR_KONTEXT.text());
        this.setResizable(false);
        ((Button) this.getDialogPane().lookupButton(ButtonType.OK)).setText(DialogovySpravce.TLACITKO_FAJN.text());
        ((Button) this.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(DialogovySpravce.TLACITKO_ZRUSIT.text());
    }
}
