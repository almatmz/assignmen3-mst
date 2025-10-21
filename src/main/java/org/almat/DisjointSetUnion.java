package org.almat;

public class DisjointSetUnion {
    private int[] parent, rank;
    private OperationCounter counter;

    public DisjointSetUnion(int n, OperationCounter c) {
        parent = new int[n]; rank = new int[n];
        counter = c;
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        counter.findOps++;
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public boolean union(int a, int b) {
        counter.unionOps++;
        a = find(a); b = find(b);
        if (a == b) return false;
        if (rank[a] < rank[b]) parent[a] = b;
        else if (rank[b] < rank[a]) parent[b] = a;
        else { parent[b] = a; rank[a]++; }
        return true;
    }
}

