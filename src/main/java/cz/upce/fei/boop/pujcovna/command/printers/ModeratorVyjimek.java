package cz.upce.fei.boop.pujcovna.command.printers;

import cz.upce.fei.boop.pujcovna.data.model.Motorka;

public final class ModeratorVyjimek {

    public static void nahlasitChybnyPrikaz() { System.out.println("\nChybný príkaz!"); }

    public static void nahlasitChybnyInteger() { System.out.print("\nMusíte zadat celé císlo!"); }

    public static void nahlasitChybnyTyp() { System.out.println("\nChybný typ!"); }

    public static void nahlasitChybnouZnacku() { System.out.print("\nChybná znacka!"); }

    public static void nahlasitChybnouSPZ() { System.out.print("\nChybný formát!"); }

    public static void nahlasitChybnyDouble() { System.out.print("\nChybný formát!"); }

    public static void nahlasitVyjimkuPrikazuNovy(Motorka novaMotorka) { System.out.printf("\nNebyl nastaven aktuální prvek, proto Motorka je ulozena na konec seznamu:\n%s\n", novaMotorka); }

    public static void nahlasitVyjimkuNadji() { System.out.println("\nZádná Motorka neobsahuje takovou hodnotu!"); }

    public static void nahlasitVyjimkuDej() { System.out.println("\nChyba! Není nastaven aktuální prvek, anebo je seznam prázdný."); }

    public static void nahlasitVyjimkuVypis() { System.out.println("\nZatím zádné Motorky v seznamu nejsou."); }

    public static void nahlasitVyjimkuPrvni() { System.out.println("\nChyba! Seznam je prázdný."); }

    public static void nahlasitVyjimkuDalsi() { System.out.println("\nChyba! Není nastaven aktuální prvek nebo je dosazen poslední prvek v seznamu."); }

    public static void nahlasitVyjimkuPosledni() { System.out.println("\nChyba! Seznam je prázdný."); }

    public static void nahlasitVyjimkuVyjmi() { System.out.println("\nChyba! Není nastaven aktuální prvek nebo je seznam prázdný"); }

    public static void nahlasitVujimkuCisloAtributu() { System.out.println("\nChydné císlo atributu!"); }

    public static void nahlasitVyjimkuEdituj() { System.out.println("\nChyba! Je seznam prázdný nebo není nastaven aktuální prvek."); }

    public static void nahlasitVyjimkuOdeber() { System.out.println("\nNebyl nalezen zádný prvek s takovou hodnotou atributu."); }

    public static void nahlasitVyjimkuZalohuj() { System.out.println("\nI/O vyjimka! Seznam nebyl ulozen do binárního souboru."); }

    public static void nahlasitVyjimkuObnov() { System.out.println("\nI/O výjimka! Seznam nebyl obnoven z binárního souboru."); }

    public static void nahlasitVyjimkuUloz() { System.out.println("\nI/O výjimka! Seznam nebyl ulozen do textového souboru."); }

    public static void nahlasitVyjimkuNacti() { System.out.println("\nI/O výjimka! Seznam nebyl nacten z textového souboru."); }
}
