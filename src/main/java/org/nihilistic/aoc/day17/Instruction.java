package org.nihilistic.aoc.day17;

public enum Instruction {
    ADV, BXL, BST, JNZ, BXC, OUT, BDV, CDV;

    public static Instruction fromOpcode(int opCode) {
        return values()[opCode];
    }
}
