package org.nihilistic.aoc.day3;

public class Machine {
    private int total = 0;

    private boolean executing = true;

    public void doMul(int a, int b) {
        if (executing) {
            total += (a*b);
        }
    }

    public void stopExecuting() {
        executing = false;
    }

    public void startExecuting() {
        executing = true;
    }

    public int getTotal() {
        return total;
    }
}
