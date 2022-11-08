package sk.caladi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sk.caladi.Kodiva;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DivisibleWithSubstitutionAndMergingTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void divisibleBy2Test() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toSet());
        conditions.put((num) -> num % 2 == 0, "foo");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = """
                1
                foo
                3
                foo
                5
                """;
        assertEquals(output, outContent.toString());
    }

    @Test
    public void divisibleBy2MissingNumber5Test() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toSet());
        conditions.put((num) -> num % 2 == 0, "foo");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = """
                1
                foo
                3
                foo
                """;
        assertNotEquals(output, outContent.toString());
    }

    @Test
    public void divisibleBy4Test() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet());
        conditions.put((num) -> num % 4 == 0, "fuu");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = """
                1
                2
                3
                fuu
                5
                6
                7
                fuu
                9
                """;
        assertEquals(output, outContent.toString());
    }

    @Test
    public void divisibleBy4MissingNumber9Test() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet());
        conditions.put((num) -> num % 4 == 0, "fuu");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = """
                1
                2
                3
                fuu
                5
                6
                7
                fuu
                """;
        assertNotEquals(output, outContent.toString());
    }

    @Test
    public void divisibleBy2And4Test() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet());
        conditions.put((num) -> num % 2 == 0, "foo");
        conditions.put((num) -> num % 4 == 0, "fuu");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = """
                1
                foo
                3
                foofuu
                5
                foo
                7
                foofuu
                9
                """;
        assertEquals(output, outContent.toString());
    }

    @Test
    public void divisibleBy2And4And6Test() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toSet());
        conditions.put((num) -> num % 2 == 0, "foo");
        conditions.put((num) -> num % 4 == 0, "fuu");
        conditions.put((num) -> num % 6 == 0, "faa");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = """
                1
                foo
                3
                foofuu
                5
                foofaa
                7
                foofuu
                9
                foo
                11
                foofuufaa
                """;
        assertEquals(output, outContent.toString());
    }


    @Test
    public void divisibleBy2And4WrongConditionOrderTest() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet());
        conditions.put((num) -> num % 4 == 0, "fuu");
        conditions.put((num) -> num % 2 == 0, "foo");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = """
                1
                foo
                3
                foofuu
                5
                foo
                7
                foofuu
                9
                """;
        assertNotEquals(output, outContent.toString());
    }

    @Test
    public void divisibleEmptyInputSetTest() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = new HashSet<>();
        conditions.put((num) -> num % 4 == 0, "fuu");
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
        String output = "";
        assertEquals(output, outContent.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void divisibleNullInputSetTest() {
        final Map<Predicate<Integer>, String> conditions = new LinkedHashMap<>();
        final Set<Integer> integers = null;
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void divisibleNullInputConditionsTest() {
        final Map<Predicate<Integer>, String> conditions = null;
        final Set<Integer> integers = new HashSet<>();
        Kodiva.printNumbersBySubstitutionAndMerging(conditions, integers);
    }
}
