package org.almat;

import java.util.*;

public class Graph {
    private final List<String> nodes;
    private final List<Edge> edges;
    private final Map<String, Integer> nameToIndex;
    private final List<List<Edge>> adjList;

    public Graph(List<String> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>();
        this.nameToIndex = new HashMap<>();
        this.adjList = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            nameToIndex.put(nodes.get(i), i);
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(String from, String to, int weight) {
        int u = nameToIndex.get(from);
        int v = nameToIndex.get(to);
        Edge e = new Edge(u, v, weight, from, to);
        edges.add(e);
        adjList.get(u).add(e);
        adjList.get(v).add(e);
    }

    public int getVertexCount() {
        return nodes.size();
    }
    public int getEdgeCount() {
        return edges.size();
    }
    public List<Edge> getEdges() {
        return edges;
    }
    public List<List<Edge>> getAdjList() {
        return adjList;
    }
    public String getNodeName(int index) {
        return nodes.get(index);
    }
}

