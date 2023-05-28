package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Třída obsahuje statické metody pro ukládání a načtení binárních nebo testových dat.
 */
public final class Perzistence {

    private static final String TRVALA_CESTA_BIN = "src/main/java/cz/upce/fei/boop/pujcovna/util/%s";
    private static final String TRVALE_JMENO_BIN_SOUBORU = "data";
    private static final String TRVALA_PRIPONA_BIN_SOUBORU = ".bin";

    private static String FLEXIVNI_CESTA_TXT = "src/main/java/cz/upce/fei/boop/pujcovna/util/%s";
    private static final String TRVALE_JMENO_TXT_SOUBORU = "data";
    private static final String TRVALA_PRIPONA_TXT_SOUBORU = ".txt";
    public static final String TEXTOVY_ODDELOVAC = "\n";
    public static final String ODDELOVAC_PARAMETRU = ", ";
    public static final String ODDELOVAC_HODNOT = "=";

    /**
     * Generická metoda uloží data do binárního souboru.
     *
     * @param seznam Seznam prvků pro uložení.
     * @param <T> Typový parametr prvků, které jsou v seznamu.
     *
     * @throws IOException Vystaví se v procesu uložení dat.
     */
    public static <T> void ulozBin(SpojovySeznam<T> seznam) throws IOException {
        final String cesta = dejCelouCestuBin();
        try (ObjectOutputStream vystup = new ObjectOutputStream(new FileOutputStream(cesta))) {
            Objects.requireNonNull(seznam);

            vystup.writeInt(seznam.size());
            Iterator<T> it = seznam.iterator();
            while (it.hasNext()) {
                vystup.writeObject(it.next());
            }

        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Generická metoda načte data z binárního souboru.
     *
     * @param seznam Seznam prvků pro čtení.
     * @param <T> Typový parametr prvků v seznamu.
     *
     * @throws IOException Vystaví se v procesu čtení dat.
     */
    public static <T> SpojovySeznam<T> nactiBin(SpojovySeznam<T> seznam) throws IOException {
        final String cesta = dejCelouCestuBin();
        try (ObjectInputStream vstup = new ObjectInputStream(new FileInputStream(cesta))) {
            Objects.requireNonNull(seznam);

            seznam.zrus();
            final int pocet = vstup.readInt();
            for (int i = 0; i < pocet; i++) {
                seznam.vlozPosledni((T) vstup.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new IOException(ex);
        }
        return seznam;
    }

    /**
     * Generická metoda ulozí data do textového souboru.
     *
     * @param seznam Seznam prvků pro uložení.
     * @param jmenoSouboru Jméno s příponou pro soubor.
     * @param <T> Typový parametr prvků.
     *
     * @throws IOException Vystaví se v procesu uložení dat.
     */
    public static <T> void ulozText(SpojovySeznam<T> seznam, String jmenoSouboru) throws IOException {
        final String cesta = dejCelouCestuTxt(jmenoSouboru);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(cesta))) {
            final Iterator<T> seznamIterator = seznam.iterator();
            while (seznamIterator.hasNext()) {
                bw.write(seznamIterator.next().toString());
                bw.newLine();
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Metoda načte seznam data z textového souboru.
     *
     * @return Vrací obsah textového souboru v podobě {@code String}, zabalený v {@code Optional}.
     *
     * @throws IOException Vystaví se v procesu čtení dat.
     */
    public static Optional<String> nactiText() throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(dejCelouCestuTxt(TRVALE_JMENO_TXT_SOUBORU)))) {
            String radek = br.readLine();
            while (radek != null) {
                sb.append(radek);
                sb.append(TEXTOVY_ODDELOVAC);
                radek = br.readLine();
            }
        } catch (IOException ex) {
            if (jeCestaTxt())
                throw new IOException(ex);
        }
        return Optional.of(sb.toString());
    }

    public static <T> void vypisDoKonzole(Stream<T> datovod) { datovod.forEach(System.out::println); }

    public static void vypisDoKonzole(String vystup) { System.out.println(vystup); }

    private static String dejCelouCestuBin() {
        return String.format(TRVALA_CESTA_BIN, TRVALE_JMENO_BIN_SOUBORU + TRVALA_PRIPONA_BIN_SOUBORU);
    }

    private static String dejCelouCestuTxt(String jmenoSouboru) {
        FLEXIVNI_CESTA_TXT = String.format(FLEXIVNI_CESTA_TXT, jmenoSouboru + TRVALA_PRIPONA_TXT_SOUBORU);
        return FLEXIVNI_CESTA_TXT;
    }

    private static boolean jeCestaTxt() { return FLEXIVNI_CESTA_TXT.endsWith(TRVALA_PRIPONA_TXT_SOUBORU); }
}
