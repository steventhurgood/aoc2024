package org.nihilistic.aoc.day7;

public class PlusTimesVariantBuilder implements VariantBuilder {
    @Override
    public NumberTree[] buildVariants(long[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("array size == 0: " + numbers.length);
        }
        NumberTree partialTrees[] = {
                new NumberTree(NumberTree.Operator.NONE, null, numbers[0])
        };

        for (var i = 1; i < numbers.length; i++) {
            NumberTree newPartialTrees[] = new NumberTree[partialTrees.length * 2];
            for (var j = 0; j < partialTrees.length; j++) {
                newPartialTrees[j * 2] = new NumberTree(NumberTree.Operator.PLUS, partialTrees[j], numbers[i]);
                newPartialTrees[j * 2 + 1] = new NumberTree(NumberTree.Operator.TIMES, partialTrees[j], numbers[i]);
            }
            partialTrees = newPartialTrees;
        }
        return partialTrees;
    }
}