package org.nihilistic.aoc.day7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberTrees {
    static Logger logger = LoggerFactory.getLogger(NumberTrees.class);

    public static boolean hasValidSolution(long target, long[] numbers, VariantBuilder buildVariants) {
        logger.debug("Finding valid solution for: {}", numbers);
        var variants = buildVariants.buildVariants(numbers);
        logger.debug("{} variants", variants.length);

        for (var variant : variants) {
            logger.debug("testing {} = {}", target, variant);
            var result = variant.eval();
            if (result == target) {
                logger.debug("matches: " + result);
                return true;
            }
            logger.debug("does not match: " + result);
        }
        logger.debug("no match found");
        return false;
    }
}
/*
 * 81 40 27 -
 * ((81*40) + 27) = 3276
 * ((81+40) + 27) = 148
 * ((81*40) * 27) = 87480
 * ((81+40) * 27) = 3276
 */

/*
 * eg., ((1*2) + 3)
 * 
 * = new NumberTree(Operator.PLUS,
 * new NumberTree(Operator.TIMES, 1, new NumberTree(Operator.PLUS, 2, 0))
 * )
 */