package org.almat;

public class Edge implements Comparable<Edge> {
    public final int u, v;
    public final int weight;
    public final String from, to;

    public Edge(int u, int v, int weight, String from, String to) {
        this.u = u; this.v = v; this.weight = weight;
        this.from = from; this.to = to;
    }

    public int other(int node) {
        return (node == u) ? v : u;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }
}

