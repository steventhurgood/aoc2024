package org.nihilistic.aoc.day9;

import java.util.ArrayList;
import java.util.LinkedList;
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
        int i=0;
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
        // List<DiskAllocation> allocations = new LinkedList<DiskAllocation>(allocations());
        List<DiskAllocation> allocations = new ArrayList<DiskAllocation>(allocations());

        // this is horrible to do with a linked list
        // todo: use a ListIterator
        int nextFree;
        int nextToDefrag;
        Optional<Integer> findNextFree;
        while (true) {
            nextToDefrag = allocations.size()-1;
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
                allocations.add(nextFree+1, currentFree.spaceAfterFilling(currentDefrag.size()));
                allocations.removeLast();
            }
            while (allocations.getLast().free()) {
                allocations.removeLast();
            }
            logger.info("allocations size: {}", allocations.size());
            // logger.info(new DiskMap(allocations).toString());
        }
    }

    DiskMap defraggedWithIterator() {
        List<DiskAllocation> newAllocations = new LinkedList<DiskAllocation>();
        // this is horrible to do with a linked list
        // todo: use a ListIterator

        var spaceIterator = allocations.listIterator();
        var blockIterator = allocations.listIterator(allocations.size());
        DiskAllocation nextFree;
        DiskAllocation nextBlock;

        while (blockIterator.hasPrevious()) {
            nextBlock = blockIterator.previous();
            if (nextBlock.free()) {
                blockIterator.remove();
            } else {
                break;
            }
        }
        if (!blockIterator.hasPrevious()) {
            throw new IllegalArgumentException("no blocks to allocate: " + toString());
        }
        // blockIterator now points at the right-most block.

        while (true) {
            // find the next free space.
            while (spaceIterator.hasNext()) {
                nextFree = spaceIterator.next(); 
                if (nextFree.free()) {
                    break;
                }
            }
            if (spaceIterator.hasNext()) {
                // no more space
                return new DiskMap(newAllocations);
            }
            // nextFree now points to the leftmost free block
            // nextBlock now points to the rightmost block to defrag
            var blocksToFill = nextFree.size();
            var blocksToAllocate = nextBlock.size();

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
                allocations.add(nextFree+1, currentFree.spaceAfterFilling(currentDefrag.size()));
                allocations.removeLast();
            }
            while (allocations.getLast().free()) {

        }

        int nextFree;
        int nextToDefrag;
        Optional<Integer> findNextFree;
        while (true) {
            nextToDefrag = allocations.size()-1;
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
                allocations.add(nextFree+1, currentFree.spaceAfterFilling(currentDefrag.size()));
                allocations.removeLast();
            }
            while (allocations.getLast().free()) {
                allocations.removeLast();
            }
            logger.info("allocations size: {}", allocations.size());
            // logger.info(new DiskMap(allocations).toString());
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var allocation : allocations) {
            String n = String.valueOf(allocation.fileId());
            if (allocation.free()) {
                n = ".";
            }
            for (var i=0; i < allocation.size(); i++) {
                builder.append(n);
            }
        }
        return builder.toString();
    }

    public long checksum() {
        int offset = 0;
        long checksum = 0;
        for (var allocation : allocations) {
            int n = allocation.fileId();
            for (var i=0; i < allocation.size(); i++) {
                checksum += offset * n;
                offset++;
            }
        }
        return checksum;
    }
}
