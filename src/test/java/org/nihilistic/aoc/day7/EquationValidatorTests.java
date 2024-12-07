package org.nihilistic.aoc.day7;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class EquationValidatorTests {
    @Test
    public void testEquationValidtor_whenisValidPlusTimes_returnsCorrectResult() {
        // arrange
        var line = "553474277786: 276737138893 2";
        VariantBuilder variantBuilder = new PlusTimesVariantBuilder();

        // act
        var actual = EquationValidator.isValid(line, variantBuilder);

        // assert

        assertThat(actual).isEqualTo(true);
    }
}
