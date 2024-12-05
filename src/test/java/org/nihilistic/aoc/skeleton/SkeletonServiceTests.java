package org.nihilistic.aoc.skeleton;

import org.junit.Assert;
import org.junit.Test;

public class SkeletonServiceTests {
    @Test
    public void testSkeletonService_whenInitialized_completes() {
        // arrange

        SkeletonService skeletonService = new SkeletonServiceImpl();

        // assert

        Assert.assertNotNull(skeletonService);

    }
}
