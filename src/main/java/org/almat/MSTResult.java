package org.almat;

import java.util.List;

public class MSTResult {
    public final List<Edge> edges;
    public final long totalCost;
    public final long operations;
    public final double timeMs;

    public MSTResult(List<Edge> e, long cost, long ops, double t) {
        this.edges = e;
        this.totalCost = cost;
        this.operations = ops;
        this.timeMs = t;
    }
}

