package com.github.wakingrufus.csv;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class DelimitedLineSplitterTest {

    /**
     *
     */
    public DelimitedLineSplitterTest() {
    }

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of makeLine method, of class DelimitedLineSplitter.
     */
    @Test
    public void testMakeLine_ArrayList() {
        System.out.println("makeLine");
        ArrayList<String> vals = new ArrayList<>();
        vals.add("a");
        vals.add("b");
        DelimitedLineSplitter instance = new DelimitedLineSplitter(",", "\"");
        String expResult = "a,b";
        String result = instance.makeLine(vals);
        assertEquals(expResult, result);
    }

    /**
     * Test of makeLine method, of class DelimitedLineSplitter.
     */
    @Test
    public void testMakeLine_ArrayList_boolean() {
        System.out.println("makeLine");
        ArrayList<String> vals = new ArrayList<>();
        vals.add("a");
        vals.add("b");
        DelimitedLineSplitter instance = new DelimitedLineSplitter(",", "\"");
        String expResult = "a,b";
        String result = instance.makeLine(vals, false);
        assertEquals(expResult, result);
    }

    /**
     * Test of splitLine method, of class DelimitedLineSplitter.
     */
    @Test
    public void testSplitLine() {

        System.out.println("splitLine");
        String line = "a,b";
        DelimitedLineSplitter instance = new DelimitedLineSplitter(",", "\"");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("a");
        expResult.add("b");
        ArrayList<String> result = instance.splitLine(line);
        assertEquals(expResult.get(0), result.get(0));

    }

    @Test
    public void testFullLine() {

        String line =
                "\"BOR\",\"5965983\",\"505001\",\"228\",\"MICHAEL L\",\"TAYLOR\",\"\",\"\",\"513-541-0658\",\"513-607-2106\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"07/25/10\"";
        DelimitedLineSplitter ds = new DelimitedLineSplitter((","), "\"");
        ArrayList<String> result = ds.splitLine(line);
        assertEquals("", result.get(6));
    }

    /**
     * Test of makeLine method, of class DelimitedLineSplitter.
     */
    @Test
    public void testMakeLine_noncomma() {
        System.out.println("makeLine");
        ArrayList<String> vals = new ArrayList<>();
        vals.add("a");
        vals.add("b");
        DelimitedLineSplitter instance = new DelimitedLineSplitter("|", "\"");
        String expResult = "a|b";
        String result = instance.makeLine(vals);
        assertEquals(expResult, result);
    }

    @Test
    public void testTwoDelims() {
        String s = "DBPPHONE,6035957293,6084143,123456789,,";
        DelimitedLineSplitter ds = new DelimitedLineSplitter(",", "\"");
        ArrayList<String> vals = ds.splitLine(s);
        assertEquals("", vals.get(4));
        assertEquals("123456789", vals.get(3));
        assertEquals("", vals.get(5));
    }

    @Test
    public void testescapechar() {
        System.out.println("testescapechar:");
        String s = "DBPPHONE,\"uhwu\"\",dhicwdc\"\"wecwdc\",6084143,123456789,\"\",,";
        DelimitedLineSplitter ds = new DelimitedLineSplitter(",", "\"");
        ArrayList<String> vals = ds.splitLine(s);
        String exp = "uhwu\",dhicwdc\"wecwdc";
        System.out.println(exp);
        System.out.println(vals.get(1));
        assertEquals(exp, vals.get(1));
        assertEquals("DBPPHONE", vals.get(0));
        assertEquals("6084143", vals.get(2));
        assertEquals("", vals.get(4));
        assertEquals("", vals.get(5));
        assertEquals("", vals.get(6));
    }

    @Test
    public void testMakeLineWithEscape() {
        System.out.println("testMakeLineWithEscape:");
        String s = ",DBPPHONE,\"uhwu\"\",dhicwdc\"\"wecwdc\",6084143,123456789,,";
        DelimitedLineSplitter ds = new DelimitedLineSplitter(",", "\"");
        ArrayList<String> vals = new ArrayList<>();
        vals.add("");
        vals.add("DBPPHONE");
        vals.add("uhwu\",dhicwdc\"wecwdc");
        vals.add("6084143");
        vals.add("123456789");
        vals.add("");
        vals.add("");
        System.out.println(s);
        System.out.println(ds.makeLine(vals));
        assertEquals(ds.makeLine(vals), s);
    }

    @Test
    public void testMakeLineWithEscapeAlways() {
        System.out.println("testMakeLineWithEscapeAlways:");
        String s2 = "\"\",\"DBPPHONE\",\"uhwu\"\",dhicwdc\"\"wecwdc\",\"6084143\",\"123456789\",\"\",\"\"";
        DelimitedLineSplitter ds = new DelimitedLineSplitter(",", "\"");
        ArrayList<String> vals = new ArrayList<>();
        vals.add("");
        vals.add("DBPPHONE");
        vals.add("uhwu\",dhicwdc\"wecwdc");
        vals.add("6084143");
        vals.add("123456789");
        vals.add("");
        vals.add("");
        System.out.println(s2);
        System.out.println(ds.makeLine(vals, true));
        assertEquals(ds.makeLine(vals, true), s2);
    }

    @Test
    public void delimitstart() {
        System.out.println("delimitstart:");
        String s = ",2,3,4";
        DelimitedLineSplitter ds = new DelimitedLineSplitter(",", "\"");
        ArrayList<String> vals = ds.splitLine(s);
        assertEquals("2", vals.get(1));
        assertEquals("", vals.get(0));
        assertEquals("3", vals.get(2));
        assertEquals("4", vals.get(3));
    }

    @Test
    public void emptylist() {
        System.out.println("emptylist:");
        DelimitedLineSplitter ds = new DelimitedLineSplitter(",", "\"");
        ArrayList<String> vals = new ArrayList<>();
        String result = ds.makeLine(vals);
        assertEquals(result, "");

    }
}
