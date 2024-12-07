package org.nihilistic.aoc.day7;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NumberTreeTest {
    @Test
    public void testNumberTree_whenEvalPlusTimes_givesRightNumber() {

        // arrange
        long numbers[] = {276737138893L , 2 };
        VariantBuilder variantBuilder = new PlusTimesVariantBuilder();
        NumberTree variants[] = variantBuilder.buildVariants(numbers);


        // act
        List<Long> actual = Arrays.stream(variants).map(NumberTree::eval).collect(Collectors.toList());

        // assert
        assertThat(actual).containsExactly(276737138893L*2, 276737138893L+2);
    }

    @Test
    public void testNumberTree_whenEvalPlusTimesConcat_givesRightNumber() {

        // arrange
        long numbers[] = {276737138893L , 2 };
        VariantBuilder variantBuilder = new PlusTimesConcatVariantBuilder();
        NumberTree variants[] = variantBuilder.buildVariants(numbers);

        // act
        List<Long> actual = Arrays.stream(variants).map(NumberTree::eval).collect(Collectors.toList());

        // assert
        assertThat(actual).containsExactly(276737138893L*2, 276737138893L+2, 2767371388932L);
    }
}
