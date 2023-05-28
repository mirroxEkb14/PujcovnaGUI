package kolekce;

import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;
import cz.upce.fei.boop.pujcovna.util.vyjimky.KolekceException;
import org.junit.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 *
 * @author karel@simerda.cz
 */
public class SpojovySeznamTest {

    /**
     * Testovací třída pro ověření implementace třídy SpojovySeznam
     */
    private static class TestClass {

        int a;

        public TestClass(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "T" + a;
        }

    }
    /***
     * Sada instancí testovací třídy pro ověření implementace třídy SpojovySeznam
     */
    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);
    private final TestClass T5 = new TestClass(5);
    private final TestClass T6 = new TestClass(6);
    private final TestClass T7 = new TestClass(7);
    private final TestClass T8 = new TestClass(8);
    private final TestClass T9 = new TestClass(9);

    SpojovySeznam<TestClass> instance;

    public SpojovySeznamTest() {}

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {
        instance = new SpojovySeznam<>();
    }

    @After
    public void tearDown() {
        instance = null;
    }

// TODO Každou veřejnou metodu třídy SpojovySeznam ověřte alespoň jedním testem  
// TODO Dosáhněte 100% pokrytí třídy SpojovySeznam tímto testem    
    
// Ukázka jednoduchého testu    
    @Test
    public void test_01_01_VlozPrvni() {
        try {
            instance.vlozPrvni(T1);
            TestClass result = instance.dejPrvni();
            TestClass expected = T1;
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

// Ukázka testu s vícenásobnou kontrolou stavu testované instance
    @Test
    public void test_01_02_VlozPrvni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T2, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_01_03_VlozPrvni() {
        try {
            instance.vlozPrvni(T3);
            instance.vlozPrvni(T2);
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.dalsi();
            TestClass[] result = {instance.dejPrvni(), instance.dejAktualni()};
            TestClass[] expected = {T1, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_01_04_VlozPrvni() {
        try {
            instance.vlozPrvni(T3);
            instance.vlozPrvni(T2);
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.dalsi();
            TestClass[] result = {instance.dejPrvni(), instance.dejAktualni(), instance.dejZaAktualnim()};
            TestClass[] expected = {T1, T2, T3};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_01_05_VlozPrvni() {
        try {
            instance.vlozPrvni(T3);
            instance.vlozPrvni(T2);
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.dalsi();
            TestClass[] result = {instance.dejZaAktualnim(), instance.dejPosledni()};
            TestClass[] expected = {T3, T3};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_01_06_VlozPrvni() {
        try {
            instance.vlozPrvni(T4);
            instance.vlozPrvni(T3);
            instance.vlozPrvni(T2);
            instance.vlozPrvni(T1);
            final int result = instance.size();
            final int expected = 4;
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

//  Ukázka testu s vyvoláním výjimky   
    @Test(expected = NullPointerException.class)
    public void test_01_07_VlozPrvni() {
        instance.vlozPrvni(null);
        fail();
    }

    /**
     * Tyto pět testů ověří metodu {@code vlozPosledni()}.
     */
    @Test
    public void test_02_01_VlozPosledni() {
        try {
            instance.vlozPosledni(T1);
            TestClass result = instance.dejPosledni();
            TestClass expected = T1;
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_02_02_VlozPosledni() {
        try {
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_02_03_VlozPosledni() {
        try {
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            final TestClass prvniPrvek = instance.dejAktualni();
            final TestClass stredniPrvek = instance.dejZaAktualnim();
            instance.dalsi();
            final TestClass posledniPrvek = instance.dejPosledni();
            TestClass[] result = {prvniPrvek, stredniPrvek, posledniPrvek};
            TestClass[] expected = {T1, T2, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_02_04_VlozPosledni() {
        try {
            final int pocetDo = instance.size();
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            instance.vlozPosledni(T3);
            instance.vlozPosledni(T4);
            final int pocetPo = instance.size();
            int[] result = {pocetDo, pocetPo};
            int[] expected = {0, 4};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(timeout = 200L)
    public void test_02_05_VlozPosledni() {
        for (long i = 0; i < 1_000_000L; i++) {
            instance.vlozPosledni(T1);
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_02_06_VlozPosledni() {
        instance.vlozPosledni(null);
        fail();
    }

    /**
     * Tyto šest testů ověří metodu {@code vlozZaAktualni()}.
     */
    @Test
    public void test_03_01_VlozZaAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.vlozZaAktualni(T2);
            TestClass[] result = {instance.dejAktualni(), instance.dejZaAktualnim()};
            TestClass[] expected = {T1, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_03_02_VlozZaAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.vlozZaAktualni(T2);
            TestClass[] result = {instance.dejZaAktualnim(), instance.dejPosledni()};
            TestClass[] expected = {T2, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_03_03_VlozZaAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPosledni();
            instance.vlozZaAktualni(T3);
            TestClass[] result = {instance.dejPrvni(), instance.dejZaAktualnim(), instance.dejPosledni()};
            TestClass[] expected = {T1, T3, T3};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_03_04_VlozZaAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.nastavPrvni();
            instance.vlozPosledni(T3);
            instance.vlozZaAktualni(T4);
            TestClass[] result = {instance.dejAktualni(), instance.dejZaAktualnim()};
            TestClass[] expected = {T2, T4};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_03_05_VlozZaAktualni() throws KolekceException {
        instance.vlozZaAktualni(null);
        fail();
    }

//    @Test(expected = NullPointerException.class)
//    public void test_03_06_VlozZaAktualni() throws KolekceException {
//
//        fail();
//    }

    /**
     * Tyto šest testů ověří metody {@code dalsi()}, {@code jeDalsi()},
     * {@code dejZaAktualnim()}, {@code pozadatNePrazdy()},
     * {@code pozadatAktualni()}, {@code zrus()}.
     */
    @Test(expected = KolekceException.class)
    public void test_04_01_Dalsi() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.dalsi();
        fail();
    }

    @Test
    public void test_04_02_JeDalsi() {
        try {
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.vlozZaAktualni(T2);
            instance.dalsi();
            instance.vlozZaAktualni(T3);
            instance.dalsi();
            instance.vlozPosledni(T4);
            instance.dalsi();
            assertFalse(instance.jeDalsi());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_04_03_DejZaAktualnim() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.dejZaAktualnim();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_04_04_PozadatNePrazdy() throws KolekceException {
        instance.nastavPrvni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_04_05_PozadatAktualni() throws KolekceException {
        instance.dejAktualni();
        fail();
    }

    @Test
    public void test_04_06_Zrus() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.vlozPosledni(T3);
        instance.nastavPrvni();
        instance.vlozZaAktualni(T2);
        final int pocetDo = instance.size();
        instance.zrus();
        final int pocetPo = instance.size();
        int[] result = {pocetDo, pocetPo};
        int[] expected = {3, 0};
        assertArrayEquals(expected, result);
    }

    /**
     * Tyto pět testů ověří metodu {@code odeberPrvni()}.
     */
    @Test
    public void test_05_01_OdeberPrvni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            TestClass result = instance.odeberPrvni();
            assertEquals(T1, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_05_02_OdeberPrvni() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozPosledni(T2);
        instance.odeberPrvni();
        instance.dejAktualni();
        fail();
    }

    @Test
    public void test_05_03_OdeberPrvni() {
        try {
            instance.vlozPrvni(T1);
            final TestClass odebranyPrvek1 = instance.odeberPrvni();
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.vlozZaAktualni(T3);
            final TestClass odebranyPrvek2 = instance.odeberPrvni();
            TestClass[] result = {odebranyPrvek1, odebranyPrvek2};
            TestClass[] expected = {T1, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_05_04_OdeberPrvni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.vlozZaAktualni(T3);
            instance.dalsi();
            instance.vlozZaAktualni(T4);
            final int pocetDo = instance.size();
            instance.odeberPrvni();
            instance.odeberPrvni();
            final int pocetPo = instance.size();
            int[] result = {pocetDo, pocetPo};
            int[] expected = {4, 2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_05_05_OdeberPrvni() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.odeberPrvni();
        instance.odeberPrvni();
        fail();
    }

    /**
     * Tyto šest testů ověří metodu {@code odeberPosledni()}.
     */
    @Test(expected = KolekceException.class)
    public void test_06_01_OdeberPosledni() throws KolekceException {
        instance.vlozPosledni(T1);
        instance.odeberPrvni();
        instance.odeberPosledni();
        fail();
    }

    @Test
    public void test_06_02_OdeberPosledni() {
        try {
             instance.vlozPrvni(T1);
             TestClass result = instance.odeberPosledni();
             TestClass expected = T1;
             assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_06_03_OdeberPosledni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.odeberPosledni();
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_06_04_OdeberPosledni() {
        try {
            instance.vlozPrvni(T2);
            instance.nastavPrvni();
            instance.vlozPosledni(T3);
            instance.vlozPosledni(T4);
            instance.vlozPrvni(T1);
            final TestClass odebranyPrvek = instance.odeberPosledni();
            TestClass[] result = {instance.dejPrvni(), instance.dejZaAktualnim(), instance.dejPosledni(), odebranyPrvek};
            TestClass[] expected = {T1, T3, T3, T4};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_06_05_OdeberPosledni() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        TestClass[] prvkyDo = {instance.dejPrvni(), instance.dejPosledni()};
        instance.odeberPosledni();
        TestClass[] prvlyPo = {instance.dejPrvni(), instance.dejPosledni()};
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_06_06_OdeberPosledni() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozPosledni(T2);
        instance.odeberPosledni();
        instance.dalsi();
        fail();
    }

    /**
     *  Tyto jedenáct testů ověří metody {@code odeberAktualni()}, {@code odeberZaAktualnim()}.
     */
    @Test(expected = KolekceException.class)
    public void test_07_01_OdeberAktualni() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.odeberAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_07_02_OdeberAktualni() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.odeberPrvni();
        instance.odeberAktualni();
        fail();
    }

    @Test
    public void test_07_03_OdeberAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            TestClass result = instance.odeberAktualni();
            TestClass expected = T1;
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_07_04_OdeberAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.odeberAktualni();
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T2, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_07_05_OdeberAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.dalsi();
            instance.odeberAktualni();
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_07_06_OdeberAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.vlozPosledni(T3);
            instance.vlozZaAktualni(T2);
            instance.dalsi();
            instance.odeberAktualni();
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T3};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_07_07_OdeberAktualni() {
        try {
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.vlozPosledni(T3);
            instance.vlozZaAktualni(T2);
            instance.dalsi();
            instance.dalsi();
            instance.vlozPosledni(T4);
            instance.odeberAktualni();
            instance.nastavPrvni();
            TestClass[] result = {instance.dejPrvni(), instance.dejZaAktualnim(), instance.dejPosledni()};
            TestClass[] expected = {T1, T2, T4};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_07_08_OdeberAktualni() throws KolekceException {
        instance.vlozPosledni(T1);
        instance.vlozPosledni(T2);
        instance.nastavPosledni();
        instance.odeberAktualni();
        instance.dejAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_07_09_OdeberZaAktualnim() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.odeberZaAktualnim();
        fail();
    }

    @Test
    public void test_07_10_OdeberZaAktualnim() {
        try {
            instance.vlozPrvni(T2);
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            TestClass result = instance.odeberZaAktualnim();
            TestClass expected = T2;
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_07_11_OdeberZaAktualnim() {
        try {
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            instance.vlozZaAktualni(T2);
            instance.odeberZaAktualnim();
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    /**
     * Tyto čtyři testy ověří metodu {@code iterator()}.
     */
    @Test
    public void test_08_01_Iterator() {
        try {
            instance.vlozPrvni(T1);
            Iterator<TestClass> iterator = instance.iterator();
            iterator.next();
            assertFalse(iterator.hasNext());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_08_02_Iterator() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            Iterator<TestClass> iterator = instance.iterator();
            iterator.next();
            assertTrue(iterator.hasNext());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void test_08_03_Iterator() {
        Iterator<TestClass> iterator = instance.iterator();
        iterator.next();
        fail();
    }

    @Test
    public void test_08_04_Iterator() {
        try {
            Iterator<TestClass> iterator = instance.iterator();
            assertFalse(iterator.hasNext());
        } catch (Exception ex) {
            fail();
        }
    }

    /**
     * Tento test ověří metodu {@code stream()}.
     */
    @Test
    public void test_09_01_Stream() {
        try {
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            long result = instance.stream().count();
            long expected = instance.size();
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    /**
     * Tento test ověří metodu (@code dejAktualni()).
     */
    @Test(expected = KolekceException.class)
    public void test_10_01_DejAktualni() throws KolekceException {
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.nastavPosledni();
        instance.odeberPosledni();
        instance.dejAktualni();
        fail();
    }
}
