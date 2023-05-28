package cz.upce.fei.boop.pujcovna.kolekce;

import cz.upce.fei.boop.pujcovna.util.vyjimky.KolekceException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SpojovySeznam<E> implements Seznam<E> {

    private Prvek<E> prvni;
    private Prvek<E> aktualni;
    private Prvek<E> posledni;
    private int pocet;

    private static class Prvek<E> {
        private final E polozka;
        private Prvek<E> dalsi;

        private Prvek(E polozka) {
            this.polozka = polozka;
        }
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        pozadatNePrazdy();
        aktualni = prvni;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        pozadatNePrazdy();
        aktualni = posledni;
    }

    /**
     * Privátní pomocní metoda pro kontrolu počtu prvků v seznamu.
     *
     * @throws KolekceException, když je seznam prázdný.
     */
    private void pozadatNePrazdy() throws KolekceException {
        if (jePrazdny())
            throw new KolekceException();
    }

    @Override
    public void dalsi() throws KolekceException {
        if (!jeDalsi()) {
            throw new KolekceException();
        }
        aktualni = aktualni.dalsi;
    }

    @Override
    public boolean jeDalsi() {
        return aktualni != null && aktualni.dalsi != null;
    }

    @Override
    public void vlozPrvni(E data) {
        pozadatNeNull(data);

        final Prvek<E> novyPrvek = new Prvek<>(data);
        if (prvni == null) {
            prvni  = posledni = novyPrvek;
        } else {
            novyPrvek.dalsi = prvni;
            prvni = novyPrvek;
        }
        zvysPocet();
    }

    @Override
    public void vlozPosledni(E data) {
        pozadatNeNull(data);

        final Prvek<E> novyPrvek = new Prvek<>(data);
        if (prvni == null) {
            prvni  = posledni = novyPrvek;
        } else {
            Prvek<E> aktualniPrvek = posledni;
            aktualniPrvek.dalsi = posledni = novyPrvek;
        }
        zvysPocet();
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException();
        }
        pozadatNeNull(data);

        final Prvek<E> novyPrvek = new Prvek<>(data);
        if (aktualni == prvni) {
            novyPrvek.dalsi = prvni.dalsi;
            prvni.dalsi = novyPrvek;
            zmenPosledni(prvni, novyPrvek);
        } else {
            Prvek<E> aktualniPrvek = prvni;
            while (aktualniPrvek != aktualni) {
                aktualniPrvek = aktualniPrvek.dalsi;
            }
            novyPrvek.dalsi = aktualniPrvek.dalsi;
            aktualniPrvek.dalsi = novyPrvek;
            zmenPosledni(aktualniPrvek, novyPrvek);
        }
        zvysPocet();
    }

    /**
     * @param objekt datová entita typu E.
     * @throws NullPointerException, když atribut je {@code null}.
     */
    private void pozadatNeNull(E objekt) {
        if (objekt == null)
            throw new NullPointerException();
    }

    /**
     * Metoda posune ukazatal na poslední prvek.
     *
     * @param porovnavaciPrvek  prvek, se kterým porovnáváme ukazatel na poslední prvek.
     * @param prirazovaciPrvek  prvek, který přiradáme k ukazateli posledního prvku.
     */
    private void zmenPosledni(Prvek<E> porovnavaciPrvek, Prvek<E> prirazovaciPrvek) {
        if (porovnavaciPrvek == posledni)
            posledni = prirazovaciPrvek;
    }

    /**
     * Privátní metoda, která zvyšuje počet prvků v seznamu.
     */
    private void zvysPocet() {
        pocet++;
    }

    @Override
    public boolean jePrazdny() {
        return pocet == 0;
    }

    @Override
    public E dejPrvni() throws KolekceException {
        pozadatNePrazdy();
        return prvni.polozka;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        pozadatNePrazdy();
        return posledni.polozka;
    }

    @Override
    public E dejAktualni() throws KolekceException {
        pozadatAktualni();
        return aktualni.polozka;
    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        pozadatZaAktualnim();
        return aktualni.dalsi.polozka;
    }

    /**
     * Privátní pomocní metoda zajišťuje, že seznam není prázdný a
     * aktuálně nastavený prvek a jeho další jsou nastaveny.
     *
     * @throws KolekceException, když je seznam prázdný anebo aktuálně
     * nastavený prvek je {@code null}.
     */
    private void pozadatZaAktualnim() throws KolekceException {
        if (jePrazdny() || aktualni == null || aktualni.dalsi == null)
            throw new KolekceException();
    }

    /**
     * Privátní pomocní metoda zajišťuje, že seznam není prázdný a
     * aktuálně nastavený prvek je nastaven.
     *
     * @throws KolekceException, když je seznam prázdný anebo aktuálně
     * nastavený prvek je {@code null}.
     */
    private void pozadatAktualni() throws KolekceException {
        if (jePrazdny() || aktualni == null)
            throw new KolekceException();
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        pozadatNePrazdy();
        if (prvni == aktualni) {
            aktualni = null;
        }

        final E odebranyPolozka = prvni.polozka;
        final Prvek<E> dalsiPrvniho = prvni.dalsi;
        prvni = dalsiPrvniho;
        zmenPosledni(dalsiPrvniho, null);
        snizPocet();
        return odebranyPolozka;
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        pozadatNePrazdy();
        if (posledni == aktualni) {
            aktualni = null;
        }

        final E odebranyPolozka = posledni.polozka;
        if (prvni.dalsi == null) {
            prvni = null;
        } else {
            Prvek<E> aktualniPrvek = prvni;
            while (aktualniPrvek.dalsi.dalsi != null) {
                aktualniPrvek = aktualniPrvek.dalsi;
            }
            aktualniPrvek.dalsi = null;
            posledni = aktualniPrvek;
        }
        snizPocet();
        return odebranyPolozka;
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        pozadatAktualni();
        return odeberPrvek(aktualni.polozka);
    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {
        pozadatZaAktualnim();
        return odeberPrvek(aktualni.dalsi.polozka);
    }

    /**
     * @param klic  hodnota prvku v seznamu, kterou musíme odebrat.
     * @return      vrací tuto samu odebranou hodnotu.
     *
     * jestli je ten prvek aktualni
     */
    private E odeberPrvek(E klic) {
        Prvek<E> aktualniPrvek = prvni;

        if (aktualniPrvek.polozka.equals(klic)) {
            final Prvek<E> dalsiPrvniho = prvni.dalsi;
            zmenAktualni(aktualniPrvek);
            prvni = dalsiPrvniho;
            zmenPosledni(prvni, dalsiPrvniho);
        } else {
            while (aktualniPrvek.dalsi != null) {
                if (aktualniPrvek.dalsi.polozka.equals(klic)) {
                    final Prvek<E> dalsiAktualniho = aktualniPrvek.dalsi;
                    zmenAktualni(dalsiAktualniho);
                    aktualniPrvek.dalsi = aktualniPrvek.dalsi.dalsi;
                    zmenPosledni(dalsiAktualniho, aktualniPrvek);
                    break;
                }
                aktualniPrvek = aktualniPrvek.dalsi;
            }
        }
        snizPocet();
        return klic;
    }

    /**
     * Pomocní metoda pro odeberPrvek(E klic).
     */
    private void zmenAktualni(Prvek<E> odebranyPrvek) {
        if (aktualni == odebranyPrvek)
            aktualni = null;
    }

    /**
     * Privátní metoda, která snižuje počet prvků v seznamu.
     */
    private void snizPocet() {
        pocet--;
    }

    @Override
    public int size() {
        return pocet;
    }

    @Override
    public void zrus() {
        prvni = posledni = aktualni = null;
        pocet = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            Prvek<E> aktualniPrvek = prvni;

            @Override
            public boolean hasNext() {
                return aktualniPrvek != null;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    E data = aktualniPrvek.polozka;
                    aktualniPrvek = aktualniPrvek.dalsi;
                    return data;
                }
                throw new NoSuchElementException();
            }
        };
    }
}
