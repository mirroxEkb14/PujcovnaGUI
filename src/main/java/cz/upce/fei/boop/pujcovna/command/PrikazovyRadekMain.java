package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.command.printers.ModeratorVyjimek;
import cz.upce.fei.boop.pujcovna.command.printers.ModeratorZprav;
import cz.upce.fei.boop.pujcovna.data.model.*;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import cz.upce.fei.boop.pujcovna.spravce.Prikaz;
import cz.upce.fei.boop.pujcovna.spravce.SpravaMotorek;
import cz.upce.fei.boop.pujcovna.util.vyjimky.ChybnaHodnotaException;
import cz.upce.fei.boop.pujcovna.util.vyjimky.KolekceException;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author zea1ot 4/8/2023
 */
public final class PrikazovyRadekMain {

    private static final SpravaMotorek seznamMotorek = SpravaMotorek.getInstance();

    public static void main(String[] args) {
        ModeratorZprav.help();

        while (true) {
            final String prikaz = PrikazonyTazatel.pozadatPrikaz();
            zpracovatPrikaz(prikaz);
        }
    }

    private static void zpracovatPrikaz(final String prikaz) {
        Prikaz typPrikazu = PrikazovyManipulant.dejTypPrikazu(prikaz);
        Objects.requireNonNull(typPrikazu);

        switch (typPrikazu) {
            case HELP -> ModeratorZprav.help();
            case NOVY -> {
                TypMotorky novyTyp = PrikazonyTazatel.pozadatNovyTyp();
                Znacka novaZnacka = PrikazonyTazatel.pozadatNovouZnacku();
                String noveSPZ = PrikazonyTazatel.pozadatNovouSPZ();
                final double novaCena24h = PrikazonyTazatel.pozadatNovouCenu24h();
                ukoncitPrikazNovy(novyTyp, novaZnacka, noveSPZ, novaCena24h);
            }
            case NAJDI -> {
                final int vstupniId = PrikazonyTazatel.pozadatHodnotuProHledani();
                ukoncitPrikazNajdi(vstupniId);
            }
            case ODEBER -> {
                try {
                    final int id = PrikazonyTazatel.pozadatHodnotuProOdebrani();
                    final Optional<Motorka> aktualniPredOdebranim = dejAktualniMotorkuPredOdepranim();
                    final Motorka odebranaMotorka = seznamMotorek.odeber(id);
                    if (bylaOdebranaAktualni(aktualniPredOdebranim)) {
                        ModeratorZprav.nahlasitVysledekOdeberJeAktualni(odebranaMotorka);
                        return;
                    }
                    ModeratorZprav.nahlasitVysledekOdeberNeniAktualni(odebranaMotorka);
                } catch (ChybnaHodnotaException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuOdeber);
                }
            }
            case DEJ -> {
                try {
                    final Motorka aktualniMotorka = seznamMotorek.dej();
                    ModeratorZprav.nahlasitVysledekDej(aktualniMotorka);
                } catch (KolekceException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuDej);
                }
            }
            case EDITUJ -> {
                try {
                    final Function<Motorka, Motorka> dejUpravenouMotorku = PrikazovyRadekMain::dejEditovanouMotorku;
                    final Motorka editovanaMotorka = seznamMotorek.edituj(dejUpravenouMotorku);
                    ModeratorZprav.nahlasitVysledekEdituj(editovanaMotorka);
                } catch (KolekceException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuEdituj);
                }
            }
            case VYJMI -> {
                try {
                    final Motorka odebranaMotorka = seznamMotorek.vyjmi();
                    ModeratorZprav.nahlasitVysledekVyjmi(odebranaMotorka);
                } catch (KolekceException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuVyjmi);
                }
            }
            case PRVNI -> {
                try {
                    seznamMotorek.prvni();
                    ModeratorZprav.nahlasitVysledekPrvni();
                } catch (KolekceException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuPrvni);
                }
            }
            case DALSI -> {
                try {
                    seznamMotorek.dalsi();
                    ModeratorZprav.nahlasitVysledekDalsi();
                } catch (KolekceException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuDalsi);
                }
            }
            case POSLEDNI -> {
                try {
                    seznamMotorek.posledni();
                    ModeratorZprav.nahlasitVysledekPosledni();
                } catch (KolekceException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuPosledni);
                }
            }
            case POCET -> ModeratorZprav.nahlasitVysledekPocet(seznamMotorek.pocet());
            case OBNOV -> {
                try {
                    if (seznamMotorek.obnov()) {
                        ModeratorZprav.nahlasitVysledekObnov();
                        break;
                    }
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuObnov);
                } catch (IOException ignored) {}
            }
            case ZALOHUJ -> {
                try {
                    if (seznamMotorek.zalohuj()) {
                        ModeratorZprav.nahlasitVysledekZalohuj();
                        break;
                    }
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuZalohuj);
                } catch (IOException ignored) {}
            }
            case VYPIS -> {
                if (seznamMotorek.pocet() == 0) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuVypis);
                    break;
                }
                seznamMotorek.vypis();
            }
            case NACTI_TEXT -> {
                try {
                    if (!seznamMotorek.nacti())
                        ModeratorZprav.nahlasitVysledekNactiPrazdny();
                } catch (IOException ex) {
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuNacti);
                }
            }
            case ULOZ_TEXT -> {
                try {
                    final String jmenoSouboru = PrikazonyTazatel.pozadatJmenoTextovehoSouboru();
                    final boolean jeUlozeno = seznamMotorek.uloz(jmenoSouboru);
                    if (jeUlozeno) {
                        ModeratorZprav.nahlasitVysledekUloz();
                        break;
                    }
                    seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuUloz);
                } catch (IOException ignored) {}
            }
            case GENERUJ -> {
                final int pocetMotorek = PrikazonyTazatel.pozadatPocetMotorekProGeneraci();
                seznamMotorek.generuj(pocetMotorek);
                ModeratorZprav.nahlasitVysledekGeneruj();
            }
            case ZRUS -> {
                seznamMotorek.zrus();
                ModeratorZprav.nahlasitVysledekZrus();
            }
            case EXIT -> PrikazonyTazatel.pozadatNeExit();
        }
    }

    /**
     * Metoda ukončí příkaz {@code novy}: vytvoří novou {@code Motorku} podle zadaných
     * uživatelem atributů a nahlasí ukončení příkazu.
     */
    private static void ukoncitPrikazNovy(TypMotorky novyTyp, Znacka novaZnacka, String novaSPZ, double novaCena24h) {
        switch (novyTyp) {
            case RETRO_MOTORKA -> {
                final double novaHmotnost = PrikazonyTazatel.pozadatRetroAtribut();
                final RetroMotorka novaRetro = new RetroMotorka(novaZnacka, novaSPZ, novaCena24h, novaHmotnost);
                final boolean result = seznamMotorek.novy(novaRetro);
                nahlasitVysledekPrikazuNovy(novaRetro, result);
            }
            case SPORTOVNI_MOTORKA -> {
                final int novyPocetValcu = PrikazonyTazatel.pozadatSportovniAtribut();
                final SportovniMotorka novaSportovni = new SportovniMotorka(novaZnacka, novaSPZ, novaCena24h, novyPocetValcu);
                final boolean result = seznamMotorek.novy(novaSportovni);
                nahlasitVysledekPrikazuNovy(novaSportovni, result);
            }
            case STANDARDNI_MOTORKA -> {
                final int novyPocetRychlosti = PrikazonyTazatel.pozadatStandardniAtribut();
                final StandardniMotorka novaStandardni = new StandardniMotorka(novaZnacka, novaSPZ, novaCena24h, novyPocetRychlosti);
                final boolean result = seznamMotorek.novy(novaStandardni);
                nahlasitVysledekPrikazuNovy(novaStandardni, result);
            }
            case TERENNI_MOTORKA -> {
                final double novaSpotrebaPaliva = PrikazonyTazatel.pozadatTerenniAtribut();
                final TerenniMotorka novaStandardni = new TerenniMotorka(novaZnacka, novaSPZ, novaCena24h, novaSpotrebaPaliva);
                final boolean result = seznamMotorek.novy(novaStandardni);
                nahlasitVysledekPrikazuNovy(novaStandardni, result);
            }
        }
    }

    /**
     * Pomocní motoda pro {@code ukoncitPrikazNovy()}: podle {@code boolean} hodnotě
     * nahlasí, zda {@code Motorka} byla uložena za aktuální prvek v seznamu, anebo
     * jako poslední.
     */
    private static void nahlasitVysledekPrikazuNovy(Motorka novaMotorka, boolean result) {
        if (result) {
            ModeratorZprav.nahlasitVysledekPrikazuNovy(novaMotorka);
            return;
        }
        seznamMotorek.nahlasErrorLog(() -> ModeratorVyjimek.nahlasitVyjimkuPrikazuNovy(novaMotorka));
    }

    /**
     * Metoda ukončí příkaz {@code najdi}: vytváří novou {@code Motorku} a nový
     * {@code Comparator} podle vstupního id, pak porovnává toto id.
     *
     * @param h Vstupní hodnota uživatele (id {@code Motorky}).
     */
    private static void ukoncitPrikazNajdi(int h) {
        Optional<Motorka> nepovinny = dejMotorkuPodleID(h);
        Consumer<Motorka> hlaseniVysledku = ModeratorZprav::nahlasitVysledekNadji;
        nepovinny.ifPresentOrElse(hlaseniVysledku, () -> seznamMotorek.nahlasErrorLog(ModeratorVyjimek::nahlasitVyjimkuNadji));
    }

    /**
     * Pomocní metoda pro {@code ukoncitPrikazNajdi()}.
     */
    private static Optional<Motorka> dejMotorkuPodleID(int h) {
        final Comparator<Motorka> idComp = Comparator.comparingInt(Motorka::getId);
        Klic.nastavZmensenyCitac(h);
        final Klic klic = new Klic(null, null, null, .0);
        Stream<Motorka> datavod = seznamMotorek.najdi(idComp, klic);
        Klic.nastavZpetCitat(seznamMotorek.pocet());
        return datavod.findAny();
    }

    /**
     * Následující metody jsou pomocní pro příkaz {@code odeber}.
     */
    private static Optional<Motorka> dejAktualniMotorkuPredOdepranim() {
        Optional<Motorka> aktualniPredOdebranim = Optional.empty();
        try {
            aktualniPredOdebranim = Optional.of(seznamMotorek.dejAktualniMotorku());
        } catch (KolekceException ignored) {}
        return aktualniPredOdebranim;
    }

    private static boolean bylaOdebranaAktualni(Optional<Motorka> aktualniPredOdebranim) {
        if (aktualniPredOdebranim.isEmpty())
            return false;

        try {
            if (seznamMotorek.dejAktualniMotorku().equals(aktualniPredOdebranim.get()))
                return false;
        } catch (KolekceException ignored) {}
        return true;
    }

    /**
     * Metoda plní příkaz {@code edituj}.
     *
     * @param aktualniMotorka Aktuálně nastavená {@code Motorka}.
     *
     * @return Editována {@code Motorka}.
     */
    private static Motorka dejEditovanouMotorku(Motorka aktualniMotorka) {
        final int cisloAtributu = PrikazovyManipulant.dejVstupniIdentifikatorAtributu(aktualniMotorka);
        return PrikazovyManipulant.dejEditovanouMotorkuPodleCislaAtributu(aktualniMotorka, cisloAtributu);
    }

    /**
     * Tato metoda zavolá metodu seznamu pro výpis výjimek.
     * <p>
     * Volá se převážně z {@code PrikazovyTazatel}.
     */
    public static void nahlasVyjimku(Runnable vyjimka) {
        seznamMotorek.nahlasErrorLog(vyjimka);
    }
}
