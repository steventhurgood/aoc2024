package org.nihilistic.aoc.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record DiskMap(List<DiskAllocation> allocations) {
    private static Logger logger = LoggerFactory.getLogger(DiskMap.class);

    static DiskMap fromString(String input) {
        // var allocations = new LinkedList<DiskAllocation>();
        var allocations = new ArrayList<DiskAllocation>();
        int blockStart = 0;
        int fileId = 0;
        boolean free = false;
        for (int i = 0; i < input.length(); i++) {
            int size = Integer.valueOf(input.charAt(i)) - 48; // ascii('0') = 48
            if (free) {
                if (size > 0) {
                    allocations.add(new DiskAllocation(blockStart, blockStart + size, -1, true));
                }
            } else {
                allocations.add(new DiskAllocation(blockStart, blockStart + size, fileId, false));
                fileId++;
            }
            blockStart += size;
            free = !free;
        }
        return new DiskMap(allocations);
    }

    Optional<Integer> nextFreeAllocation(List<DiskAllocation> allocations) {
        int i = 0;
        while (true) {
            if (i >= allocations.size()) {
                return Optional.empty();
            }
            if (allocations.get(i).free()) {
                return Optional.of(i);
            }
            i++;
        }
    }

    DiskMap defragged() {
        // List<DiskAllocation> allocations = new
        // LinkedList<DiskAllocation>(allocations());
        List<DiskAllocation> allocations = new ArrayList<DiskAllocation>(allocations());

        // this is horrible to do with a linked list
        // todo: use a ListIterator
        int nextFree;
        int nextToDefrag;
        Optional<Integer> findNextFree;
        while (true) {
            nextToDefrag = allocations.size() - 1;
            var currentDefrag = allocations.get(nextToDefrag);
            findNextFree = nextFreeAllocation(allocations);

            if (findNextFree.isEmpty()) {
                // no more free allocations
                return new DiskMap(allocations);
            }
            nextFree = findNextFree.get();
            var currentFree = allocations.get(nextFree);
            var blocksToFill = currentFree.size();
            var blocksToAllocate = currentDefrag.size();

            if (blocksToAllocate == blocksToFill) {
                allocations.set(nextFree, currentFree.fill(currentDefrag.fileId()));
                allocations.removeLast();
            }
            if (blocksToAllocate > blocksToFill) {
                allocations.set(nextFree, currentFree.fill(currentDefrag.fileId()));
                allocations.set(nextToDefrag, currentDefrag.shrink(blocksToAllocate - blocksToFill));
            }
            if (blocksToAllocate < blocksToFill) {
                allocations.set(nextFree, currentFree.shrinkAndFill(currentDefrag.size(), currentDefrag.fileId()));
                allocations.add(nextFree + 1, currentFree.spaceAfterFilling(currentDefrag.size()));
                allocations.removeLast();
            }
            while (allocations.getLast().free()) {
                allocations.removeLast();
            }
            logger.info("allocations size: {}", allocations.size());
            // logger.info(new DiskMap(allocations).toString());
        }
    }

    DiskMap defragWholeFiles() {
        // List<DiskAllocation> allocations = new
        // LinkedList<DiskAllocation>(allocations());
        List<DiskAllocation> allocations = new ArrayList<DiskAllocation>(allocations());

        // this is horrible to do with a linked list
        // todo: use a ListIterator
        int nextFree;
        int nextToDefrag;
        Optional<Integer> findNextFree;
        while (true) {
            nextToDefrag = allocations.size() - 1;
            var currentDefrag = allocations.get(nextToDefrag);
            findNextFree = nextFreeAllocation(allocations);

            if (findNextFree.isEmpty()) {
                // no more free allocations
                return new DiskMap(allocations);
            }
            nextFree = findNextFree.get();
            var currentFree = allocations.get(nextFree);
            var blocksToFill = currentFree.size();
            var blocksToAllocate = currentDefrag.size();

            if (blocksToAllocate == blocksToFill) {
                allocations.set(nextFree, currentFree.fill(currentDefrag.fileId()));
                allocations.removeLast();
            }
            if (blocksToAllocate > blocksToFill) {
                allocations.set(nextFree, currentFree.fill(currentDefrag.fileId()));
                allocations.set(nextToDefrag, currentDefrag.shrink(blocksToAllocate - blocksToFill));
            }
            if (blocksToAllocate < blocksToFill) {
                allocations.set(nextFree, currentFree.shrinkAndFill(currentDefrag.size(), currentDefrag.fileId()));
                allocations.add(nextFree + 1, currentFree.spaceAfterFilling(currentDefrag.size()));
                allocations.removeLast();
            }
            while (allocations.getLast().free()) {
                allocations.removeLast();
            }
            logger.info("allocations size: {}", allocations.size());
            // logger.info(new DiskMap(allocations).toString());
        }
    }

}
