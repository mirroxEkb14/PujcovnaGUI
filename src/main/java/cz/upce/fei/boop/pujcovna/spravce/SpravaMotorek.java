package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.model.*;
import cz.upce.fei.boop.pujcovna.data.vycty.TypMotorky;
import cz.upce.fei.boop.pujcovna.data.vycty.Znacka;
import cz.upce.fei.boop.pujcovna.generator.Generator;
import cz.upce.fei.boop.pujcovna.kolekce.Seznam;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;
import cz.upce.fei.boop.pujcovna.perzistence.Perzistence;
import cz.upce.fei.boop.pujcovna.util.vyjimky.ChybnaHodnotaException;
import cz.upce.fei.boop.pujcovna.util.vyjimky.KolekceException;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Singleton třída. Fasáda. Adaptér.
 */
public final class SpravaMotorek implements Ovladani {

    private static final Generator generator = new Generator();

    private static final int POCATECNI_KAPACITA = 10;
    private static final int POCATECNI_INDEX = 0;

    private static final int ZVYSOVACI_CINITEL = 2;

    private static SpravaMotorek instance;

    private Seznam<Motorka> motorky;
    private int kapacita;
    /**
     * Statický atribut, který reprezentuje aktuální počet {@code Motorek} v seznamu.
     */
    private int index;

    public static SpravaMotorek getInstance() {
        if (instance == null)
            return new SpravaMotorek();

        return instance;
    }

    private SpravaMotorek() { nastav(); }

    private void nastav() {
        kapacita = POCATECNI_KAPACITA;
        this.motorky = new SpojovySeznam<>();
        index = POCATECNI_INDEX;
    }

    @Override
    public boolean novy(Motorka novaMotorka) {
        try {
            motorky.vlozZaAktualni(novaMotorka);
            zvysVelikost();
            return true;
        } catch (KolekceException ex) {
            motorky.vlozPosledni(novaMotorka);
            zvysVelikost();
            return false;
        }
    }

    @Override
    public Stream<Motorka> najdi(Comparator<Motorka> comparator, Motorka klic) {
        return motorky
                .stream()
                .filter(d -> comparator.compare(d, klic) == 0);
    }

    @Override
    public Motorka odeber(int id) throws ChybnaHodnotaException {
        final Motorka motorkaProOdebrani = dejMotorkuPodleHodnoty(id)
                .orElseThrow(ChybnaHodnotaException::new);
        final boolean jeZarovenAktualni = jeZarovenAktualni(motorkaProOdebrani);

        if (jeZarovenAktualni) {
            odeberAktualniMotorku();
        } else {
            try {
                if (motorky.dejAktualni() != null)
                    odeberKdyzJeNastavenAktualni(motorkaProOdebrani);
            } catch (KolekceException ex) {
                odeberKdyzNeniNastavenAktualni(motorkaProOdebrani);
            }
        }
        obnovCitacMotorek();
        return motorkaProOdebrani;
    }

    /**
     * Následující metody jsou pomocní pro {@code odeber()}.
     */
    private Optional<Motorka> dejMotorkuPodleHodnoty(int h) {
        return motorky
                .stream()
                .filter(m -> m.getId() == h)
                .findFirst();
    }

    public boolean jeZarovenAktualni(Motorka motorka) {
        try {
            return motorky.dejAktualni().equals(motorka);
        } catch (KolekceException ex) {
            return false;
        }
    }

    private void odeberAktualniMotorku() {
        try {
            motorky.odeberAktualni();
        } catch (KolekceException ignored) {}
    }

    private void odeberKdyzJeNastavenAktualni(Motorka m) {
        try {
            final Motorka docasneUloziste = motorky.dejAktualni();
            motorky.nastavPrvni();
            for (Motorka prvek : motorky) {
                if (prvek.equals(m)) {
                    motorky.odeberAktualni();
                    vratOdkaz(docasneUloziste);
                    return;
                }
                motorky.dalsi();
            }
        } catch (KolekceException ignored) {}
    }

    public void vratOdkaz(Motorka m) throws KolekceException {
        final Iterator<Motorka> seznamIterator = motorky.iterator();
        motorky.nastavPrvni();
        while (seznamIterator.hasNext()) {
            if (seznamIterator.next().equals(m))
                return;
            motorky.dalsi();
        }
    }

    private void odeberKdyzNeniNastavenAktualni(Motorka m) {
        try {
            motorky.nastavPrvni();
            for (Motorka prvek : motorky) {
                if (prvek.equals(m)) {
                    motorky.odeberAktualni();
                    return;
                }
                motorky.dalsi();
            }
        } catch (KolekceException ignored) {}
    }

    private void obnovCitacMotorek() {
        if (this.jePrazdny()) Motorka.resetPocetMotorek();
    }

    @Override
    public Motorka dej() throws KolekceException { return motorky.dejAktualni(); }

    @Override
    public Motorka edituj(Function<Motorka, Motorka> dejUpraveny) throws KolekceException {
        final Motorka aktualniPrvek = motorky.dejAktualni();
        final Motorka podleMotorky = dejUpraveny.apply(aktualniPrvek);
        motorky.vlozZaAktualni(podleMotorky);
        motorky.odeberAktualni();
        Motorka.setCitac(motorky.dejPosledni().getId());
        nastavAktualniPoEditaci(podleMotorky);
        return podleMotorky;
    }

    /**
     * Pomocní metoda pro {@code edituj()}.
     */
    private void nastavAktualniPoEditaci(Motorka upravenaMotorka) {
        try {
            motorky.nastavPrvni();
            Iterator<Motorka> seznamIterator = motorky.iterator();
            while (seznamIterator.hasNext()) {
                if (seznamIterator.next() == upravenaMotorka)
                    break;
                motorky.dalsi();
            }
        } catch (KolekceException ignored) {}
    }

    @Override
    public Motorka vyjmi() throws KolekceException {
        return motorky.odeberAktualni();
    }

    @Override
    public void prvni() throws KolekceException { motorky.nastavPrvni(); }

    @Override
    public void dalsi() throws KolekceException { motorky.dalsi(); }

    @Override
    public void posledni() throws KolekceException { motorky.nastavPosledni(); }

    @Override
    public int pocet() { return motorky.size(); }

    @Override
    public boolean obnov() throws IOException {
        try {
            Perzistence.nactiBin(motorky);
            Motorka.setCitac(motorky.dejPosledni().getId());
            return true;
        } catch (IOException ignored) {
            return false;
        } catch (KolekceException ex) {
            Motorka.resetPocetMotorek();
            return false;
        }
    }

    @Override
    public boolean zalohuj() throws IOException {
        try {
            Perzistence.ulozBin(motorky);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public void vypis() {
        final Stream<Motorka> datovod = dejDatovod();
        Perzistence.vypisDoKonzole(datovod);
    }

    @Override
    public Stream<Motorka> dejDatovod() {
        return motorky.stream();
    }

    @Override
    public boolean nacti() throws IOException {
        final Optional<String> data = Perzistence.nactiText();
        if (data.isPresent()) {
            Perzistence.vypisDoKonzole(data.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean uloz(String jmenoSouboru) throws IOException {
        try {
            Perzistence.ulozText(motorky, jmenoSouboru);
            return true;
        } catch (IOException ignored) {}
        return false;
    }

    @Override
    public void generuj(int pocet) { generator.generuj(motorky, pocet); }

    @Override
    public void zrus() {
        motorky.zrus();
        Motorka.resetPocetMotorek();
    }

    @Override
    public Iterator<Motorka> iterator() { return motorky.iterator(); }

    public boolean nactiTextDoSeznamu() throws IOException {
        final Optional<String> data = Perzistence.nactiText();
        if (data.isPresent()) {
            final String[] pole = data.get().split(Perzistence.TEXTOVY_ODDELOVAC);
            motorky.zrus();
            for (String s : pole) {
                final Motorka nactenyPrvek = dejMotorkuPodleAtributu(s);
                motorky.vlozPosledni(nactenyPrvek);
            }
            return true;
        }
        throw new IOException();
    }

    private Motorka dejMotorkuPodleAtributu(String pole) {
        final String[] seznamDat = pole.split(Perzistence.ODDELOVAC_PARAMETRU);

        final String nactenyTyp = seznamDat[1].split(Perzistence.ODDELOVAC_HODNOT)[1];
        final String nactenaZnacka = seznamDat[2].split(Perzistence.ODDELOVAC_HODNOT)[1];

        final TypMotorky typ = TypMotorky.dejPodleNazvu(nactenyTyp);
        final Znacka znacka = Znacka.dejPodleNazvu(nactenaZnacka);
        final String spz = seznamDat[3].split(Perzistence.ODDELOVAC_HODNOT)[1];
        final double cena24h = Double.parseDouble(seznamDat[4].split(Perzistence.ODDELOVAC_HODNOT)[1].trim());

        Objects.requireNonNull(typ);
        return switch (typ) {
            case RETRO_MOTORKA -> new RetroMotorka(
                    znacka,
                    spz,
                    cena24h,
                    Double.parseDouble(seznamDat[5].split(Perzistence.ODDELOVAC_HODNOT)[1].trim())
            );
            case SPORTOVNI_MOTORKA -> new SportovniMotorka(
                    znacka,
                    spz,
                    cena24h,
                    Integer.parseInt(seznamDat[5].split(Perzistence.ODDELOVAC_HODNOT)[1].trim())
            );
            case STANDARDNI_MOTORKA -> new StandardniMotorka(
                    znacka,
                    spz,
                    cena24h,
                    Integer.parseInt(seznamDat[5].split(Perzistence.ODDELOVAC_HODNOT)[1].trim())
            );
            case TERENNI_MOTORKA -> new TerenniMotorka(
                    znacka,
                    spz,
                    cena24h,
                    Double.parseDouble(seznamDat[5].split(Perzistence.ODDELOVAC_HODNOT)[1].trim())
            );
            default -> null; // nedosazitelný kód
        };
    }

    public Motorka dejAktualniMotorku() throws KolekceException { return motorky.dejAktualni(); }

    /**
     * Aby počet {@code Motorek} nepřekročil kapacitu, metoda kontroluje, zda je již roven
     * nebo větší než kapacita, pokud ano - zvýší statický atribut kapacity o dvojnásobek.
     */
    public void zvysVelikost() {
        if (++index >= kapacita)
            kapacita *= ZVYSOVACI_CINITEL;
    }

    private boolean jePrazdny() { return pocet() == 0; }
}
