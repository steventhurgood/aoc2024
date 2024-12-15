package org.nihilistic.aoc.day15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record BigWarehouse (BigWarehouseMap warehouseMap, Instructions instructions) {
    private static Logger logger = LoggerFactory.getLogger(BigWarehouse.class);
  
    @Override
    public String toString() {
        return warehouseMap.toString();
    }

    public void simulate() {
        logger.info("\n" + warehouseMap.toString());
        for (Instruction instruction : instructions.instructions()) {
            logger.info("Applying instruction: " + instruction);
            warehouseMap.apply(instruction);
            logger.info("\n" + warehouseMap.toString());
        }
    }

    public int gps() {
        return warehouseMap.gps();
    }
}