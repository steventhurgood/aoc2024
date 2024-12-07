package org.nihilistic.aoc.day7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlusTimesConcatVariantBuilder implements VariantBuilder {
    private static Logger logger = LoggerFactory.getLogger(PlusTimesConcatVariantBuilder.class);

    @Override
    public NumberTree[] buildVariants(long[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("array size == 0: " + numbers.length);
        }
        NumberTree partialTrees[] = {
                new NumberTree(NumberTree.Operator.NONE, null, numbers[0])
        };

        for (var i = 1; i < numbers.length; i++) {
            NumberTree newPartialTrees[] = new NumberTree[partialTrees.length * 3];
            for (var j = 0; j < partialTrees.length; j++) {
                newPartialTrees[j * 3] = new NumberTree(NumberTree.Operator.PLUS, partialTrees[j], numbers[i]);
                newPartialTrees[j * 3 + 1] = new NumberTree(NumberTree.Operator.TIMES, partialTrees[j], numbers[i]);
                newPartialTrees[j * 3 + 2] = new NumberTree(NumberTree.Operator.CONCAT, partialTrees[j], numbers[i]);
            }
            partialTrees = newPartialTrees;
        }
        return partialTrees;
    }
}
