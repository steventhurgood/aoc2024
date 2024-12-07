package org.nihilistic.aoc.day7;

import java.util.Objects;

import javax.annotation.Nullable;

public record NumberTree(Operator operator,@Nullable  NumberTree left, long right) {
    public enum Operator{
        PLUS {
           @Override
           public long eval(NumberTree left, long right) {
              if (Objects.isNull(left)) {
                throw new IllegalArgumentException("bad tree: " + toString());
              }
              return left.eval() + right;
           }

        @Override
        public String toString(NumberTree left, long right) {
            return "(" + left.toString() + " + " + right + ")";
        } 
        },
        TIMES {

            @Override
            public long eval(NumberTree left, long right) {
                return left.eval() * right;
            }
            @Override
            public String toString(NumberTree left, long right) {
                return "(" + left.toString() + " * " + right + ")";
            } 
        },
        CONCAT {

            @Override
            public long eval(NumberTree left, long right) {
                // 123 || 843 = 123842
                long counter = right;
                long leftnum = left.eval();
                while (counter > 0) {
                    counter /= 10;
                    leftnum *= 10;
                }
                return leftnum + right;
            }

            @Override
            public String toString(NumberTree left, long right) {
                return "(" + left.toString() + " || " + right + ")";
            }

        },
        NONE {

            @Override
            public long eval(NumberTree left, long right) {
                return right;
            }
            @Override
            public String toString(NumberTree left, long right) {
                return "" + right;
            } 
        };
        public abstract long eval(NumberTree left, long right);
        public abstract String toString(NumberTree left, long right);
    };

    public long eval() {
        return operator.eval(left, right);
    }

    @Override
    public String toString() {
        return operator.toString(left, right);
    }
}
