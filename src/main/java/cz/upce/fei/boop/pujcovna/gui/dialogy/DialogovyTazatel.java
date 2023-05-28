package cz.upce.fei.boop.pujcovna.gui.dialogy;

import cz.upce.fei.boop.pujcovna.data.model.*;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import cz.upce.fei.boop.pujcovna.gui.alerty.Alarm;
import cz.upce.fei.boop.pujcovna.util.vyjimky.ChybnaHodnotaException;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Třída obsahuje statické metody, které žádají o určitou hodnotu.
 */
public class DialogovyTazatel {

    private static final String NULOVA_HODNOTA = "0";
    private static final double INICIALIZACNI_DOUBLE_HODNOTA = .0;
    private static final int INICIALIZACNI_INTEGER_HODNOTA = -1;
    public static boolean isCancelClicked;

    public static void resetIsCancelClicked() { isCancelClicked = !isCancelClicked; }

    /**
     * Příkaz {@code Generuj}.
     */
    public static Optional<String> pozadatGeneruj() {
        final Optional<String> vstup = new GeneratorDialog().showAndWait();
        if (vstup.isEmpty()){
            isCancelClicked = true;
            return Optional.empty();
        }
        return jeValidniInteger(vstup.get()) && !vstup.get().equals(NULOVA_HODNOTA)? vstup : Optional.empty();
    }

    /**
     * Příkaz {@code Edituj}.
     */
    public static Motorka pozadatEdituj(Motorka aktualniPrvek) {
        EditorDialog dialog = new EditorDialog(aktualniPrvek);
        Optional<ButtonType> odpoved = dialog.showAndWait();

        if (odpoved.isPresent() && odpoved.get().getButtonData().isDefaultButton()) {
            Motorka.setCitac(aktualniPrvek.getId() - Motorka.getSnizovaciHodnotaCitace());
            try {
                return dejEditovanouMotorku(aktualniPrvek, dialog);
            } catch (ChybnaHodnotaException ignored) {}
        }
        return null;
    }

    private static Motorka dejEditovanouMotorku(Motorka aktualniPrvek, EditorDialog dialogReference) throws ChybnaHodnotaException {
        final Znacka novaZnacka = dialogReference.getNovouZnacku().orElse(aktualniPrvek.getZnacka());
        final String novaSPZ = dejOverenouSPZ(dialogReference, aktualniPrvek.getSpz());
        final double novaCena24h = dejOverenouCenu24h(dialogReference, aktualniPrvek.getCena24h());

        return switch (aktualniPrvek.getTyp()) {
            case RETRO_MOTORKA -> new RetroMotorka(
                    novaZnacka,
                    novaSPZ,
                    novaCena24h,
                    dejOverenouHmotnost(dialogReference, ((RetroMotorka) aktualniPrvek).getHmotnost()));
            case SPORTOVNI_MOTORKA -> new SportovniMotorka(
                    novaZnacka,
                    novaSPZ,
                    novaCena24h,
                    dejOverenyPocetValcu(dialogReference, ((SportovniMotorka) aktualniPrvek).getPocetValcu()));
            case STANDARDNI_MOTORKA -> new StandardniMotorka(
                    novaZnacka,
                    novaSPZ,
                    novaCena24h,
                    dejOverenyPocetRychlosti(dialogReference, ((StandardniMotorka) aktualniPrvek).getPocetRychlosti()));
            case TERENNI_MOTORKA -> new TerenniMotorka(
                    novaZnacka,
                    novaSPZ,
                    novaCena24h,
                    dejOverenouSpotrebuPaliva(dialogReference, ((TerenniMotorka) aktualniPrvek).getSpotrebaPaliva()));
            default -> throw new ChybnaHodnotaException();
        };
    }

    /**
     * Příkaz {@code Nový}.
     */
    public static Motorka pozadatNovy(int podledniID, String typ) {
        final TvurceDialog dialog = new TvurceDialog(podledniID, typ);
        Optional<ButtonType> odpoved = dialog.showAndWait();

        if (odpoved.isPresent() && odpoved.get().getButtonData().isDefaultButton()) {
            try {
                return dejVytvorenouMotorku(dialog);
            } catch (ChybnaHodnotaException ignored) {}
        }
        return null;
    }

    private static Motorka dejVytvorenouMotorku(TvurceDialog dialogReference) throws ChybnaHodnotaException {
        final Znacka novaZnacka = dialogReference.getCbZnacka();
        final String novaSPZ = dejOverenouSPZ(dialogReference);
        final double cena24h = dejOverenouCenu24h(dialogReference);

        return switch (dialogReference.getVybranyTyp()) {
            case RETRO_MOTORKA -> new RetroMotorka(novaZnacka, novaSPZ, cena24h,
                    dejOverenouHmotnost(dialogReference));
            case SPORTOVNI_MOTORKA -> new SportovniMotorka(novaZnacka, novaSPZ, cena24h,
                    dejOverenyPocetValcu(dialogReference));
            case STANDARDNI_MOTORKA -> new StandardniMotorka(novaZnacka, novaSPZ, cena24h,
                    dejOverenyPocetRychlosti(dialogReference));
            case TERENNI_MOTORKA -> new TerenniMotorka(novaZnacka, novaSPZ, cena24h,
                    dejOverenouSpotrebuPaliva(dialogReference));
            default -> throw new ChybnaHodnotaException();
        };
    }

    private static String dejOverenouSPZ(EditorDialog d, String starySPZ) throws ChybnaHodnotaException {
        String novaSPZ = d.getNovouSPZ();
        if (jePrazdnyString(d.getNovouSPZ())) novaSPZ = starySPZ;
        else if (!jeValidniSPZ(novaSPZ)) nahlasChybnouHodnotu(Alarm.CHYBNA_SPZ);

        return novaSPZ;
    }

    private static String dejOverenouSPZ(TvurceDialog dialog) throws ChybnaHodnotaException {
        final String novaSPZ = dialog.getTfSPZ();
        if (jePrazdnyString(novaSPZ) || !jeValidniSPZ(novaSPZ)) nahlasChybnouHodnotu(Alarm.CHYBNA_SPZ);

        return novaSPZ;
    }

    private static double dejOverenouCenu24h(EditorDialog d, double staraCena24h) throws ChybnaHodnotaException {
        String novaCena24h = d.getNovouCenu24h();
        double cenaDouble = INICIALIZACNI_DOUBLE_HODNOTA;

        if (jePrazdnyString(d.getNovouCenu24h())) cenaDouble = staraCena24h;
        else if (!jeValidniDouble(novaCena24h)) nahlasChybnouHodnotu(Alarm.CHYBNA_CENA24h);
        else cenaDouble = Double.parseDouble(novaCena24h);

        return cenaDouble;
    }

    private static double dejOverenouCenu24h(TvurceDialog dialog) throws ChybnaHodnotaException {
        String novaCena24h = dialog.getTfCena24h();
        if (jePrazdnyString(novaCena24h) || !jeValidniDouble(novaCena24h)) nahlasChybnouHodnotu(Alarm.CHYBNA_CENA24h);

        return Double.parseDouble(novaCena24h);
    }

    private static double dejOverenouHmotnost(EditorDialog d, double staraHmotnost) throws ChybnaHodnotaException {
        String novaHmotnost = d.getNovouHmotnost();
        double hmotnostDouble = INICIALIZACNI_DOUBLE_HODNOTA;

        if (jePrazdnyString(d.getNovouHmotnost())) hmotnostDouble = staraHmotnost;
        else if (!jeValidniDouble(novaHmotnost)) nahlasChybnouHodnotu(Alarm.CHYBNA_HMOTNOST);
        else hmotnostDouble = Double.parseDouble(novaHmotnost);

        return hmotnostDouble;
    }

    private static double dejOverenouHmotnost(TvurceDialog dialog) throws ChybnaHodnotaException {
        String novaHmotnost = dialog.getTfHmotnost();
        if (jePrazdnyString(novaHmotnost) || !jeValidniDouble(novaHmotnost)) nahlasChybnouHodnotu(Alarm.CHYBNA_HMOTNOST);

        return Double.parseDouble(novaHmotnost);
    }

    private static int dejOverenyPocetValcu(EditorDialog d, int staryPocetValcu) throws ChybnaHodnotaException {
        String novyPocetValcu = d.getNovyPocetValcu();
        int pocetValcuInteger = INICIALIZACNI_INTEGER_HODNOTA;

        if (jePrazdnyString(d.getNovyPocetValcu())) pocetValcuInteger = staryPocetValcu;
        else if (!jeValidniInteger(novyPocetValcu)) nahlasChybnouHodnotu(Alarm.CHYBNY_INTEGER);
        else pocetValcuInteger = Integer.parseInt(novyPocetValcu);

        return pocetValcuInteger;
    }

    private static int dejOverenyPocetValcu(TvurceDialog dialog) throws ChybnaHodnotaException {
        String novyPocetValcu = dialog.getTfPocetValcu();
        if (jePrazdnyString(novyPocetValcu) || !jeValidniInteger(novyPocetValcu)) nahlasChybnouHodnotu(Alarm.CHYBNY_INTEGER);

        return Integer.parseInt(novyPocetValcu);
    }

    private static int dejOverenyPocetRychlosti(EditorDialog d, int staryPocetRychlosti) throws ChybnaHodnotaException {
        String novyPocetRychlosti = d.getNovyPocetRychlosti();
        int pocetRychlostiInteger = INICIALIZACNI_INTEGER_HODNOTA;

        if (jePrazdnyString(d.getNovyPocetRychlosti())) pocetRychlostiInteger = staryPocetRychlosti;
        else if (!jeValidniInteger(novyPocetRychlosti)) nahlasChybnouHodnotu(Alarm.CHYBNY_INTEGER);
        else pocetRychlostiInteger = Integer.parseInt(novyPocetRychlosti);

        return pocetRychlostiInteger;
    }

    private static int dejOverenyPocetRychlosti(TvurceDialog dialog) throws ChybnaHodnotaException {
        String novyPocetRychlosti = dialog.getTfPocetRychlosti();
        if (jePrazdnyString(novyPocetRychlosti) || !jeValidniInteger(novyPocetRychlosti)) nahlasChybnouHodnotu(Alarm.CHYBNY_INTEGER);

        return Integer.parseInt(novyPocetRychlosti);
    }

    private static double dejOverenouSpotrebuPaliva(EditorDialog d, double staraSpotrebaPaliva) throws ChybnaHodnotaException {
        String novaSpotrebaPaliva = d.getNovouSpotrebuPaliva();
        double spotrebaPalivaDouble = INICIALIZACNI_DOUBLE_HODNOTA;

        if (jePrazdnyString(d.getNovouSpotrebuPaliva())) spotrebaPalivaDouble = staraSpotrebaPaliva;
        else if (!jeValidniDouble(novaSpotrebaPaliva)) nahlasChybnouHodnotu(Alarm.CHYBNA_SPOTREBA_PALIVA);
        else spotrebaPalivaDouble = Double.parseDouble(novaSpotrebaPaliva);

        return spotrebaPalivaDouble;
    }

    private static double dejOverenouSpotrebuPaliva(TvurceDialog dialog) throws ChybnaHodnotaException {
        String novaSpotrebaPaliva = dialog.getTfSpotrebaPaliva();
        if (jePrazdnyString(novaSpotrebaPaliva) || !jeValidniDouble(novaSpotrebaPaliva)) nahlasChybnouHodnotu(Alarm.CHYBNA_SPOTREBA_PALIVA);

        return Double.parseDouble(novaSpotrebaPaliva);
    }

    /**
     * Příkaz {@code Uloz}.
     */
    public static Optional<String> pozadatUloz() {
        final TextInputDialog dialog = new TextInputDialog(DialogovySpravce.JMENO_TXT_SOUBORU.text());
        dialog.setTitle(DialogovySpravce.INPUT_DIALOG_TITULEK.text());
        dialog.setHeaderText(DialogovySpravce.INPUT_DIALOG_HLAVICKA.text());
        dialog.setContentText(DialogovySpravce.INPUT_DIALOG_LABEL.text());

        return dialog.showAndWait();
    }

    /**
     * Příkaz {@code Najdi}.
     */
    public static Optional<String> pozadatNajdi(String defaultniHodnota) {
        final TextInputDialog dialog = new TextInputDialog(defaultniHodnota);
        dialog.setTitle(DialogovySpravce.NAJDI_ID_TITULEK.text());
        dialog.setHeaderText(DialogovySpravce.NAJDI_ID_HLAVICKA.text());
        dialog.setContentText(DialogovySpravce.NAJDI_ID_CONTENT.text());

        return dialog.showAndWait();
    }

    /**
     * Metoda nahlasí chybu.
     */
    private static void nahlasChybnouHodnotu(Alarm typZpravy) throws ChybnaHodnotaException {
        Alarm.alertErr.accept(typZpravy.zprava());
        throw new ChybnaHodnotaException();
    }

    /**
     * Následující metody reprezentují validátory pro vstupní hodnoty.
     */
    public static boolean jeValidniSPZ(String vstup) {
        Pattern pt = Pattern.compile("\\d[A-Z]\\d\\s\\d\\d\\d\\d");
        Matcher mt = pt.matcher(vstup);
        return mt.matches();
    }

    public static boolean jeValidniInteger(String vstup) {
        try {
            Integer.parseInt(vstup);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean jeValidniDouble(String vstup) {
        Pattern pt = Pattern.compile("[0-9]+\\.0");
        Matcher mc = pt.matcher(vstup);
        return mc.matches();
    }

    public static boolean jePrazdnyString(String s) { return s == null || s.isEmpty(); }
}
