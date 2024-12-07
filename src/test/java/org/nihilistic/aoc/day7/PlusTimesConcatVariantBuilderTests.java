package org.nihilistic.aoc.day7;

import static com.google.common.truth.Truth.assertThat;


import org.junit.Test;

public class PlusTimesConcatVariantBuilderTests {
    @Test
    public void testPlusTimesVariantBuilder_whenBuildVariants_works() {
        // arrange

        long numbers[] = { 1, 2 };
        NumberTree expected[] = {
                new NumberTree(NumberTree.Operator.PLUS, new NumberTree(NumberTree.Operator.NONE, null, 1), 2),
                new NumberTree(NumberTree.Operator.TIMES, new NumberTree(NumberTree.Operator.NONE, null, 1), 2),
                new NumberTree(NumberTree.Operator.CONCAT, new NumberTree(NumberTree.Operator.NONE, null, 1), 2),
        };
        VariantBuilder variantBuilder = new PlusTimesVariantBuilder();

        // act
        NumberTree actual[] = variantBuilder.buildVariants(numbers);

        // assert

        assertThat(actual).isEqualTo(expected);

    }
}
