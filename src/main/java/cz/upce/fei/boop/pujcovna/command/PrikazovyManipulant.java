package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.command.printers.ModeratorVyjimek;
import cz.upce.fei.boop.pujcovna.data.model.*;
import cz.upce.fei.boop.pujcovna.data.vycty.Atribut;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import cz.upce.fei.boop.pujcovna.spravce.Prikaz;
import cz.upce.fei.boop.pujcovna.util.vyjimky.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Třída obsahuje pomocní metody pro {@code PrikazovyRadekMain}.
 * <p>
 * Třída nemá referenci na seznam {@code Motorek}.
 */
public final class PrikazovyManipulant {

    /**
     * Metoda určuje, jaký příkaz zadal uživatel.
     * <p>
     * Tato metoda se volá po metodě {@code pozadatPrikaz()}, a proto vstupní
     * data jsou vždycky platná.
     *
     * @return Návratová hodnota nikdy nebude {@code null}.
     */
    public static Prikaz dejTypPrikazu(String platnyPrikaz) {
        for (Prikaz vzorec : Prikaz.values()) {
            String[] varianty = vzorec.toString().split(Prikaz.getOddelovac());
            Optional<String> result = Arrays.stream(varianty)
                    .filter(platnyPrikaz::equalsIgnoreCase)
                    .findFirst();
            if (result.isPresent())
                return vzorec;
        }
        return null; // nedosažitelný kód
    }


    /**
     * @param prikaz Vstupní uživatelský příkaz.
     *
     * @return Vrací {@code true}, když hodnota vstupního parametru byla
     * nalezena ve výčtovém typy {@code Prikaz}.
     *
     * @throws NeznamyPrikazException Když vstupní příkaz nebyl nalezen v
     * enumu {@code Prikaz}.
     */
    public static boolean jeValidniPrikaz(String prikaz) throws NeznamyPrikazException {
        final Prikaz[] prikazy = Prikaz.values();
        Arrays.stream(prikazy)
                .map(Prikaz::toString)
                .map(p -> p.split(Prikaz.getOddelovac()))
                .flatMap(Arrays::stream)
                .filter(prikaz::equalsIgnoreCase)
                .findFirst()
                .orElseThrow(NeznamyPrikazException::new);
        return true;
    }

    /**
     * Následující metody kontrolují vstupní parametry, zda jsou validní.
     * <p>
     * Pokud ano, vrácejí odpovídající hodnotu. Pokud ne, vystaví se výjimka.
     */
    public static TypMotorky dejValidniTyp(int typ) throws NeznamyTypMotorkyException {
        Integer cisloTypu = Stream.of(TypMotorky.RETRO_CISLO, TypMotorky.SPORTOVNI_CISLO, TypMotorky.STANDARDNI_CISLO, TypMotorky.TERENNI_CISLO)
                .filter(c -> c == typ)
                .findFirst()
                .orElseThrow(NeznamyTypMotorkyException::new);
        return TypMotorky.values()[--cisloTypu];
    }

    public static int dejValidniInteger(String vstup) throws ChybnyIntegerException {
        try {
            return Integer.parseInt(vstup);
        } catch (NumberFormatException ex) {
            throw new ChybnyIntegerException();
        }
    }

    public static Znacka dejValidniZnacku(int znacka) throws NeznamaZnackaException {
        Integer cisloZnacky = Stream.of(Znacka.BMW_CISLO, Znacka.HONDA_CISLO)
                .filter(c -> c == znacka)
                .findFirst()
                .orElseThrow(NeznamaZnackaException::new);
        return Znacka.values()[--cisloZnacky];
    }

    public static boolean dejValidniSPZ(String inputSPZ) throws ChybnyFormatSPZException {
        Pattern pt = Pattern.compile("\\d[A-Z]\\d\\s\\d\\d\\d\\d");
        Matcher mt = pt.matcher(inputSPZ);
        if (!mt.matches())
            throw new ChybnyFormatSPZException();
        return true;
    }

    public static double dejValidniDouble(String vstup) throws ChybnyDoubleException {
        Pattern pt = Pattern.compile("[0-9]+\\.0");
        Matcher mc = pt.matcher(vstup);
        if (mc.matches())
            return Double.parseDouble(vstup);
        throw new ChybnyDoubleException();
    }

    /**
     * Metoda je pomocní pro {@code dejMotorkyPodleTypu()}.
     */
    public static Optional<TypMotorky> dejAtributPodleNazvu(String vstupniNazev) {
        for (TypMotorky typ : TypMotorky.values()) {
            if (typ.nazev().equalsIgnoreCase(vstupniNazev))
                return Optional.of(typ);
        }
        return Optional.empty();
    }

    /**
     * Metoda je pomocní pro {@code dejMotorkyPodleZnacky()}.
     */
    public static Optional<Znacka> dejZnackuPodleNazvu(String vstupniNazev) {
        for (Znacka znacka : Znacka.values()) {
            if (znacka.nazev().equalsIgnoreCase(vstupniNazev))
                return Optional.of(znacka);
        }
        return Optional.empty();
    }

    /**
     * Následující metody jsou pomocní pro {@code dejEditovanouMotorku()}.
     * <p>
     * Metoda {@code dejVstupniIdentifikatorAtributu()} vrací volbu uživatele, jaký atribut chce upravit.
     */
    public static Motorka dejEditovanouMotorkuPodleCislaAtributu(Motorka m, int vstupniAtribut) {
        final Atribut atributNaEditaci = dejAtributPodleIdentifikatoru(vstupniAtribut);
        Motorka.setCitac(m.getId() - Motorka.getSnizovaciHodnotaCitace());
        Motorka result = null;
        switch (atributNaEditaci) {
            case ZNACKA -> {
                final Znacka novaZnacka = PrikazonyTazatel.pozadatNovouZnacku();
                switch (m.getTyp()) {
                    case RETRO_MOTORKA -> {
                        final double hmotnost = ((RetroMotorka) m).getHmotnost();
                        result = new RetroMotorka(novaZnacka, m.getSpz(), m.getCena24h(), hmotnost);
                    }
                    case SPORTOVNI_MOTORKA -> {
                        final int pocetValcu = ((SportovniMotorka) m).getPocetValcu();
                        result = new SportovniMotorka(novaZnacka, m.getSpz(), m.getCena24h(), pocetValcu);
                    }
                    case STANDARDNI_MOTORKA -> {
                        final int pocetRychlosti = ((StandardniMotorka) m).getPocetRychlosti();
                        result = new StandardniMotorka(novaZnacka, m.getSpz(), m.getCena24h(), pocetRychlosti);
                    }
                    case TERENNI_MOTORKA -> {
                        final double spotrebaPaliva = ((TerenniMotorka) m).getSpotrebaPaliva();
                        result = new TerenniMotorka(novaZnacka, m.getSpz(), m.getCena24h(), spotrebaPaliva);
                    }
                }
            }
            case SPZ -> {
                final String noveSPZ = PrikazonyTazatel.pozadatNovouSPZ();
                switch (m.getTyp()) {
                    case RETRO_MOTORKA -> {
                        final double hmotnost = ((RetroMotorka) m).getHmotnost();
                        result = new RetroMotorka(m.getZnacka(), noveSPZ, m.getCena24h(), hmotnost);
                    }
                    case SPORTOVNI_MOTORKA -> {
                        final int pocetValcu = ((SportovniMotorka) m).getPocetValcu();
                        result = new SportovniMotorka(m.getZnacka(), noveSPZ, m.getCena24h(), pocetValcu);
                    }
                    case STANDARDNI_MOTORKA -> {
                        final int pocetRychlosti = ((StandardniMotorka) m).getPocetRychlosti();
                        result = new StandardniMotorka(m.getZnacka(), noveSPZ, m.getCena24h(), pocetRychlosti);
                    }
                    case TERENNI_MOTORKA -> {
                        final double spotrebaPaliva = ((TerenniMotorka) m).getSpotrebaPaliva();
                        result = new TerenniMotorka(m.getZnacka(), noveSPZ, m.getCena24h(), spotrebaPaliva);
                    }
                }
            }
            case CENA24H -> {
                final double novaCena24h = PrikazonyTazatel.pozadatNovouCenu24h();
                switch (m.getTyp()) {
                    case RETRO_MOTORKA -> {
                        final double hmotnost = ((RetroMotorka) m).getHmotnost();
                        result = new RetroMotorka(m.getZnacka(), m.getSpz(), novaCena24h, hmotnost);
                    }
                    case SPORTOVNI_MOTORKA -> {
                        final int pocetValcu = ((SportovniMotorka) m).getPocetValcu();
                        result = new SportovniMotorka(m.getZnacka(), m.getSpz(), novaCena24h, pocetValcu);
                    }
                    case STANDARDNI_MOTORKA -> {
                        final int pocetRychlosti = ((StandardniMotorka) m).getPocetRychlosti();
                        result = new StandardniMotorka(m.getZnacka(), m.getSpz(), novaCena24h, pocetRychlosti);
                    }
                    case TERENNI_MOTORKA -> {
                        final double spotrebaPaliva = ((TerenniMotorka) m).getSpotrebaPaliva();
                        result = new TerenniMotorka(m.getZnacka(), m.getSpz(), novaCena24h, spotrebaPaliva);
                    }
                }
            }
            case HMOTNOST -> {
                final double hmotnost = PrikazonyTazatel.pozadatRetroAtribut();
                result = new RetroMotorka(m.getZnacka(), m.getSpz(), m.getCena24h(), hmotnost);
            }
            case POCET_VALCU -> {
                final int pocetValcu = PrikazonyTazatel.pozadatSportovniAtribut();
                result = new SportovniMotorka(m.getZnacka(), m.getSpz(), m.getCena24h(), pocetValcu);
            }
            case POCET_RYCHLOSTI -> {
                final int pocetRychlosti = PrikazonyTazatel.pozadatStandardniAtribut();
                result = new StandardniMotorka(m.getZnacka(), m.getSpz(), m.getCena24h(), pocetRychlosti);
            }
            case SPOTREBA_PALIVA -> {
                final double spotrebaPaliva = PrikazonyTazatel.pozadatTerenniAtribut();
                result = new TerenniMotorka(m.getZnacka(), m.getSpz(), m.getCena24h(), spotrebaPaliva);
            }
        }
        return result;
    }

    private static Atribut dejAtributPodleIdentifikatoru(int identifikatorAtributu) {
        Atribut result = null;
        for (Atribut atribut : Atribut.values()) {
            if (identifikatorAtributu == atribut.identifikator()) {
                result = atribut;
                break;
            }
        }
        return result;
    }

    public static int dejVstupniIdentifikatorAtributu(Motorka aktualniMotorka) {
        int vstupniAtribut = -1;
        boolean jeValidniAtribut = false;
        switch (aktualniMotorka.getTyp()) {
            case RETRO_MOTORKA -> {
                do {
                    try {
                        final String vstup = PrikazonyTazatel.pozadatRetroAtributProEditaci(aktualniMotorka);
                        vstupniAtribut = dejValidniInteger(vstup);
                        jeValidniAtribut = zkontrolujRetroAtribut(vstupniAtribut);
                    } catch (ChybnyIntegerException ex) {
                        ModeratorVyjimek.nahlasitChybnyInteger();
                    } catch (ChybneCisloAtributuException ex) {
                        ModeratorVyjimek.nahlasitVujimkuCisloAtributu();
                    }
                } while (!jeValidniAtribut);
            }
            case SPORTOVNI_MOTORKA -> {
                do {
                    try {
                        final String vstup = PrikazonyTazatel.pozadatSportovniAtributProEditaci(aktualniMotorka);
                        vstupniAtribut = dejValidniInteger(vstup);
                        jeValidniAtribut = zkontrolujSportovniAtribut(vstupniAtribut);
                    } catch (ChybnyIntegerException ex) {
                        ModeratorVyjimek.nahlasitChybnyInteger();
                    }
                } while (!jeValidniAtribut);
            }
            case STANDARDNI_MOTORKA -> {
                do {
                    try {
                        final String vstup = PrikazonyTazatel.pozadatStandardniAtributProEditaci(aktualniMotorka);
                        vstupniAtribut = dejValidniInteger(vstup);
                        jeValidniAtribut = zkontrolujStandardniAtribut(vstupniAtribut);
                    } catch (ChybnyIntegerException ex) {
                        ModeratorVyjimek.nahlasitChybnyInteger();
                    }
                } while (!jeValidniAtribut);
            }
            case TERENNI_MOTORKA -> {
                do {
                    try {
                        final String vstup = PrikazonyTazatel.pozadatTerenniAtributProEditaci(aktualniMotorka);
                        vstupniAtribut = dejValidniInteger(vstup);
                        jeValidniAtribut = zkontrolujTerenniAtribut(vstupniAtribut);
                    } catch (ChybnyIntegerException ex) {
                        ModeratorVyjimek.nahlasitChybnyInteger();
                    }
                } while (!jeValidniAtribut);
            }
        }
        return vstupniAtribut;
    }

    public static boolean zkontrolujRetroAtribut(int vstupniAtribut) throws ChybneCisloAtributuException {
        final boolean result = Stream.of(Atribut.ZNACKA, Atribut.SPZ, Atribut.CENA24H, Atribut.HMOTNOST)
                .map(Atribut::identifikator)
                .anyMatch(attr -> vstupniAtribut == attr);
        if (!result)
            throw new ChybneCisloAtributuException();
        return true;
    }

    public static boolean zkontrolujSportovniAtribut(int vstupniAtribut) {
        return Stream.of(Atribut.ZNACKA, Atribut.SPZ, Atribut.CENA24H, Atribut.POCET_VALCU)
                .map(Atribut::identifikator)
                .anyMatch(attr -> vstupniAtribut == attr);
    }

    public static boolean zkontrolujStandardniAtribut(int vstupniAtribut) {
        return Stream.of(Atribut.ZNACKA, Atribut.SPZ, Atribut.CENA24H, Atribut.POCET_RYCHLOSTI)
                .map(Atribut::identifikator)
                .anyMatch(attr -> vstupniAtribut == attr);
    }

    public static boolean zkontrolujTerenniAtribut(int vstupniAtribut) {
        return Stream.of(Atribut.ZNACKA, Atribut.SPZ, Atribut.CENA24H, Atribut.SPOTREBA_PALIVA)
                .map(Atribut::identifikator)
                .anyMatch(attr -> vstupniAtribut == attr);
    }

    /**
     * Metoda kontroluje, zda uživatel chce ukončit program.
     */
    public static boolean isExit(String prikaz) {
        return Prikaz.EXIT.toString().equalsIgnoreCase(prikaz);
    }
}
