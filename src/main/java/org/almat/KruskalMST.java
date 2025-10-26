package org.almat;

import java.util.*;

public class KruskalMST {
    public static MSTResult run(Graph g) {
        long start = System.nanoTime();
        OperationCounter c = new OperationCounter();
        List<Edge> edges = new ArrayList<>(g.getEdges());
        edges.sort((a,b)->{c.sortComparisons++;return Integer.compare(a.weight,b.weight);});
        DisjointSetUnion dsu = new DisjointSetUnion(g.getVertexCount(), c);

        List<Edge> mstEdges = new ArrayList<>();
        for (Edge e : edges) {
            c.comparisons++;
            if (dsu.union(e.u, e.v)) mstEdges.add(e);
            if (mstEdges.size() == g.getVertexCount()-1) break;
        }

        long totalCost = mstEdges.stream().mapToLong(x->x.weight).sum();
        double timeMs = (System.nanoTime()-start)/1000000.0;
        return new MSTResult(mstEdges, totalCost, c.total(), timeMs);
    }
}

