package org.nihilistic.aoc.day9;

public record DiskAllocation(int start, int end, int fileId, boolean free) {

    public int size() {
        return end-start;
    }

    public DiskAllocation fill(int newFileId) {
        return new DiskAllocation(start, end, newFileId, false);
    }

    public DiskAllocation shrink(int newSize) {
        return new DiskAllocation(start, start+newSize, fileId, free);
    }

    public DiskAllocation shrinkAndFill(int newSize, int newFileId) {
        return new DiskAllocation(start, start+newSize, newFileId, false);
    }

    public DiskAllocation spaceAfterFilling(int size) {
        return new DiskAllocation(start+size, end, fileId, true);
    }
    
}
