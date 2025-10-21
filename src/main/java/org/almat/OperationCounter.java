package org.almat;

public class OperationCounter {
    public long comparisons = 0;
    public long pqOps = 0;
    public long relaxations = 0;
    public long findOps = 0;
    public long unionOps = 0;
    public long sortComparisons = 0;

    public long total() {
        return comparisons + pqOps + relaxations + findOps + unionOps + sortComparisons;
    }
}
