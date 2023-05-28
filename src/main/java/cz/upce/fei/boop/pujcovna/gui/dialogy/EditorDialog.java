package cz.upce.fei.boop.pujcovna.gui.dialogy;

import cz.upce.fei.boop.pujcovna.data.model.Motorka;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import cz.upce.fei.boop.pujcovna.gui.vyjimky.ChybnaCestaException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Třída reprezentují dialogové okénko pro příkaz {@code Edituj}.
 */
public class EditorDialog extends Dialog<ButtonType> {

    private static final int hGapHodnota = 10;
    private static final int vGapHodnota = 10;

    private static final int INSETS_HORNI = 10;
    private static final int INSETS_DOLNI = 10;
    private static final int INSETS_PRAVY = 150;
    private static final int INSETS_LEVY = 10;

    private static final int LINE_START_X = 100;
    private static final int LINE_START_Y = 150;
    private static final int LINE_END_X = 300;
    private static final int LINE_END_Y = 150;

    private static final int HORIZONTALNI_SPACING = 10;
    private static final int VERTIKALNI_SPACING = 10;

    private final ChoiceBox<String> cbVyberTypu = new ChoiceBox<>();
    private final ChoiceBox<String> cbVyberZnacky = new ChoiceBox<>();
    private final TextField tfSPZ = new TextField();
    private final TextField tfCena24h = new TextField();
    private final TextField tfHmotnost = new TextField();
    private final TextField tfPocetValcu = new TextField();
    private final TextField tfPocetRychlosti = new TextField();
    private final TextField tfSpotrebaPaliva = new TextField();

    private static final String JMENO_IMAGE_SOUBORU = "icon_dialog.png";

    private final Consumer<TypMotorky> typEditor = e -> {
        switch (e) {
            case RETRO_MOTORKA -> nastavVyberHmotnosti();
            case SPORTOVNI_MOTORKA -> nastavVyberPoctuValcu();
            case STANDARDNI_MOTORKA -> nastavVyberPoctuRychlosti();
            case TERENNI_MOTORKA -> nastavVyberSpotrebyPaliva();
        }
        cbVyberTypu.setDisable(true);
    };

    private Node dalBtn;

    private final TypMotorky aktualniTyp;
    private final Znacka aktualniZnacka;

    public EditorDialog(Motorka aktualniPrvek) {
        aktualniTyp = aktualniPrvek.getTyp();
        aktualniZnacka = aktualniPrvek.getZnacka();

        this.setTitle(DialogovySpravce.EDITOR_TITULEK.text());
        this.setHeaderText(DialogovySpravce.EDITOR_HLAVICKA.text());
        try {
            this.setGraphic(new ImageView(dejImageDialogu()));
        } catch (ChybnaCestaException ignored) {}

        nastavDialog();
    }

    private void nastavDialog() {
        nastavDialogButtons();
        nastavTeloDialogu();
        this.setResizable(false);
    }

    /**
     * Metoda nastaví tlačítka {@code OK} a {@code Cancel}.
     */
    private void nastavDialogButtons() {
        ButtonType dalButton = new ButtonType(DialogovySpravce.TLACITKO_DAL.text(), ButtonBar.ButtonData.OK_DONE);
        ButtonType zpetButton = new ButtonType(DialogovySpravce.TLACITKO_ZPET.text(), ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(dalButton, zpetButton);

        dalBtn = this.getDialogPane().lookupButton(dalButton);
        dalBtn.setDisable(true);
    }

    /**
     * Metoda a její pomocní nastaví celé tělo dialogu.
     */
    private void nastavTeloDialogu() {
        final VBox root = new VBox();

        final VBox vBox = dejHorniCastDialogu();
        final GridPane gridPane = dejCelyDialog(vBox);

        root.getChildren().addAll(vBox, gridPane);
        this.getDialogPane().setContent(root);
    }

    private GridPane dejCelyDialog(VBox horniCastDialogu) {
        final GridPane grid = new GridPane();
        grid.setHgap(hGapHodnota);
        grid.setVgap(vGapHodnota);
        grid.setPadding(new Insets(INSETS_HORNI, INSETS_PRAVY, INSETS_DOLNI, INSETS_LEVY));

        final Label znackaLabel = new Label(DialogovySpravce.EDITOR_VYBER_ZNACKY.text());
        nastavVyberZnacky();
        final Label spzLabel = new Label(DialogovySpravce.EDITOR_VYBER_SPZ.text());
        nastavVyberSPZ();
        final Label cena24hLabel = new Label(DialogovySpravce.EDITOR_VYBER_CENY24h.text());
        nastavVyberCeny24h();
        typEditor.accept(aktualniTyp);
        final Label hmotnostLabel = new Label(DialogovySpravce.EDITOR_VYBER_RETRO.text());
        final Label pocetValcuLabel = new Label(DialogovySpravce.EDITOR_VYBER_SPORTOVNI.text());
        final Label pocetRychlostiLabel = new Label(DialogovySpravce.EDITOR_VYBER_STANDARDNI.text());
        final Label spotrebaPalivaLabel = new Label(DialogovySpravce.EDITOR_VYBER_TERENNI.text());

        grid.add(horniCastDialogu, EditorRozmer.PRVNI_SLOUPEC.index(), EditorRozmer.RADEK_HORNI_CASTI.index());
        grid.add(znackaLabel, EditorRozmer.SLOUPEC_LABEL_ZNACKY.index(), EditorRozmer.RADEK_LABEL_ZNACKY.index());
        grid.add(cbVyberZnacky, EditorRozmer.SLOUPEC_FIELD_ZNACKY.index(), EditorRozmer.RADEK_FIELD_ZNACKY.index());
        grid.add(spzLabel, EditorRozmer.PRVNI_SLOUPEC.index(), EditorRozmer.RADEK_SPZ.index());
        grid.add(tfSPZ, EditorRozmer.DRUHY_SLOUPEC.index(), EditorRozmer.RADEK_SPZ.index());
        grid.add(cena24hLabel, EditorRozmer.PRVNI_SLOUPEC.index(), EditorRozmer.RADEK_CENY24H.index());
        grid.add(tfCena24h, EditorRozmer.DRUHY_SLOUPEC.index(), EditorRozmer.RADEK_CENY24H.index());
        grid.add(hmotnostLabel, EditorRozmer.PRVNI_SLOUPEC.index(), EditorRozmer.RADEK_HMOTNOSTI.index());
        grid.add(tfHmotnost, EditorRozmer.DRUHY_SLOUPEC.index(), EditorRozmer.RADEK_HMOTNOSTI.index());
        grid.add(pocetValcuLabel, EditorRozmer.PRVNI_SLOUPEC.index(), EditorRozmer.RADEK_POCTU_VALCU.index());
        grid.add(tfPocetValcu, EditorRozmer.DRUHY_SLOUPEC.index(), EditorRozmer.RADEK_POCTU_VALCU.index());
        grid.add(pocetRychlostiLabel, EditorRozmer.PRVNI_SLOUPEC.index(), EditorRozmer.RADEK_POCTU_RYCHLOSTI.index());
        grid.add(tfPocetRychlosti, EditorRozmer.DRUHY_SLOUPEC.index(), EditorRozmer.RADEK_POCTU_RYCHLOSTI.index());
        grid.add(spotrebaPalivaLabel, EditorRozmer.PRVNI_SLOUPEC.index(), EditorRozmer.RADEK_SPOTREBY_PALIVA.index());
        grid.add(tfSpotrebaPaliva, EditorRozmer.DRUHY_SLOUPEC.index(), EditorRozmer.RADEK_SPOTREBY_PALIVA.index());

        return grid;
    }

    /**
     * Metoda a její pomocní vytvoří horní část dialogu.
     */
    private VBox dejHorniCastDialogu() {
        final Label typLabel = new Label(DialogovySpravce.EDITOR_VYBER_TYPU.text());
        nastavVyberTypu();
        final Line line = new Line(LINE_START_X, LINE_START_Y, LINE_END_X, LINE_END_Y);
        final HBox hBox = new HBox(typLabel, cbVyberTypu);
        hBox.setSpacing(HORIZONTALNI_SPACING);
        hBox.setAlignment(Pos.CENTER_LEFT);
        final VBox vBox = new VBox(hBox, line);
        vBox.setSpacing(VERTIKALNI_SPACING);

        return vBox;
    }

    private void nastavVyberTypu() {
        cbVyberTypu.getItems().addAll(
                TypMotorky.RETRO_MOTORKA.nazev(),
                TypMotorky.SPORTOVNI_MOTORKA.nazev(),
                TypMotorky.STANDARDNI_MOTORKA.nazev(),
                TypMotorky.TERENNI_MOTORKA.nazev()
        );
        cbVyberTypu.getSelectionModel().selectFirst();
    }

    /**
     * Výběr spotřeby paliva {@code Motorky}.
     */
    private void nastavVyberSpotrebyPaliva() {
        tfSpotrebaPaliva.setPromptText(DialogovySpravce.EDITOR_SPOTREBA_PALIVA_PROMPT.text());
        tfSpotrebaPaliva.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());
        tfSpotrebaPaliva.setDisable(false);

        vypniPodleTerenni();
    }

    /**
     * Výběr počtu rychlostí {@code Motorky}.
     */
    private void nastavVyberPoctuRychlosti() {
        tfPocetRychlosti.setPromptText(DialogovySpravce.EDITOR_POCET_RYCHLOSTI_PROMPT.text());
        tfPocetRychlosti.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());
        tfPocetRychlosti.setDisable(false);

        vypniPodleStandardni();
    }

    /**
     * Výběr počtu válců {@code Motorky}.
     */
    private void nastavVyberPoctuValcu() {
        tfPocetValcu.setPromptText(DialogovySpravce.EDITOR_POCET_VALCU_PROMPT.text());
        tfPocetValcu.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());
        tfPocetValcu.setDisable(false);

        vypniPodleSportovni();
    }

    /**
     * Výběr hmotnosti {@code Motorky}.
     */
    private void nastavVyberHmotnosti() {
        tfHmotnost.setPromptText(DialogovySpravce.EDITOR_HMOTNOST_PROMPT.text());
        tfHmotnost.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());
        tfHmotnost.setDisable(false);

        vypniPodleRetro();
    }

    /**
     * Výběr ceny {@code Motorky}.
     */
    private void nastavVyberCeny24h() {
        tfCena24h.setPromptText(DialogovySpravce.EDITOR_CENA24h_PROMPT.text());
        tfCena24h.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());
    }

    /**
     * Výběr spz {@code Motorky}.
     */
    private void nastavVyberSPZ() {
        tfSPZ.setPromptText(DialogovySpravce.EDITOR_SPZ_PROMPT.text());
        tfSPZ.textProperty().addListener((observableValue, s, t1) -> overDialogPrazdny());
    }

    /**
     * Výběr značky {@code Motorky}.
     */
    private void nastavVyberZnacky() {
        switch (aktualniZnacka) {
            case HONDA -> cbVyberZnacky.getItems().add(Znacka.BMW.nazev());
            case BMW -> cbVyberZnacky.getItems().add(Znacka.HONDA.nazev());
        }
        cbVyberZnacky.getItems().add(DialogovySpravce.EDITOR_VYBER_ZNACKY_NONE.text());
        cbVyberZnacky.getSelectionModel().selectLast();
        cbVyberZnacky.getSelectionModel().selectedItemProperty().addListener((observableValue, number, t1) -> overDialogPrazdny());
    }

    /**
     * Následující metody vypnou nebo zapnou tlačítka podle typu {@code Motorky}.
     */
    private void vypniPodleRetro() {
        final int indexTypu = TypMotorky.RETRO_CISLO - 1;
        cbVyberTypu.getSelectionModel().select(indexTypu);

        tfPocetValcu.setPromptText(null);
        tfPocetValcu.setText(null);
        tfPocetValcu.setDisable(true);

        tfPocetRychlosti.setPromptText(null);
        tfPocetRychlosti.setText(null);
        tfPocetRychlosti.setDisable(true);

        tfSpotrebaPaliva.setPromptText(null);
        tfSpotrebaPaliva.setText(null);
        tfSpotrebaPaliva.setDisable(true);
    }

    private void vypniPodleSportovni() {
        final int indexTypu = TypMotorky.SPORTOVNI_CISLO - 1;
        cbVyberTypu.getSelectionModel().select(indexTypu);

        tfHmotnost.setPromptText(null);
        tfHmotnost.setText(null);
        tfHmotnost.setDisable(true);

        tfPocetRychlosti.setPromptText(null);
        tfPocetRychlosti.setText(null);
        tfPocetRychlosti.setDisable(true);

        tfSpotrebaPaliva.setPromptText(null);
        tfSpotrebaPaliva.setText(null);
        tfSpotrebaPaliva.setDisable(true);
    }

    private void vypniPodleStandardni() {
        final int indexTypu = TypMotorky.STANDARDNI_CISLO - 1;
        cbVyberTypu.getSelectionModel().select(indexTypu);

        tfHmotnost.setPromptText(null);
        tfHmotnost.setText(null);
        tfHmotnost.setDisable(true);

        tfPocetValcu.setPromptText(null);
        tfPocetValcu.setText(null);
        tfPocetValcu.setDisable(true);

        tfSpotrebaPaliva.setPromptText(null);
        tfSpotrebaPaliva.setText(null);
        tfSpotrebaPaliva.setDisable(true);
    }

    private void vypniPodleTerenni() {
        final int indexTypu = TypMotorky.TERENNI_CISLO - 1;
        cbVyberTypu.getSelectionModel().select(indexTypu);

        tfHmotnost.setPromptText(null);
        tfHmotnost.setText(null);
        tfHmotnost.setDisable(true);

        tfPocetValcu.setPromptText(null);
        tfPocetValcu.setText(null);
        tfPocetValcu.setDisable(true);

        tfPocetRychlosti.setPromptText(null);
        tfPocetRychlosti.setText(null);
        tfPocetRychlosti.setDisable(true);
    }

    /**
     * Metoda a její pomocní ověří, zda uživatel zadal nějakou novou hodnotu anebo jsou všechny hodnoty prázdné.
     */
    private void overDialogPrazdny() {
        if (jePrazdnaZnacka() && DialogovyTazatel.jePrazdnyString(tfSPZ.getText())  &&
                DialogovyTazatel.jePrazdnyString(tfCena24h.getText()) &&
                DialogovyTazatel.jePrazdnyString(tfHmotnost.getText()) &&
                DialogovyTazatel.jePrazdnyString(tfPocetValcu.getText()) &&
                DialogovyTazatel.jePrazdnyString(tfPocetRychlosti.getText()) &&
                DialogovyTazatel.jePrazdnyString(tfSpotrebaPaliva.getText())) {
            dalBtn.setDisable(true);
            return;
        }
        dalBtn.setDisable(false);
    }

    public boolean jePrazdnaZnacka() {
        final String vybranaZnacka = cbVyberZnacky.getSelectionModel().getSelectedItem();
        final String znackaNic = DialogovySpravce.EDITOR_VYBER_ZNACKY_NONE.text();
        return (cbVyberZnacky.getValue() == null || vybranaZnacka.equals(znackaNic));
    }

    /**
     * @return Vrací obrázek dialogu.
     */
    private Image dejImageDialogu() throws ChybnaCestaException {
        final InputStream cesta = this.getClass().getClassLoader().getResourceAsStream(JMENO_IMAGE_SOUBORU);
        if (cesta == null)
            throw new ChybnaCestaException();
        return new Image(cesta);
    }

    /**
     * @return Vrací značku podle hodnoty v {@code ChoiceBox}.
     */
    public Optional<Znacka> getNovouZnacku() {
        final Znacka nalezenaZnacka = Znacka.dejPodleNazvu(cbVyberZnacky.getValue());
        return nalezenaZnacka == null ? Optional.empty() : Optional.of(nalezenaZnacka);
    }

    /**
     * Gettery třídy.
     */
    public String getNovouSPZ() { return tfSPZ.getText(); }

    public String getNovouCenu24h() { return tfCena24h.getText(); }

    public String getNovouHmotnost() { return tfHmotnost.getText(); }

    public String getNovyPocetValcu() { return tfPocetValcu.getText(); }

    public String getNovyPocetRychlosti() { return tfPocetRychlosti.getText(); }

    public String getNovouSpotrebuPaliva() { return tfSpotrebaPaliva.getText(); }
}
