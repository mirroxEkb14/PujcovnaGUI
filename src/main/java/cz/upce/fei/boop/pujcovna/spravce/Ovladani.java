package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.model.Motorka;
import cz.upce.fei.boop.pujcovna.util.vyjimky.ChybnaHodnotaException;
import cz.upce.fei.boop.pujcovna.util.vyjimky.KolekceException;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Interfejs deklaruje metody pro implementaci všech příkazů.
 */
public interface Ovladani extends Iterable<Motorka> {

    /**
     * Metoda vytvoří novou instanci a vloží data do seznamu za aktuální prvek.
     * V případě, že metoda zjistí, že nebyl nastaven aktuální prvek, tak vloží
     * data za poslední prvek.
     *
     * @param novyPrvek Nová položka, která bude přidána do seznamu.
     *
     * @return Vrací {@code true} když byl nastaven aktuální prvek a nový prvek je
     * uložen za aktuální, {@code false} když nebyl nastaven aktuální prvek a nový
     * prvek je ulože na konec seznamu.
     */
    boolean novy(Motorka novyPrvek);

    /**
     * Metoda najde v seznamu data podle vstupního kliče a {@code Comparatoru}. Klič a
     * {@code Comparator} budou vytrořeny a budou se porovnávat podle hodnoty uživatele,
     * tj. podle id {@code Motorky}.
     *
     * @param comparator Vstupní {@code Comparator}, podle implementace kterého metoda
     *                   hledá položku v seznamu.
     * @param klic Prvek, podle kterého metoda hledá data.
     *
     * @return Vrací datavod nalezených prvků.
     */
    Stream<Motorka> najdi(Comparator<Motorka> comparator, Motorka klic);

    /**
     * Metoda odebere data ze seznamu podle id.
     * <p>
     * Metoda mění aktuální počet objektů v seznamu.
     * <p>
     * Pokud prvek pro odebrání není nastaven jeko aktuální, tak dochází do druhého bloku {@code try},
     * kde metoda nejdříve zkontroluje, zda je vůbec nastaven odkaz na aktuální prvek seznamu,
     * pokud ano, tak metoda uloží tento prvek do dočasné proměnné a začne iteraci až se dostane do
     * prvku, pak ho nastaví jako aktuální a odebere, potom, podle uloženého prvku, najde ho v seznamu
     * a nastaví jako aktuálni, jak to bylo před odebíráním; pokud ne, metoda začne iteraci hned, nastaví
     * prvek jako aktuální a zavolá {@code odeberAktualni()} - aktuální prvek potom nastaven nebude.
     *
     * @param id Hodnota atributu, podle které bude prvek obebrán.
     *
     * @return Odebraný prvek.
     *
     * @throws ChybnaHodnotaException Výjimka se vystaví, když nebyl nalezen žádný prvek s takou
     * hodnotou atributu.
     */
    Motorka odeber(int id) throws ChybnaHodnotaException;

    /**
     * Přístupová metoda vrátí odkaz na aktuální data v seznamu.
     *
     * @return Odkaz na aktuální data.
     *
     * @throws KolekceException Výjimka se vystaví, když není nastaven aktuální prvek,
     * anebo je seznam prázdný.
     */
    Motorka dej() throws KolekceException;

    /**
     * Metoda edituje aktuální data v seznamu.
     *
     * @return Prvek po editaci.
     *
     * @param dejUpraveny Funkční interfejs, transformující aktuální prvek. Jako vstupní
     *                    parametr přijímá aktuální {@code Motorku}, jako výstup vrací
     *                    již editovanou {@code Motorku}.
     *
     * @throws KolekceException Když nebyl nastaven aktuální prvek nebo byl seznam prázdný.
     */
    Motorka edituj(Function<Motorka, Motorka> dejUpraveny) throws KolekceException;

    /**
     * Metoda odebere aktuální data ze seznamu.
     * <p>
     * Metoda mění aktuální počet objektů v seznamu.
     *
     * @return Odkaz na odebraný prvek.
     *
     * @throws KolekceException Když nebyl nastaven aktuální prvek nebo byl seznam prázdný.
     */
    Motorka vyjmi() throws KolekceException;

    /**
     * Metoda nastaví jako aktuální první data v seznamu.
     *
     * @throws KolekceException Když byl seznam prázdný.
     */
    void prvni() throws KolekceException;

    /**
     * Metoda pohybu přestaví aktuální ukazatel na další prvek seznamu.
     *
     * @throws KolekceException Když nebyl nastaven aktuální prvek nebo byl
     * dosažen poslední prvek.
     */
    void dalsi() throws KolekceException;

    /**
     * Metoda pohybu nastaví aktuální ukazatel na poslední data seznamu.
     *
     * @throws KolekceException Když je seznam prázdný.
     */
    void posledni() throws KolekceException;

    /**
     * Metoda vrací aktuální pocet polozek v seznamu.
     *
     * @return Vrací hodnotu s počtem polozek v seznamu.
     */
    int pocet();

    /**
     * Metoda obnoví seznam dat z binárního souboru.
     *
     * @return Vrací {@code true}, když data byla úspěšně načtena a seznam byl obnoven.
     *
     * @throws IOException Výjimka se vystaví při čtení dat z binárního souboru. .
     */
    boolean obnov() throws IOException;

    /**
     * Metoda zálohuje seznam dat do binárního souboru.
     *
     * @return Vrací {@code true}, když data byla úspěšně zálohována.
     *
     * @throws IOException Výjimka se vystaví při uložení dat z binárního souboru. .
     */
    boolean zalohuj() throws IOException;

    /**
     * Metoda zobrazí seznam dat.
     */
    void vypis();

    /**
     * Metoda dodá datový stream. Je pomocní pro {@code vypis}.
     *
     * @return Datový stream prvků.
     */
    Stream<Motorka> dejDatovod();

    /**
     * Metoda načte seznam dat z textového souboru. Pokud soubor ne prázdný, metoda vrátí prázdný {@code Optional}.
     *
     * @return Vrací {@code true} když se nevystavila výjimka a data byla úspěšně načtena.
     *
     * @throws IOException Když se objeví nějaká výjimka při čtení dat z textového souboru.
     */
    boolean nacti() throws IOException;

    /**
     * Metoda ulozí seznam dat do textového souboru.
     *
     * @param jmenoSouboru Jméno souboru s příponou, kde budou data uložena.
     *
     * @return Vrací {@code true}, když data byla úspěšně uložena a nebyla chyhozena výjimka.
     *
     * @throws IOException Když se objeví nějaká výjimka při uložení dat do textového souboru.
     */
    boolean uloz(String jmenoSouboru) throws IOException;

    /**
     * Metoda generuje náhodne data pro testování.
     *
     * @param pocet Počet {@code Motorek} pro generaci.
     */
    void generuj(int pocet);

    /**
     * Metoda zruší všechny data v seznamu a vynuluje {@code id} Motorek.
     */
    void zrus();

    /**
     * Metoda zpracovává vstupní lambdu (anonymní finkci), která má být v podobě referenci na
     * metody tříd z balíčku {@code printers}.
     *
     * Používá se v příkazovém řádku.
     */
    default void nahlasErrorLog(Runnable lambda) {
        lambda.run();
    }

    /**
     * Metoda zpracovává vstupní lambdu (anonymní finkci), která má zobrazovat alert
     * uživateli se vstupnou zprávou.
     *
     * Používá se v GUI.
     *
     * @param lambda {@code Consumer}, který by měl vytvářet nový alert.
     * @param zprava zpráva pro uživatele.
     */
    default void nahlasErrorLog(Consumer<String> lambda, String zprava) {
        lambda.accept(zprava);
    }
}
