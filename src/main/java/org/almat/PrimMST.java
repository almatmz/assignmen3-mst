package org.almat;

import java.util.*;

public class PrimMST {
    public static MSTResult run(Graph g) {
        long start = System.nanoTime();
        OperationCounter c = new OperationCounter();
        int V = g.getVertexCount();

        boolean[] used = new boolean[V];
        int[] dist = new int[V];
        int[] parent = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        dist[0] = 0;
        pq.add(new int[]{0, 0});

        List<Edge> mstEdges = new ArrayList<>();
        while (!pq.isEmpty() && mstEdges.size() < V - 1) {
            int[] top = pq.poll();
            c.pqOps++;
            int w = top[0], u = top[1];
            if (used[u]) continue;
            used[u] = true;

            if (parent[u] != -1) {
                int pu = parent[u];
                String from = g.getNodeName(pu);
                String to = g.getNodeName(u);
                mstEdges.add(new Edge(pu, u, w, from, to));
            }

            for (Edge e : g.getAdjList().get(u)) {
                int v = e.other(u);
                c.comparisons++;
                if (!used[v] && e.weight < dist[v]) {
                    dist[v] = e.weight;
                    parent[v] = u;
                    pq.add(new int[]{e.weight, v});
                    c.relaxations++;
                }
            }
        }

        long totalCost = mstEdges.stream().mapToLong(e -> e.weight).sum();
        double timeMs = (System.nanoTime() - start) / 1000000.0;
        return new MSTResult(mstEdges, totalCost, c.total(), timeMs);
    }
}
