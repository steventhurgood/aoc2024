package org.nihilistic.aoc.day15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Warehouse(WarehouseMap warehouseMap, Instructions instructions) {
    private static Logger logger = LoggerFactory.getLogger(WarehouseMap.class);

    public static Warehouse fromString(String input) {
        var splitPoint = input.indexOf("\n\n", 0);
        var warehouseMap = WarehouseMap.fromString(input.substring(0, splitPoint));
        var instructions = Instructions.fromString(input.substring(splitPoint+2, input.length()));
        return new Warehouse(warehouseMap, instructions);
    }

    public void simulate() {
        logger.info("\n" + warehouseMap.toString());
        for (var instruction : instructions.instructions()) {
            logger.debug("applying: " + instruction.toString());
            warehouseMap.apply(instruction);
            logger.debug("\n" + warehouseMap.toString());
        }
    }

    public int gps() {
        return warehouseMap.gps();
    }

    public String toString() {
        return warehouseMap.toString();
    }
   
    public BigWarehouse embiggen() {
        var bigMap = warehouseMap.embiggen();
        return new BigWarehouse(bigMap, instructions);
    }
}
