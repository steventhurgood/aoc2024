package org.nihilistic.aoc.day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Computer {
    private long registers[];
    private int instructionPointer;
    private List<Integer> program;
    private List<Integer> output;

    private Computer() {
        instructionPointer = 0;
        registers = new long[3];
        output = new ArrayList<Integer>();
    }

    public static Computer fromString(String input) {
        var computer = new Computer();
        input.lines().forEach(line -> {
            var parts = line.split(": ");
            Long value;
            switch (parts[0]) {
                case "Register A":
                    value = Long.valueOf(parts[1]);
                    computer.registers[0] = value;
                    break;
                case "Register B":
                    value = Long.valueOf(parts[1]);
                    computer.registers[1] = value;
                    break;
                case "Register C":
                    value = Long.valueOf(parts[1]);
                    computer.registers[2] = value;
                    break;
                case "Program":
                    var program = Arrays.stream(parts[1].split(",")).mapToInt(Integer::valueOf).boxed().toList();
                    computer.program = program;
                    break;
            }
        });
        return computer;
    }

    private long combo(int value) {
        if (value >= 4 && value < 7) {
            return registers[value - 4];
        }
        return (long) value;
    }

    public boolean execute() {
        var current = Instruction.fromOpcode(program.get(instructionPointer));

        long numerator;
        long value;
        int literal = program.get(instructionPointer + 1);

        switch (current) {

            case ADV:
                numerator = registers[0];
                value = combo(literal);
                registers[0] = numerator >> value;
                instructionPointer += 2;
                break;
            case BXL:
                value = registers[1] ^ literal;
                registers[1] = value;
                instructionPointer += 2;
                break;
            case BST:
                value = combo(literal) & 0b111;
                registers[1] = value;
                instructionPointer += 2;
                break;
            case JNZ:
                if (registers[0] != 0) {
                    instructionPointer = literal;
                } else {
                    instructionPointer += 2;
                }
                break;
            case BXC:
                value = registers[1] ^ registers[2];
                registers[1] = value;
                instructionPointer += 2;
                break;
            case OUT:
                value = combo(literal) & 0b111;
                output.add((int) value);
                instructionPointer += 2;
                break;
            case BDV:
                numerator = registers[0];
                value = combo(literal);
                registers[1] = numerator >> value;
                instructionPointer += 2;
                break;
            case CDV:
                numerator = registers[0];
                value = combo(literal);
                registers[2] = numerator >> value;
                instructionPointer += 2;
                break;
            default:
                break;
        }
        return (instructionPointer < program.size());
    }

    public List<Integer> run() {
        for (; execute();) {
        }
        return output;
    }

    public String comboString(int literal) {
        return switch(literal) {
            case 4 -> "register A";
            case 5 -> "register B";
            case 6 -> "register C";
            default -> String.valueOf(literal);
        };
    }

    @Override
    public String toString() {
        var out = new StringBuilder();
        out.append("Register A: " + registers[0] + "\n");
        out.append("Register B: " + registers[1] + "\n");
        out.append("Register C: " + registers[2] + "\n");
        out.append("\n");
        for (var i = 0; i < program.size(); i += 2) {
            var current = Instruction.fromOpcode(program.get(i));
            var literal = program.get(i + 1);
            switch (current) {

                case ADV:
                    out.append("[%d] ADV: register A <- register A >> %s\n".formatted(i/2, comboString(literal)));
                    break;
                case BXL:
                    out.append("[%d] BXL: register B <- register B XOR %d\n".formatted(i/2, literal));
                    break;
                case BST:
                    out.append("[%d] BST: register B <- %s & 0b111\n".formatted(i/2, comboString(literal)));
                    break;
                case JNZ:
                    out.append("[%d] JNZ: register A %d\n".formatted(i/2, literal));
                case BXC:
                    out.append("[%d] BXC: register B <- register B ^ register C\n".formatted(i/2));
                    break;
                case OUT:
                    out.append("[%d] OUT: output register A\n".formatted(i/2));
                    break;
                case BDV:
                    out.append("[%d] BDV: register B <- register A >> %s\n".formatted(i/2, comboString(literal)));
                    break;
                case CDV:
                    out.append("[%d] CDV: register C <- register A >> %s\n".formatted(i/2, comboString(literal)));
                    break;
                default:
                    break;
            }

        }
        out.append("\n");
        return out.toString();
    }
}
