package sk.caladi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Kodiva {

    public static void main(String[] args) {
        final Set<Integer> integers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toSet());
        final Map<Predicate<Integer>, String> conditionsMerging = new LinkedHashMap<>();
        conditionsMerging.put((num) -> num % 2 == 0, "foo");
        conditionsMerging.put((num) -> num % 4 == 0, "fuu");
        printNumbersBySubstitutionAndMerging(conditionsMerging, integers);
    }

    public static void printNumbersBySubstitutionAndMerging(Map<Predicate<Integer>, String> conditions, Set<Integer> integers) {
        if (integers == null) throw new IllegalArgumentException("A set of integers must be present");
        if (conditions == null) throw new IllegalArgumentException("A set of conditions must be present");
        for (Integer i : integers) {
            String number = "";
            for (Predicate<Integer> condition : conditions.keySet()) {
                if (condition.test(i)) {
                    number += conditions.get(condition);
                }
            }
            System.out.println((number.isEmpty()) ? String.valueOf(i) : number);
        }
    }
}