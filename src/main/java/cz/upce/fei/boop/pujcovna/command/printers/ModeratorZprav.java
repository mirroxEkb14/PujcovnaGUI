package cz.upce.fei.boop.pujcovna.command.printers;

import cz.upce.fei.boop.pujcovna.data.model.Motorka;
import cz.upce.fei.boop.pujcovna.data.vycty.Atribut;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;

import java.util.Locale;

public final class ModeratorZprav {

    private static final String ODRADKOVANI = "\n";

    public static String getOdradkovani() { return ODRADKOVANI; }

    public static void odradkovani() { System.out.print(ODRADKOVANI); }

    public static void nashle() { System.out.println("\nNa shledanou!"); }

    public static void help() {
        ModeratorZprav.odradkovani();
        System.out.print("""
                 help, h     - výpis príkazu
                 novy,no     - vytvor novou instanci a vloz data za aktuální prvek
                 najdi,na,n  - najdi v seznamu data podle hodnoty nejakém atributu
                 odeber,od   - odeber data ze seznamu podle nejaké hodnoty atributu
                 dej         - zobraz aktuální data v seznamu
                 edituj,edit - edituj aktuální data v seznamu
                 vyjmi       - vyjmi aktuální data ze seznamu
                 prvni,pr    - nastav jako aktuální první data v seznamu
                 dalsi,da    - prejdi na dalsí data
                 posledni,po - prejdi na poslední data
                 pocet       - zobraz pocet polozek v seznamu
                 obnov       - obnov seznam data z binárního souboru
                 zalohuj     - zálohuj seznam dat do binárního souboru
                 vypis       - zobraz seznam dat
                 nactitext,nt- nacti seznam data z textového souboru
                 uloztext,ut - uloz seznam data do textového souboru
                 generuj,g   - generuj náhodne data pro testování
                 zrus        - zrus všechny data v seznamu
                 exit        - ukoncení programu
                """);
    }

    public static void poprositPrikaz() { System.out.print("\nZadejte príkaz: "); }

    public static void poprositNovyTyp() { System.out.printf("\nZadejte typ z nabídky:\n%s", getVariantyTypu()); }

    /**
     * Pomocní metoda pro {@code poprositNovyTyp()}.
     */
    private static String getVariantyTypu() {
        final String retro = TypMotorky.RETRO_MOTORKA.nazev().toUpperCase(Locale.ENGLISH);
        final String sportovni = TypMotorky.SPORTOVNI_MOTORKA.nazev().toUpperCase(Locale.ENGLISH);
        final String standardni = TypMotorky.STANDARDNI_MOTORKA.nazev().toUpperCase(Locale.ENGLISH);
        final String terenni = TypMotorky.TERENNI_MOTORKA.nazev().toUpperCase(Locale.ENGLISH);
        return String.format("""
                %s - %d
                %s - %d
                %s - %d
                %s - %d
                """, retro, TypMotorky.RETRO_CISLO, sportovni, TypMotorky.SPORTOVNI_CISLO,
                standardni, TypMotorky.STANDARDNI_CISLO, terenni, TypMotorky.TERENNI_CISLO);
    }

    public static void poprositNovouZnacku() { System.out.printf("\nZadejte znacku z nabídky:\n%s", getVariantyZnacek()); }

    /**
     * Pomocní metoda pro {@code poprositNovouZnacku()}.
     */
    private static String getVariantyZnacek() {
        final String bmw = Znacka.BMW.nazev().toLowerCase(Locale.ENGLISH);
        final String honda = Znacka.HONDA.nazev().toLowerCase(Locale.ENGLISH);
        return String.format("""
                %s - %d
                %s - %d
                """, bmw, Znacka.BMW_CISLO, honda, Znacka.HONDA_CISLO);
    }

    public static void poprositNovouSPZ() { System.out.println("\nZadejte SPZ ve formátu: 0A0 0000"); }

    public static void poprositNovouCenu24h() { System.out.println("\nZadejte cenu ve formátu: 0.0"); }

    public static void poprositNovouHmotnost() { System.out.println("\nZadejte hmotnost Retro Motorky ve formátu: 0.0"); }

    public static void poprositNovyPocetValcu() { System.out.println("\nZadejte pocet válcu Sportovní Motorky: "); }

    public static void poprositNovyPocetRychlosti() { System.out.println("\nZadejte pocet rychlostí Standardní Motorky: "); }

    public static void poprositNovouSpotrebuPaliva() { System.out.println("\nZadejte spotrebu paliva Terenní Motorky: "); }

    public static void nahlasitVysledekPrikazuNovy(Motorka novaMotorka) { System.out.printf("\nMotorka byla úspesne pridána za aktuální prvek:\n%s\n", novaMotorka); }

    public static void poprositHodnotuProHledani() { System.out.print("\nZadejte id nejaké Motorky: "); }

    public static void nahlasitVysledekNadji(Motorka motorka) { System.out.printf("\nNalezená Motorka:\n%s\n", motorka); }

    public static void nahlasitVysledekDej(Motorka aktualniMotorka) { System.out.printf("\nAktuální prvek:\n%s\n", aktualniMotorka); }

    public static void nahlasitVysledekZrus() { System.out.println("\nVsechny polozky byly zruseny."); }

    public static void nahlasitVysledekPrvni() { System.out.println("\nPrvní prvek byl nastaven jako aktuální."); }

    public static void nahlasitVysledekDalsi() { System.out.println("\nAktuální prvek byl prestaven na dalsí prvek seznamu."); }

    public static void nahlasitVysledekPosledni() { System.out.println("\nPoslední prvek byl nastaven jako aktuální."); }

    public static void nahlasitVysledekPocet(int velikost) { System.out.printf("\nPocet polozek v seznamu: %d\n", velikost); }

    public static void nahlasitVysledekVyjmi(Motorka odebranyPrvek) { System.out.printf("\nAktuální prvek byl odebrán:\n%s\n", odebranyPrvek); }

    public static void poprositRetroAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.odradkovani();
        System.out.printf("""
                Aktuální prvek: %s
                Jaký atribut byste chteli editovat?
                Znacka - %d
                SPZ - %d
                Cena24h - %d
                Hmotnost - %d
                """, aktualniPrvek, Atribut.ZNACKA.identifikator(), Atribut.SPZ.identifikator(),
                Atribut.CENA24H.identifikator(), Atribut.HMOTNOST.identifikator());
    }

    public static void poprositSportovniAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.odradkovani();
        System.out.printf("""
                Aktuální prvek: %s
                Jaký atribut byste chteli editovat?
                Znacka - %d
                SPZ - %d
                Cena24h - %d
                Pocet válcu - %d
                """, aktualniPrvek, Atribut.ZNACKA.identifikator(), Atribut.SPZ.identifikator(),
                Atribut.CENA24H.identifikator(), Atribut.POCET_VALCU.identifikator());
    }

    public static void poprositStandardniAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.odradkovani();
        System.out.printf("""
                Aktuální prvek: %s
                Jaký atribut byste chteli editovat?
                Znacka - %d
                SPZ - %d
                Cena24h - %d
                Pocet rychlostí - %d
                """, aktualniPrvek, Atribut.ZNACKA.identifikator(), Atribut.SPZ.identifikator(),
                Atribut.CENA24H.identifikator(), Atribut.POCET_RYCHLOSTI.identifikator());
    }

    public static void poprositTerenniAtributProEditaci(Motorka aktualniPrvek) {
        ModeratorZprav.odradkovani();
        System.out.printf("""
                Aktuální prvek: %s
                Jaký atribut byste chteli editovat?
                Znacka - %d
                SPZ - %d
                Cena24h - %d
                Spotreba paliva - %d
                """, aktualniPrvek, Atribut.ZNACKA.identifikator(), Atribut.SPZ.identifikator(),
                Atribut.CENA24H.identifikator(), Atribut.SPOTREBA_PALIVA.identifikator());
    }

    public static void nahlasitVysledekEdituj(Motorka upravenyPrvek) { System.out.printf("\nAktuální prvek je upraven:\n%s\n", upravenyPrvek); }

    public static void poprositHodnotuProOdebrani() { System.out.print("\nZadejte id, podle kterého bude prvek odebrán: "); }

    public static void nahlasitVysledekOdeberNeniAktualni(Motorka odebranyPrvek) { System.out.printf("\nPrvek byl odebrán: %s\n", odebranyPrvek); }

    public static void nahlasitVysledekOdeberJeAktualni(Motorka odebranyPrvek) { System.out.printf("\nByl odebrán prvek, který zároveň byl nastaven jako aktuální:\n%s\n", odebranyPrvek); }

    public static void nahlasitVysledekZalohuj() { System.out.println("\nSeznam byl ulozen do binárního souboru."); }

    public static void nahlasitVysledekObnov() { System.out.println("\nSeznam byl obnoven z binárního souboru."); }

    public static void poprositJmenoTextovehoSouboru() { System.out.print("\nZadejte jméno souboru (bez přípony): "); }

    public static void nahlasitVysledekUloz() { System.out.println("\nSeznam byl ulozen do textového souboru."); }

    public static void nahlasitVysledekNactiPrazdny() { System.out.println("\nV textovém soubotu žádná data zatím nejsou!"); }

    public static void poprositPocetProGeneraci() { System.out.print("\nZadejte počet Motorek pro generaci: "); }

    public static void nahlasitVysledekGeneruj() { System.out.println("\nData byla vygenerována!"); }
}
