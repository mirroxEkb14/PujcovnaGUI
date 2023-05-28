package cz.upce.fei.boop.pujcovna.gui.kontejnery;

import cz.upce.fei.boop.pujcovna.data.model.Motorka;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.gui.alerty.Alarm;
import cz.upce.fei.boop.pujcovna.gui.dialogy.DialogovySpravce;
import cz.upce.fei.boop.pujcovna.gui.dialogy.DialogovyTazatel;
import cz.upce.fei.boop.pujcovna.gui.filtry.FiltrTyp;
import cz.upce.fei.boop.pujcovna.gui.filtry.FiltrID;
import cz.upce.fei.boop.pujcovna.spravce.SpravaMotorek;
import cz.upce.fei.boop.pujcovna.util.vyjimky.KolekceException;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Třída reprezentuje pravou část hlavního okénka, což je {@code VBox}, obsahující všechny tlačítka a
 * lably.
 * <br>
 * Zajistí vytvoření a nastaví chování pro každé tlačítko a kontejner.
 */
public class PrikazovyPanel extends VBox {

    /**
     * Deklarace všech tlačítek.
     */
    private Button btnPrvni, btnDalsi, btnPredchozi, btnPosledni, btnGeneruj, btnEdituj,
            btnVyjmi, btnZrus, btnZalohuj, btnObnov, btnUloz, btnNacti;
    private ChoiceBox<String> cbNovy, cbNajdi;
    private ChoiceBox<TypMotorky> cbFiltr;

    /**
     * Odřádkování pro každý jednotlivý {@code VBox} kontejneru, sloužící k seřazení tlačítek.
     */
    private static final int INSETS_HORNI = 15;
    private static final int INSETS_DOLNI = 0;
    private static final int INSETS_PRAVY = 12;
    private static final int INSETS_LEVY = 12;
    private static final int SPACING = 10;

    /**
     * Ukazatelé pro ověření indexů a velikosti seznamu.
     */
    private static final int NULOVY_UKAZATEL_VELIKOSTI = 0;
    private static final int INDEXOVY_ZVETSOVAK = 1;

    /**
     * Int proměnná je určena pro to, aby při stisknutí tlačítka z kontejneru {@code Prochazeni}
     * seznam dokázal určit id aktuálního prvku a vyznačit ho v seznamu dat.
     */
    private static int citac = 0;

    /**
     * Booleova proměnná slouží k rozpoznávání, zda uživatel stisknul nějaké tlačítko z kontejneru
     * {@code Prochazeni} anebo vybral prvek přímo v {@code ListView}.
     */
    private static boolean prochazeniClicked;

    /**
     * Instance na spojový seznam dat.
     */
    private final SpravaMotorek motorky = SpravaMotorek.getInstance();

    /**
     * Instance na {@code ListView} seznam, který vidí uživatel.
     */
    private final SeznamPanel seznamReference;

    /**
     * Deklarace a inicializace filtrů.
     */
    private FiltrTyp filtr = new FiltrTyp(TypMotorky.NONE_FILTR);
    private FiltrID filtrID;

    /**
     * Konstruktor třídy zajistí inicializaci hodnot privátních instančních proměnných a
     * provede postupné vytvoření kontejnerů pro uživatelskou navigaci.
     *
     * @param seznam Instance na {@code ListView} seznam.
     */
    public PrikazovyPanel(SeznamPanel seznam) {
        seznamReference = seznam;

        inicializujGrafickePrvky();
        nastavPosluchacSeznamu();
        nastavProchazeni();
        nastavPrikazy();
        nastavProhlizeni();
        nastavSoubory();
    }

    /**
     * Inicializace všech tlačítek.
     */
    private void inicializujGrafickePrvky() {
        btnPrvni = new Button();
        btnDalsi = new Button();
        btnPredchozi = new Button();
        btnPosledni = new Button();
        btnGeneruj = new Button();
        btnEdituj = new Button();
        btnVyjmi = new Button();
        btnZrus = new Button();
        cbNovy = new ChoiceBox<>();
        cbNajdi = new ChoiceBox<>();
        cbFiltr = new ChoiceBox<>();
        btnZalohuj = new Button();
        btnObnov = new Button();
        btnUloz = new Button();
        btnNacti = new Button();
    }

    /**
     * Následující metoda a její pomocní nastaví posluchač, který reaguje na
     * každou změnu v {@code ListView} seznamu.
     */
    public void nastavPosluchacSeznamu() {
        seznamReference.getSelectionModel().selectedItemProperty().addListener((observableValue, motorka, t1) -> {
            if (prochazeniClicked) return;

            final int index = seznamReference.getSelectionModel().getSelectedIndex();
            if (index == NULOVY_UKAZATEL_VELIKOSTI && dejVelikostSeznamView() > NULOVY_UKAZATEL_VELIKOSTI) {
                nastavPrvniAction();
            } else if (index + INDEXOVY_ZVETSOVAK == dejVelikostSeznamView() && dejVelikostSeznamView() > NULOVY_UKAZATEL_VELIKOSTI) {
                nastavPosledniAction();
            } else { posunPrvek(index); }
        });
    }

    private void posunPrvek(int index) {
        try {
            vynulujCitac();
            motorky.prvni();
            for (int i = 0; i < index; i++) {
                zvysCitac();
                motorky.dalsi();
            }
        } catch (KolekceException ignored) {}

        btnEdituj.setDisable(false);
        btnVyjmi.setDisable(false);
        btnPrvni.setDisable(false);
        btnDalsi.setDisable(false);
        btnPredchozi.setDisable(false);
        btnPosledni.setDisable(false);
    }

    /**
     * Následující metody nastaví kontejner {@code Prochazeni}.
     * <br>
     * Jednotlivé pomocní metody vrácejí jednotlivá nastavená tlačítka:
     * <ol>
     * <li>{@code První}
     * <li>{@code Další}
     * <li>{@code Předchozí}
     * <li>{@code Poslední}
     * </ol>
     */
    private void nastavProchazeni() {
        final VBox vBox = new VBox();

        Label titulek = new Label(Tlacitko.TITULEK_PROCHAZENI.titulek());

        final HBox hBox1 = dejNastavenyHBox1Prochazeni();
        final HBox hBox2 = dejNastavenyHBox2Prochazeni();

        vBox.getChildren().addAll(titulek, hBox1, hBox2);
        nastavVBox(vBox);
    }

    private HBox dejNastavenyHBox1Prochazeni() {
        btnPrvni.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnPrvni.setText(Tlacitko.TLACITKO_PRVNI.titulek());
        btnPrvni.setDisable(true);
        btnPrvni.setOnAction(actionEvent -> nastavPrvniAction());

        btnDalsi.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnDalsi.setText(Tlacitko.TLACITKO_DALSI.titulek());
        btnDalsi.setDisable(true);
        btnDalsi.setOnAction(actionEvent -> nastavDalsiAction());

        HBox hBox1 = new HBox(btnPrvni, btnDalsi);
        hBox1.setSpacing(Tlacitko.HODNOTA_HORIZONTALNIHO_SPACING);

        return hBox1;
    }

    private void nastavPrvniAction() {
        try {
            vynulujCitac();
            motorky.prvni();
            nastavVybranyPrvek();
            btnPrvni.setDisable(true);
            btnPredchozi.setDisable(true);
            overProPrvni();
        } catch (KolekceException ex) {
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_PRVNI.zprava());
        }
    }

    private void overProPrvni() {
        if (btnVyjmi.isDisabled()) btnVyjmi.setDisable(false);
        if (btnEdituj.isDisabled()) btnEdituj.setDisable(false);
        if (jePouzeJeden()) {
            btnDalsi.setDisable(true);
            btnPosledni.setDisable(true);
            return;
        }
        if (btnDalsi.isDisabled()) btnDalsi.setDisable(false);
        if (btnPosledni.isDisabled()) btnPosledni.setDisable(false);
    }

    private void nastavDalsiAction() {
        try {
            zvysCitac();
            motorky.dalsi();
            nastavVybranyPrvek();
            overProDalsi();
        } catch (KolekceException ex) {
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_DALSI.zprava());
        }
    }

    private void overProDalsi() {
        if (btnPredchozi.isDisabled()) btnPredchozi.setDisable(false);
        if (citac + INDEXOVY_ZVETSOVAK == seznamReference.getItems().size()) btnPosledni.setDisable(true);
        if (btnPrvni.isDisabled()) btnPrvni.setDisable(false);
        if (btnVyjmi.isDisabled()) btnVyjmi.setDisable(false);
        if (btnEdituj.isDisabled()) btnEdituj.setDisable(false);
        if (seznamReference.getSelectionModel().getSelectedIndex() + INDEXOVY_ZVETSOVAK == seznamReference.getItems().size())
            btnDalsi.setDisable(true);
    }

    private void nastavVybranyPrvek() {
        prochazeniClicked = true;
        seznamReference.getSelectionModel().select(citac);
        prochazeniClicked = false;
    }

    private HBox dejNastavenyHBox2Prochazeni() {
        btnPredchozi.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnPredchozi.setText(Tlacitko.TLACITKO_PREDCHOZI.titulek());
        btnPredchozi.setDisable(true);
        btnPredchozi.setOnAction(actionEvent -> nastavPredchoziAction());

        btnPosledni.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnPosledni.setText(Tlacitko.TLACITKO_POSLEDNI.titulek());
        btnPosledni.setDisable(true);
        btnPosledni.setOnAction(actionEvent -> nastavPosledniAction());

        HBox hBox2 = new HBox(btnPredchozi, btnPosledni);
        hBox2.setSpacing(Tlacitko.HODNOTA_HORIZONTALNIHO_SPACING);

        return hBox2;
    }

    private void nastavPredchoziAction() {
        try {
            zmensiCitac();
            nastavVybranyPrvek();
            final Motorka aktualni = seznamReference.getSelectionModel().getSelectedItem();
            motorky.vratOdkaz(aktualni);
            overProPredchozi();
        } catch (KolekceException ignored) {}
    }

    private void overProPredchozi() {
        if (citac == NULOVY_UKAZATEL_VELIKOSTI) btnPredchozi.setDisable(true);
        if (btnDalsi.isDisabled()) btnDalsi.setDisable(false);
        if (btnPosledni.isDisabled()) btnPosledni.setDisable(false);
        if (btnVyjmi.isDisabled()) btnVyjmi.setDisable(false);
        if (btnEdituj.isDisabled()) btnEdituj.setDisable(false);
        if (dejAktualniIndex() == NULOVY_UKAZATEL_VELIKOSTI) btnPrvni.setDisable(true);
    }

    private void nastavPosledniAction() {
        try {
            motorky.posledni();
            nastavMaxCitac();
            nastavVybranyPrvek();
            btnPosledni.setDisable(true);
            overProPosledni();
        } catch (KolekceException ex) {
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_POSLEDNI.zprava());
        }
    }

    private void overProPosledni() {
        if (!btnDalsi.isDisabled()) btnDalsi.setDisable(true);
        if (btnPredchozi.isDisabled() && dejAktualniIndex() != NULOVY_UKAZATEL_VELIKOSTI) btnPredchozi.setDisable(false);
        if (btnPrvni.isDisabled() && !jePouzeJeden()) btnPrvni.setDisable(false);
        if (btnVyjmi.isDisabled()) btnVyjmi.setDisable(false);
        if (btnEdituj.isDisabled()) btnEdituj.setDisable(false);
        if (dejPosledniPrvek().equals(dejPrvniPrvek())) btnPrvni.setDisable(true);
    }

    private void nastavVBox(VBox vBox) {
        vBox.setPadding(new Insets(INSETS_HORNI, INSETS_PRAVY, INSETS_DOLNI, INSETS_LEVY));
        vBox.setSpacing(SPACING);
        this.getChildren().add(vBox);
    }

    /**
     * Následující metody nastaví kontejner {@code Prikazy}.
     * <br>
     * Jednotlivé pomocní metody vrácejí jednotlivá nastavená tlačítka:
     * <ol>
     * <li>{@code Generuj}
     * <li>{@code Edituj}
     * <li>{@code Vyjmi}
     * <li>{@code Zrus}
     * </ol>
     */
    private void nastavPrikazy() {
        final VBox vBox = new VBox();

        Label titulek = new Label(Tlacitko.TITULEK_PRIKAZY.titulek());

        final HBox hBox1 = dejNastavenyHBox1Prikazy();
        final HBox hBox2 = dejNastavenyHBox2Prikazy();

        vBox.getChildren().addAll(titulek, hBox1, hBox2);
        nastavVBox(vBox);
    }

    private HBox dejNastavenyHBox1Prikazy() {
        btnGeneruj.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnGeneruj.setText(Tlacitko.TLACITKO_GENERUJ.titulek());
        btnGeneruj.setOnAction(actionEvent -> nastavGenerujAction());

        btnEdituj.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnEdituj.setText(Tlacitko.TLACITKO_EDITUJ.titulek());
        btnEdituj.setDisable(true);
        btnEdituj.setOnAction(actionEvent -> nastavEditujAction());

        HBox hBox1 = new HBox(btnGeneruj, btnEdituj);
        hBox1.setSpacing(Tlacitko.HODNOTA_HORIZONTALNIHO_SPACING);

        return hBox1;
    }

    private void nastavGenerujAction() {
        final Optional<String> vstup = DialogovyTazatel.pozadatGeneruj();
        if (DialogovyTazatel.isCancelClicked){
            DialogovyTazatel.resetIsCancelClicked();
            return;
        }
        if (vstup.isPresent()) {
            final int pocet = Integer.parseInt(vstup.get());
            motorky.generuj(pocet);
            seznamReference.obnovSeznam(motorky.dejDatovod());

            overProObnoveniSeznamu();
            return;
        }
        motorky.nahlasErrorLog(Alarm.alertErr, Alarm.CHYBNY_INTEGER.zprava());
    }

    private void nastavEditujAction() {
        try {
            final Function<Motorka, Motorka> editFunc = DialogovyTazatel::pozadatEdituj;
            final Motorka editovanaMotorka = motorky.edituj(editFunc);
            seznamReference.obnovSeznam(editovanaMotorka);
        } catch (KolekceException | NullPointerException ignored) {}
    }

    private void overProObnoveniSeznamu() {
        if (btnPrvni.isDisabled()) btnPrvni.setDisable(false);
        if (btnPosledni.isDisabled()) btnPosledni.setDisable(false);
        if (cbNajdi.isDisabled()) cbNajdi.setDisable(false);
        if (cbFiltr.isDisabled()) cbFiltr.setDisable(false);
        if (!btnDalsi.isDisabled()) btnDalsi.setDisable(true);
        if (!btnPredchozi.isDisabled()) btnPredchozi.setDisable(true);
        if (!btnEdituj.isDisabled()) btnEdituj.setDisable(true);
        if (!btnVyjmi.isDisabled()) btnVyjmi.setDisable(true);
        if (btnZrus.isDisabled()) btnZrus.setDisable(false);
        if (btnZalohuj.isDisabled()) btnZalohuj.setDisable(false);
        if (btnUloz.isDisabled()) btnUloz.setDisable(false);
    }

    private HBox dejNastavenyHBox2Prikazy() {
        btnVyjmi.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnVyjmi.setText(Tlacitko.TLACITKO_VYJMI.titulek());
        btnVyjmi.setDisable(true);
        btnVyjmi.setOnAction(actionEvent -> nastavVyjmiAction());

        btnZrus.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnZrus.setText(Tlacitko.TLACITKO_ZRUS.titulek());
        btnZrus.setDisable(true);
        btnZrus.setOnAction(actionEvent -> nastavZrusAction());

        HBox hBox2 = new HBox(btnVyjmi, btnZrus);
        hBox2.setSpacing(Tlacitko.HODNOTA_HORIZONTALNIHO_SPACING);

        return hBox2;
    }

    private void nastavVyjmiAction() {
        try {
            motorky.vyjmi();
            seznamReference.getItems().remove(dejAktualniIndex());
            seznamReference.getSelectionModel().clearSelection();
            btnVyjmi.setDisable(true);
            overProVyjmi();
        } catch (KolekceException ex) {
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_VYJMI.zprava());
        }
    }

    private void overProVyjmi() {
        if (!btnDalsi.isDisabled()) btnDalsi.setDisable(true);
        if (!btnPredchozi.isDisabled()) btnPredchozi.setDisable(true);
        if (!btnEdituj.isDisabled()) btnEdituj.setDisable(true);
        if (dejVelikostSeznamView() == NULOVY_UKAZATEL_VELIKOSTI) {
            if (!btnPrvni.isDisabled()) btnPrvni.setDisable(true);
            if (!btnPosledni.isDisabled()) btnPosledni.setDisable(true);
            if (!btnZrus.isDisabled()) btnZrus.setDisable(true);
            if (!cbNajdi.isDisabled()) cbNajdi.setDisable(true);
            if (!cbFiltr.isDisabled()) cbFiltr.setDisable(true);
            if (!btnZalohuj.isDisabled()) btnZalohuj.setDisable(true);
            if (!btnUloz.isDisabled()) btnUloz.setDisable(true);
            return;
        }
        if (btnPrvni.isDisabled()) btnPrvni.setDisable(false);
        if (btnPosledni.isDisabled()) btnPosledni.setDisable(false);
    }

    private void nastavZrusAction() {
        motorky.zrus();
        seznamReference.getItems().clear();
        btnZrus.setDisable(true);
        overProZrus();
    }

    private void overProZrus() {
        if (!btnPrvni.isDisabled()) btnPrvni.setDisable(true);
        if (!btnDalsi.isDisabled()) btnDalsi.setDisable(true);
        if (!btnPredchozi.isDisabled()) btnPredchozi.setDisable(true);
        if (!btnPosledni.isDisabled()) btnPosledni.setDisable(true);
        if (!btnEdituj.isDisabled()) btnEdituj.setDisable(true);
        if (!btnVyjmi.isDisabled()) btnVyjmi.setDisable(true);
        if (!cbNajdi.isDisabled()) cbNajdi.setDisable(true);
        if (!cbFiltr.isDisabled()) cbFiltr.setDisable(true);
        if (!btnZalohuj.isDisabled()) btnZalohuj.setDisable(true);
        if (!btnUloz.isDisabled()) btnUloz.setDisable(true);
    }

    /**
     * Následující metody nastaví kontejner {@code Prohlizeni}.
     * <br>
     * Jednotlivé pomocní metody vrácejí jednotlivá nastavená tlačítka:
     * <ol>
     * <li>{@code Novy}
     * <li>{@code Najdi}
     * <li>{@code Filtr}
     * </ol>
     */
    private void nastavProhlizeni() {
        final VBox vBox = new VBox();

        Label titulek = new Label(Tlacitko.TITULEK_PROHLIZENI.titulek());

        final HBox hbox = dejNastavenyHBoxProhlizeni();
        nastavFiltrProhlizeni();

        vBox.getChildren().addAll(titulek, hbox, cbFiltr);
        nastavVBox(vBox);
    }

    private HBox dejNastavenyHBoxProhlizeni() {
        cbNovy.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        cbNovy.getItems().addAll(
                TypMotorky.RETRO_MOTORKA.nazev(),
                TypMotorky.SPORTOVNI_MOTORKA.nazev(),
                TypMotorky.STANDARDNI_MOTORKA.nazev(),
                TypMotorky.TERENNI_MOTORKA.nazev(),
                Tlacitko.CHOICEBOX_NOVY.titulek()
        );
        cbNovy.getSelectionModel().selectLast();
        cbNovy.setOnAction(actionEvent -> nastavNovyAction());

        cbNajdi.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        cbNajdi.getItems().addAll(
                DialogovySpravce.NAJDI_ID_VARIANTA.text(),
                Tlacitko.CHOICEBOX_NAJDI.titulek()
        );
        cbNajdi.getSelectionModel().selectLast();
        cbNajdi.setOnAction(actionEvent -> nastavNajdiAction());
        cbNajdi.setDisable(true);

        HBox hBox = new HBox(cbNovy, cbNajdi);
        hBox.setSpacing(Tlacitko.HODNOTA_HORIZONTALNIHO_SPACING);

        return hBox;
    }

    private void nastavFiltrProhlizeni() {
        cbFiltr.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        Stream.of(TypMotorky.values()).forEach(t -> cbFiltr.getItems().add(t));
        cbFiltr.getSelectionModel().selectLast();
        cbFiltr.setOnAction(actionEvent -> nastavFiltrAction());
        cbFiltr.setDisable(true);
    }

    private void nastavNovyAction() {
        final String vstup = cbNovy.getSelectionModel().getSelectedItem();
        final int posledniID = seznamReference.getItems().size() + INDEXOVY_ZVETSOVAK;
        if (vstup.equals(Tlacitko.CHOICEBOX_NOVY.titulek()))
            return;
        final Motorka novaMotorka = DialogovyTazatel.pozadatNovy(posledniID, vstup);
        if (novaMotorka != null)  {
            seznamReference.pridejNovy(novaMotorka);
            motorky.novy(novaMotorka);
            overProNovy();
        }
        cbNovy.getSelectionModel().select(Tlacitko.CHOICEBOX_NOVY.titulek());
    }

    private void overProNovy() {
        if (btnPrvni.isDisabled()) btnPrvni.setDisable(false);
        if (btnPosledni.isDisabled()) btnPosledni.setDisable(false);
        if (btnZrus.isDisabled()) btnZrus.setDisable(false);
        if (cbNajdi.isDisabled()) cbNajdi.setDisable(false);
        if (cbFiltr.isDisabled()) cbFiltr.setDisable(false);
        if (btnZalohuj.isDisabled()) btnZalohuj.setDisable(false);
        if (btnUloz.isDisabled()) btnUloz.setDisable(false);
    }

    private void nastavNajdiAction() {
        if (jeDefault()) {
            seznamReference.obnovSeznam(motorky.dejDatovod());
            overProObnoveniSeznamu();
            return;
        }
        final int aktualniIndex = seznamReference.getSelectionModel().getSelectedIndex();
        final Optional<String> odpoved = DialogovyTazatel.pozadatNajdi(String.valueOf(aktualniIndex));
        final String vstupniID = odpoved.get();
        if (DialogovyTazatel.jeValidniInteger(vstupniID)) {
            filtrID = new FiltrID(vstupniID);
            final Stream<Motorka> datovod = motorky.dejDatovod().filter(t -> filtrID.test(String.valueOf(t.getId())));
            seznamReference.obnovSeznam(datovod);
            overProFiltry();
            return;
        }
        motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_NAJDI.zprava());
    }

    private boolean jeDefault() { return cbNajdi.getValue().equalsIgnoreCase(Tlacitko.CHOICEBOX_NAJDI.titulek()); }

    private void nastavFiltrAction() {
        final TypMotorky typ = cbFiltr.getValue();
        filtr = new FiltrTyp(typ);
        final Stream<Motorka> datovod = motorky.dejDatovod().filter(t -> filtr.test(t.getTyp()));
        seznamReference.obnovSeznam(datovod);

        overProFiltry();
    }

    private void overProFiltry() {
        if (dejVelikostSeznamView() == NULOVY_UKAZATEL_VELIKOSTI) {
            if (!btnPrvni.isDisabled()) btnPrvni.setDisable(true);
            if (!btnDalsi.isDisabled()) btnDalsi.setDisable(true);
            if (!btnPredchozi.isDisabled()) btnPredchozi.setDisable(true);
            if (!btnPosledni.isDisabled()) btnPosledni.setDisable(true);
            if (!btnZalohuj.isDisabled()) btnZalohuj.setDisable(true);
            if (!btnUloz.isDisabled()) btnUloz.setDisable(true);
        } else if (dejVelikostSeznamView() == INDEXOVY_ZVETSOVAK) {
            if (btnPrvni.isDisabled()) btnPrvni.setDisable(false);
            if (btnPosledni.isDisabled()) btnPosledni.setDisable(false);
        } else {
            if (btnPrvni.isDisabled()) btnPrvni.setDisable(true);
            if (btnPosledni.isDisabled()) btnPosledni.setDisable(true);
        }
    }

    /**
     * Následující metody nastaví kontejner {@code Soubory}.
     * <br>
     * Jednotlivé pomocní metody vrácejí jednotlivá nastavená tlačítka:
     * <ol>
     * <li>{@code Zalohuj}
     * <li>{@code Obnov}
     * <li>{@code Uloz}
     * <li>{@code Nacti}
     * </ol>
     */
    private void nastavSoubory() {
        final VBox vBox = new VBox();

        Label titulek = new Label(Tlacitko.TITULEK_SOUBORY.titulek());

        final HBox hBox1 = dejNastavenyHBox1Soubory();
        final HBox hBox2 = dejNastavenyHBox2Soubory();

        vBox.getChildren().addAll(titulek, hBox1, hBox2);
        nastavVBox(vBox);

    }

    private HBox dejNastavenyHBox1Soubory() {
        btnZalohuj.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnZalohuj.setText(Tlacitko.TLACITKO_ZALOHUJ.titulek());
        btnZalohuj.setDisable(true);
        btnZalohuj.setOnAction(actionEvent -> nastavZalohujAction());

        btnObnov.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnObnov.setText(Tlacitko.TLACITKO_OBNOV.titulek());
        btnObnov.setOnAction(actionEvent -> nastavObnovAction());

        HBox hBox = new HBox(btnZalohuj, btnObnov);
        hBox.setSpacing(Tlacitko.HODNOTA_HORIZONTALNIHO_SPACING);

        return hBox;
    }

    private void nastavZalohujAction() {
        try {
            if (motorky.zalohuj()) {
                Alarm.alertInf.accept(Alarm.INFO_PRIKAZ_ZALOHUJ.zprava());
                return;
            }
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_ZALOHUJ.zprava());
        } catch (IOException ignored) {}
    }

    private void nastavObnovAction() {
        try {
            if (motorky.obnov()) {
                seznamReference.obnovSeznam(motorky.dejDatovod());
                overProObnoveniSeznamu();
                Alarm.alertInf.accept(Alarm.INFO_PRIKAZ_OBNOV.zprava());
                return;
            }
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_OBNOV.zprava());
        } catch (IOException ignored) {}
    }

    private HBox dejNastavenyHBox2Soubory() {
        btnUloz.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnUloz.setText(Tlacitko.TLACITKO_ULOZ.titulek());
        btnUloz.setDisable(true);
        btnUloz.setOnAction(actionEvent -> nastavUlozAction());

        btnNacti.setMinWidth(Tlacitko.DIMENZE_BUTTON);
        btnNacti.setText(Tlacitko.TLACITKO_NACTI.titulek());
        btnNacti.setOnAction(actionEvent -> nastavNactiAction());

        HBox hBox = new HBox(btnUloz, btnNacti);
        hBox.setSpacing(Tlacitko.HODNOTA_HORIZONTALNIHO_SPACING);

        return hBox;
    }

    private void nastavUlozAction() {
        try {
            final Optional<String> odpoved = DialogovyTazatel.pozadatUloz();
            if (odpoved.isPresent() && motorky.uloz(odpoved.get())) {
                Alarm.alertInf.accept(Alarm.INFO_PRIKAZ_ULOZ.zprava());
                return;
            }
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_ULOZ.zprava());
        } catch (IOException ignored) {}
    }

    private void nastavNactiAction() {
        try {
            motorky.nactiTextDoSeznamu();
            seznamReference.obnovSeznam(motorky.dejDatovod());
            Alarm.alertInf.accept(Alarm.INFO_PRIKAZ_NACTI.zprava());
            overProObnoveniSeznamu();
        } catch (IOException ex) {
            motorky.nahlasErrorLog(Alarm.alertErr, Alarm.VYJIMKA_PRIKAZ_NACTI.zprava());
        }
    }

    /**
     * Následující metody jsou pomocní pro vnitřní potřeby třídy.
     */
    private void zvysCitac() { citac++; }

    private void zmensiCitac() { citac--; }

    private void vynulujCitac() { citac = NULOVY_UKAZATEL_VELIKOSTI; }

    private void nastavMaxCitac() { citac = seznamReference.getItems().size() - INDEXOVY_ZVETSOVAK; }

    private int dejVelikostSeznamView() { return seznamReference.getItems().size(); }

    private int dejAktualniIndex() { return seznamReference.getSelectionModel().getSelectedIndex(); }

    private Motorka dejPrvniPrvek() { return seznamReference.getItems().get(NULOVY_UKAZATEL_VELIKOSTI); }

    private Motorka dejPosledniPrvek() {
        final int posledniIndex = seznamReference.getItems().size() - INDEXOVY_ZVETSOVAK;
        return seznamReference.getItems().get(posledniIndex);
    }

    private boolean jePouzeJeden() { return dejVelikostSeznamView() == INDEXOVY_ZVETSOVAK; }
}
