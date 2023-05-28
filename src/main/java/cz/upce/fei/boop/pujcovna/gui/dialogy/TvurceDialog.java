package cz.upce.fei.boop.pujcovna.gui.dialogy;

import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Třída zajistí vytvoření okna pro příkaz {@code Nový}, kde uživatel může podle
 * svých hodnot atributů vytvořit novou {@code Motorku}.
 */
public class TvurceDialog extends Dialog<ButtonType> {

    private static final int hGapHodnota = 15;
    private static final int vGapHodnota = 15;

    private static final int INSETS_HORNI = 12;
    private static final int INSETS_DOLNI = 12;
    private static final int INSETS_PRAVY = 12;
    private static final int INSETS_LEVY = 12;

    private static final int HORIZONTALNI_SPACING = 8;
    private static final int VERTIKALNI_SPACING = 15;

    private final Label lID = new Label();
    private final ChoiceBox<String> cbZnacka = new ChoiceBox<>();
    private final TextField tfSPZ = new TextField();
    private final TextField tfCena24h = new TextField();
    private final TextField tfHmotnost = new TextField();
    private final TextField tfPocetValcu = new TextField();
    private final TextField tfPocetRychlosti = new TextField();
    private final TextField tfSpotrebaPaliva = new TextField();

    private Node okBtn;
    private final int posledniID;

    private final VBox root = new VBox();
    private final GridPane grid = new GridPane();

    private TypMotorky vybranyTyp;

    /**
     * Tato implementace funkčního rozhraní {@code Function} zajistí ověření vstupní
     * hodnoty {@code String}, zda je názvem pro nějaký typ {@code Motorky}.
     * <br>
     * Vrací instanci na nalezený typ {@code Motorky}.
     */
    private final Function<String, TypMotorky> typFunction = e -> {
        for (TypMotorky typ : TypMotorky.values()) {
            if (e.equals(typ.nazev()))
                return typ;
        }
        return null; // nedosažitelný kód
    };

    /**
     * Následující implementace funkčního interfejsu {@code Consumer} kontroluje vstupní typ
     * {@code Motorky} a pak podle kontroly volá příslušní metodu.
     * <br>
     * Nic nevrací.
     */
    private final Consumer<TypMotorky> dialogConsumer = t -> {
        switch (t) {
            case RETRO_MOTORKA -> nastavRetroDialog();
            case SPORTOVNI_MOTORKA -> nastavSportovniDialog();
            case STANDARDNI_MOTORKA -> nastavStandardniDialog();
            case TERENNI_MOTORKA -> nastavTerenniDialog();
        }
        vybranyTyp = t;
        this.getDialogPane().setContent(root);
    };

    public TvurceDialog(int posledniID, String vstupniTyp) {
        final TypMotorky typ = typFunction.apply(vstupniTyp);
        this.posledniID = posledniID;

        this.setTitle(DialogovySpravce.TVURCE_TITULEK.text());

        nastavObecnyDialog();
        dialogConsumer.accept(typ);
    }


    /**
     * Následující metody nastaví obecnou (horní) část pro všechny typy {@code Motorek}.
     */
    private void nastavObecnyDialog() {
        nastavDialogButtons();
        nastavGridKomponent();
        root.setSpacing(VERTIKALNI_SPACING);
    }

    private void nastavGridKomponent() {
        grid.setHgap(hGapHodnota);
        grid.setVgap(vGapHodnota);
        grid.setPadding(new Insets(INSETS_HORNI, INSETS_PRAVY, INSETS_DOLNI, INSETS_LEVY));

        lID.setText(String.format(DialogovySpravce.TVURCE_ID_LABEL.text(), posledniID));
        final Label znackaLabel = new Label(DialogovySpravce.TVURCE_VYBER_ZNACKY.text());
        cbZnacka.getItems().addAll(Znacka.BMW.nazev(), Znacka.HONDA.nazev());
        cbZnacka.getSelectionModel().selectLast();
        final Label spzLabel = new Label(DialogovySpravce.TVURCE_VYBER_SPZ.text());
        tfSPZ.setText(DialogovySpravce.TVURCE_SPZ_PROMPT.text());
        final Label cena24hLabel = new Label(DialogovySpravce.TVURCE_VYBER_CENY24h.text());
        tfCena24h.setText(DialogovySpravce.TVURCE_CENA24h_PROMPT.text());

        grid.add(lID, TvurceRozmer.SLOUPEC_LABEL_ID.index(), TvurceRozmer.RADEK_LABEL_ID.index());
        grid.add(znackaLabel, TvurceRozmer.PRVNI_SLOUPEC.index(), TvurceRozmer.RADEK_ZNACKY.index());
        grid.add(cbZnacka, TvurceRozmer.DRUHY_SLOUPEC.index(), TvurceRozmer.RADEK_ZNACKY.index());
        grid.add(spzLabel, TvurceRozmer.PRVNI_SLOUPEC.index(), TvurceRozmer.RADEK_SPZ.index());
        grid.add(tfSPZ, TvurceRozmer.DRUHY_SLOUPEC.index(), TvurceRozmer.RADEK_SPZ.index());
        grid.add(cena24hLabel, TvurceRozmer.PRVNI_SLOUPEC.index(), TvurceRozmer.RADEK_CENY24H.index());
        grid.add(tfCena24h, TvurceRozmer.DRUHY_SLOUPEC.index(), TvurceRozmer.RADEK_CENY24H.index());
    }

    /**
     * Metoda nastaví dolní část pro {@code Retro} typ.
     */
    private void nastavRetroDialog() {
        final Label hmotnostLabel = new Label(DialogovySpravce.TVURCE_VYBER_HMOTNOSTI.text());
        tfHmotnost.setText(DialogovySpravce.TVURCE_HMOTNOST_PROMPT.text());
        tfHmotnost.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());

        final HBox hBox = dejNastavenyHBox(hmotnostLabel, tfHmotnost);
        root.getChildren().addAll(grid, hBox);
    }

    /**
     * Metoda nastaví dolní část pro {@code Sportovni} typ.
     */
    private void nastavSportovniDialog() {
        final Label pocetValcuLabel = new Label(DialogovySpravce.TVURCE_VYBER_POCTU_VALCU.text());
        tfPocetValcu.setText(DialogovySpravce.TVURCE_POCET_VALCU_PROMPT.text());
        tfPocetValcu.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());

        final HBox hBox = dejNastavenyHBox(pocetValcuLabel, tfPocetValcu);
        root.getChildren().addAll(grid, hBox);
    }

    /**
     * Metoda nastaví dolní část pro {@code Standardni} typ.
     */
    private void nastavStandardniDialog() {
        final Label pocetRychlostiLabel = new Label(DialogovySpravce.TVURCE_VYBER_POCTU_RYCHLOSTI.text());
        tfPocetRychlosti.setText(DialogovySpravce.TVURCE_POCET_RYCHLOSTI_PROMPT.text());
        tfPocetRychlosti.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());

        final HBox hBox = dejNastavenyHBox(pocetRychlostiLabel, tfPocetRychlosti);
        root.getChildren().addAll(grid, hBox);
    }

    /**
     * Metoda nastaví dolní část pro {@code Terenni} typ.
     */
    private void nastavTerenniDialog() {
        final Label spotrebaPalivaLabel = new Label(DialogovySpravce.TVURCE_VYBER_SPOTREBY_PALIVA.text());
        tfSpotrebaPaliva.setText(DialogovySpravce.TVURCE_SPOTREBA_PALIVA_PROMPT.text());
        tfSpotrebaPaliva.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());

        final HBox hBox = dejNastavenyHBox(spotrebaPalivaLabel, tfSpotrebaPaliva);
        root.getChildren().addAll(grid, hBox);
    }

    /**
     * Následující metody jsou spojeny s vytvářením hlavního okénka.
     */
    private HBox dejNastavenyHBox(Label label, TextField tf) {
        final HBox hBox = new HBox(label, tf);
        hBox.setSpacing(HORIZONTALNI_SPACING);
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

    private void nastavDialogButtons() {
        ButtonType okButton = new ButtonType(DialogovySpravce.TLACITKO_OK.text(), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType(DialogovySpravce.TLACITKO_CANCEL.text(), ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        okBtn = this.getDialogPane().lookupButton(okButton);
    }

    /**
     * Metoda ověří, zda je dialog prázdný.
     */
    private void overDialogPrazdny() {
        if ((tfSPZ.getText() == null || tfSPZ.getText().isEmpty()) &&
                (tfCena24h.getText() == null || tfCena24h.getText().isEmpty()) &&
                (tfHmotnost.getText() == null || tfHmotnost.getText().isEmpty()) &&
                (tfPocetValcu.getText() == null || tfPocetValcu.getText().isEmpty()) &&
                (tfPocetRychlosti.getText() == null || tfPocetRychlosti.getText().isEmpty()) &&
                (tfSpotrebaPaliva.getText() == null || tfSpotrebaPaliva.getText().isEmpty())) {
            okBtn.setDisable(true);
        }
        okBtn.setDisable(false);
    }

    public Znacka getCbZnacka() { return Znacka.dejPodleNazvu(cbZnacka.getValue()); }

    /**
     * Gettery.
     */
    public String getTfSPZ() { return tfSPZ.getText(); }

    public String getTfCena24h() { return tfCena24h.getText(); }

    public String getTfHmotnost() { return tfHmotnost.getText(); }

    public String getTfPocetValcu() { return tfPocetValcu.getText(); }

    public String getTfPocetRychlosti() { return tfPocetRychlosti.getText(); }

    public String getTfSpotrebaPaliva() { return tfSpotrebaPaliva.getText(); }

    public TypMotorky getVybranyTyp() { return vybranyTyp; }
}
