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

    Optional<Integer> nextFreeAllocationOfSize(List<DiskAllocation> allocations, int size, int maxIndex) {
        int i = 0;
        while (true) {
            if (i >= allocations.size() || i >= maxIndex) {
                return Optional.empty();
            }
            var allocation = allocations.get(i);
            if (allocation.free() && allocation.size() >= size) {
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

    DiskMap defragWholeFile() {
        var newAllocations = new ArrayList<DiskAllocation>(allocations);
        // 0 1 2 3 45 6 78 9A BC DE F16 17
        // V V V V VV V VV VV VV VV VV V
        // 00...111...2...333.44.5555.6666.777.888899
        // ^ left ............................ right ^
        int toReallocateIndex = newAllocations.size() - 1;
        while (true) {
            // defrag from the right, so take the right-most element that is not empty.
            if (toReallocateIndex < 0) {
                return new DiskMap(newAllocations);
            }
            var toReallocate = newAllocations.get(toReallocateIndex);
            if (toReallocate.free()) {
                toReallocateIndex--;
                continue;
            }
            var blocksToAllocate = toReallocate.size();
            // find a space for it
            var findNextFreeSpace = nextFreeAllocationOfSize(newAllocations, blocksToAllocate, toReallocateIndex);
            if (findNextFreeSpace.isEmpty()) {
                // cannot find a space for this. Give up and move to the next index.
                toReallocateIndex--;
                continue;
            }
            var nextFreeIndex = findNextFreeSpace.get();
            var nextFreeSpace = newAllocations.get(nextFreeIndex);
            // we have a big enough space.
            if (nextFreeSpace.size() == blocksToAllocate) {
                newAllocations.set(nextFreeIndex, nextFreeSpace.fill(toReallocate.fileId()));
            }
            if (nextFreeSpace.size() > blocksToAllocate) {
                newAllocations.set(nextFreeIndex, nextFreeSpace.shrinkAndFill(blocksToAllocate, toReallocate.fileId()));
                newAllocations.add(nextFreeIndex + 1, nextFreeSpace.spaceAfterFilling(blocksToAllocate));
                // because we added a new item, the index of the item we're reallocating has
                // increased.
                toReallocateIndex++;
            }
            // because we have successfully removed the allocation, we need to mark it as
            // free
            newAllocations.set(toReallocateIndex, toReallocate.makeFree());
            // we have successfully moved toReallocateIndex;
            toReallocateIndex--;
            logger.info("allocations size: {}", newAllocations.size());
        }
    }

    public long checksum() {
        int offset = 0;
        long checksum = 0;
        for (var allocation : allocations) {
            if (allocation.free()) {
                offset += allocation.size();
                continue;
            }
            int n = allocation.fileId();
            for (var i = 0; i < allocation.size(); i++) {
                checksum += offset * n;
                offset++;
            }
        }
        return checksum;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var allocation : allocations) {
            String n = String.valueOf(allocation.fileId());
            if (allocation.free()) {
                n = ".";
            }
            for (var i = 0; i < allocation.size(); i++) {
                builder.append(n);
            }
        }
        return builder.toString();
    }
}
