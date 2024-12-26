package org.nihilistic.aoc.day24;

public enum GateOperation {
    AND {
        @Override
        Integer apply(Integer left, Integer right) {
            return left & right;
        }
    },
    OR {
        @Override
        Integer apply(Integer left, Integer right) {
            return left | right;
        }
    },
    XOR {
        @Override
        Integer apply(Integer left, Integer right) {
            return left ^ right;
        }
    };

    abstract Integer apply(Integer left, Integer right);
}
