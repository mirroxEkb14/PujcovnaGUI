package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.command.printers.ModeratorVyjimek;
import cz.upce.fei.boop.pujcovna.command.printers.ModeratorZprav;
import cz.upce.fei.boop.pujcovna.data.model.Motorka;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import cz.upce.fei.boop.pujcovna.util.vyjimky.*;

import java.util.Scanner;

/**
 * Třída obsahuje metody, které se ptají uživatele na vstup.
 * <p>
 * Všechny metody vždycky vrácejí validní data, jelikož každá z nich obsahuje
 * {@code do-while} cyklus, který se ukončí jenom tehdy, když uživatel zadá
 * buď příkaz {@code exit}, anebo validní data.
 *
 * @author zea1ot 4/14/2023
 */
public final class PrikazonyTazatel {

    private static final Scanner in = new Scanner(System.in);
    private static String vstup;

    public static String pozadatPrikaz() {
        boolean jeValidni = false;
        do {
            try {
                ModeratorZprav.poprositPrikaz();
                vstup = in.nextLine();
                jeValidni = PrikazovyManipulant.jeValidniPrikaz(vstup);
                pozadatNeExit();
            } catch (NeznamyPrikazException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyPrikaz);
            }
        } while (!jeValidni);
        return vstup;
    }

    public static TypMotorky pozadatNovyTyp() {
        boolean jeValidniTyp = false;
        TypMotorky novyTyp = null;
        do {
            try {
                ModeratorZprav.poprositNovyTyp();
                vstup = in.nextLine();
                pozadatNeExit();
                final int cisloTypu = PrikazovyManipulant.dejValidniInteger(vstup);
                novyTyp = PrikazovyManipulant.dejValidniTyp(cisloTypu);
                jeValidniTyp = true;
            } catch (ChybnyIntegerException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyInteger);
            } catch (NeznamyTypMotorkyException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyTyp);
            }
        } while (!jeValidniTyp);
        return novyTyp;
    }

    public static Znacka pozadatNovouZnacku() {
        boolean jeValidniZnacka = false;
        Znacka novaZnacka = null;
        do {
            try {
                ModeratorZprav.poprositNovouZnacku();
                vstup = in.nextLine();
                pozadatNeExit();
                final int cisloZnacky = PrikazovyManipulant.dejValidniInteger(vstup);
                novaZnacka = PrikazovyManipulant.dejValidniZnacku(cisloZnacky);
                jeValidniZnacka = true;
            } catch (ChybnyIntegerException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyInteger);
            } catch (NeznamaZnackaException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnouZnacku);
            }
        } while (!jeValidniZnacka);
        return novaZnacka;
    }

    public static String pozadatNovouSPZ() {
        boolean jeValidniSPZ = false;
        do {
            try {
                ModeratorZprav.poprositNovouSPZ();
                vstup = in.nextLine();
                pozadatNeExit();
                jeValidniSPZ = PrikazovyManipulant.dejValidniSPZ(vstup);
            } catch (ChybnyFormatSPZException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnouSPZ);
            }
        } while (!jeValidniSPZ);
        return vstup;
    }

    public static double pozadatNovouCenu24h() {
        boolean jeValidniSPZ = false;
        double novaCena24h = .0;
        do {
            try {
                ModeratorZprav.poprositNovouCenu24h();
                vstup = in.nextLine();
                pozadatNeExit();
                novaCena24h = PrikazovyManipulant.dejValidniDouble(vstup);
                jeValidniSPZ = true;
            } catch (ChybnyDoubleException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyDouble);
            }
        } while (!jeValidniSPZ);
        return novaCena24h;
    }

    public static double pozadatRetroAtribut() {
        boolean jeValidniHmotnost = false;
        double result = .0;
        do {
            try {
                ModeratorZprav.poprositNovouHmotnost();
                vstup = in.nextLine();
                pozadatNeExit();
                result = PrikazovyManipulant.dejValidniDouble(vstup);
                jeValidniHmotnost = true;
            } catch (ChybnyDoubleException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyDouble);
            }
        } while (!jeValidniHmotnost);
        return result;
    }

    public static int pozadatSportovniAtribut() {
        boolean jeValidniPocetValcu = false;
        int result = -1;
        do {
            try {
                ModeratorZprav.poprositNovyPocetValcu();
                vstup = in.nextLine();
                pozadatNeExit();
                result = PrikazovyManipulant.dejValidniInteger(vstup);
                jeValidniPocetValcu = true;
            } catch (ChybnyIntegerException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyInteger);
            }
        } while (!jeValidniPocetValcu);
        return result;
    }

    public static int pozadatStandardniAtribut() {
        boolean jeValidniPocetRychlosti = false;
        int result = -1;
        do {
            try {
                ModeratorZprav.poprositNovyPocetRychlosti();
                vstup = in.nextLine();
                pozadatNeExit();
                result = PrikazovyManipulant.dejValidniInteger(vstup);
                jeValidniPocetRychlosti = true;
            } catch (ChybnyIntegerException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyInteger);
            }
        } while (!jeValidniPocetRychlosti);
        return result;
    }

    public static double pozadatTerenniAtribut() {
        boolean jeValidniSpotrebaPaliva = false;
        double result = .0;
        do {
            try {
                ModeratorZprav.poprositNovouSpotrebuPaliva();
                vstup = in.nextLine();
                pozadatNeExit();
                result = PrikazovyManipulant.dejValidniDouble(vstup);
                jeValidniSpotrebaPaliva = true;
            } catch (ChybnyDoubleException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyDouble);
            }
        } while (!jeValidniSpotrebaPaliva);
        return result;
    }

    public static int pozadatHodnotuProHledani() {
        boolean jeValidniId = false;
        int result = -1;
        do {
            try {
                ModeratorZprav.poprositHodnotuProHledani();
                vstup = in.nextLine();
                pozadatNeExit();
                result = PrikazovyManipulant.dejValidniInteger(vstup);
                jeValidniId = true;
            } catch (ChybnyIntegerException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyInteger);
            }
        } while (!jeValidniId);
        return result;
    }

    public static int pozadatHodnotuProOdebrani() {
        boolean jeValidniId = false;
        int result = -1;
        do {
            try {
                ModeratorZprav.poprositHodnotuProOdebrani();
                vstup = in.nextLine();
                pozadatNeExit();
                result = PrikazovyManipulant.dejValidniInteger(vstup);
                jeValidniId = true;
            } catch (ChybnyIntegerException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyInteger);
            }
        } while (!jeValidniId);
        return result;
    }

    public static String pozadatRetroAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.poprositRetroAtributProEditaci(aktualniPrvek);
        vstup = in.nextLine();
        pozadatNeExit();
        return vstup;
    }

    public static String pozadatSportovniAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.poprositSportovniAtributProEditaci(aktualniPrvek);
        vstup = in.nextLine();
        pozadatNeExit();
        return vstup;
    }

    public static String pozadatStandardniAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.poprositStandardniAtributProEditaci(aktualniPrvek);
        vstup = in.nextLine();
        pozadatNeExit();
        return vstup;
    }

    public static String pozadatTerenniAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.poprositTerenniAtributProEditaci(aktualniPrvek);
        vstup = in.nextLine();
        pozadatNeExit();
        return vstup;
    }

    public static String pozadatJmenoTextovehoSouboru() {
        ModeratorZprav.poprositJmenoTextovehoSouboru();
        vstup = in.nextLine();
        pozadatNeExit();
        return vstup;
    }

    public static int pozadatPocetMotorekProGeneraci() {
        boolean jeValidniPocetMotorek = false;
        int result = -1;
        do {
            try {
                ModeratorZprav.poprositPocetProGeneraci();
                vstup = in.nextLine();
                pozadatNeExit();
                result = PrikazovyManipulant.dejValidniInteger(vstup);
                jeValidniPocetMotorek = true;
            } catch (ChybnyIntegerException ex) {
                PrikazovyRadekMain.nahlasVyjimku(ModeratorVyjimek::nahlasitChybnyInteger);
            }
        } while (!jeValidniPocetMotorek);
        return result;
    }

    public static void pozadatNeExit() {
        if (PrikazovyManipulant.isExit(vstup)) {
            ModeratorZprav.nashle();
            System.exit(0);
        }
    }
}
