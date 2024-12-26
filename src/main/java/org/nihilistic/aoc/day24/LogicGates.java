package org.nihilistic.aoc.day24;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record LogicGates(Map<String, Integer> values, Map<String, Gate> gates) {
    private static Logger logger = LoggerFactory.getLogger(LogicGates.class);

    public static LogicGates fromString(String input) {
        var midPoint = input.indexOf("\n\n");
        var values = fixedValues(input.substring(0, midPoint));
        var gates = gateMap(input.substring(midPoint + 2, input.length()));
        return new LogicGates(values, gates);
    }

    public List<List<String>> swappedVariants(int pairCount) {
        // find all combinations that can be made by swapping pairCount gates' outputs.

        var pairs = new ArrayList<String[]>();
        var keys = new ArrayList<String>(gates.keySet());
        keys.sort(String::compareTo);

        for (var i = 0; i < keys.size() - 1; i++) {
            for (var j = i + 1; j < keys.size(); j++) {
                pairs.add(new String[] { keys.get(i), keys.get(j) });
            }
        }

        var allPairs = new ArrayList<List<String>>();
        for (var i=0; i < pairCount; i++) {
            if (i == 0) {
                // we do not yet have any pairs
                for (var pair: pairs) {
                    allPairs.add(List.of(pair[0], pair[1]));
                }
                continue;
            }

            // add each pair to each existing list of pairs where there are no gates in common
            var newAllPairs = new ArrayList<List<String>>();
            for (var existingPair: allPairs) {
                for (var pair: pairs) {
                    if (!existingPair.contains(pair[0]) && !existingPair.contains(pair[1])) {
                        var newListOfPairs = new ArrayList<String>(existingPair);
                        newListOfPairs.add(pair[0]);
                        newListOfPairs.add(pair[1]);
                    }
                }
            }
            allPairs = newAllPairs;
        }
        return allPairs;
    }

    public LogicGates swappedVariant(List<String> swaps) {
        var newGates = new HashMap<String, Gate>(gates);

        // swap 0 and 1
        var a = swaps.get(0);
        var b = swaps.get(1);
        var aGate = newGates.get(a);
        var bGate = newGates.get(b);

        newGates.put(a, bGate); // we don't change the .output() attribute of the gate as it isn't used, and it
                                // helps to see that this entry was swapped.
        newGates.put(b, aGate);

        // swap 2 and 3
        var c = swaps.get(2);
        var d = swaps.get(3);
        var cGate = newGates.get(c);
        var dGate = newGates.get(d);

        newGates.put(c, dGate); // we don't change the .output() attribute of the gate as it isn't used, and it
                                // helps to see that this entry was swapped.
        newGates.put(d, cGate);

        return new LogicGates(new HashMap<String, Integer>(values), newGates);
    }

    Integer evaluate(String name) {
        /*
         * logger.info("Evaluating: " + name);
         * if (values.containsKey(name)) {
         * logger.info("hard-wired: " + values.get(name));
         * return values.get(name);
         * }
         * var gate = gates.get(name);
         * logger.info("Computing: " + gate);
         * var left = evaluate(gate.left());
         * var right = evaluate(gate.right());
         * var result = gate.operation().apply(left, right);
         * logger.info("Result: " + gate + " = " + result);
         * return result;
         */

        return values.computeIfAbsent(name, value -> {
            var gate = gates.get(name);
            logger.info("Computing: " + gate);
            var left = evaluate(gate.left());
            var right = evaluate(gate.right());
            return gate.operation().apply(left, right);
        });
    }

    private static Map<String, Gate> gateMap(String input) {
        return input.lines().map(Gate::fromString).collect(Collectors.toMap(
                Gate::output,
                Function.identity()));
    }

    /*
     * takes line of form: x00: 1
     */
    private static Map<String, Integer> fixedValues(String input) {
        var unused = input.lines().map(line -> line.split(": ")).collect(Collectors.toMap(
                parts -> parts[0],
                parts -> parts[1]));
        var values = input.lines().map(line -> line.split(": ")).collect(Collectors.toMap(
                parts -> parts[0],
                parts -> Integer.valueOf(parts[1])));
        return new ConcurrentSkipListMap<String, Integer>(values);
    }

    public Long output() {
        return output("z");
    }

    public Long output(String prefix) {
        // find z outputs z00 +
        List<String> prefixGates = Stream.concat(
                gates.keySet().stream(),
                values.keySet().stream()).filter(key -> key.startsWith(prefix)).sorted(Comparator.comparingInt(
                        name -> Integer.valueOf(name.substring(1))))
                .toList();
        var values = prefixGates.stream().map(this::evaluate).toList();
        // do we assume that all values z00... are present?
        // values[0] is least significant.
        long output = 0L;
        for (var bit : values.reversed()) {
            output = (output << 1) + bit;
        }
        return output;
    }

    public boolean isValid() {
        var x = output("x");
        var y = output("y");
        var z = output("z");

        return (x + y) == z;
    }

    public List<String> findValidVariants() {
        for (var swaps : swappedVariants(4)) {
            var variant = swappedVariant(swaps);
            logger.info("Swap: " + swaps);
            logger.info("\n" + variant);
            if (variant.isValid()) {
                return swaps;
            }
        }
        throw new IllegalArgumentException("No valid swaps");
    }
}
