package org.almat;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Graph> graphs = JsonIO.readGraphs("input/input.json");
        JsonArray results = new JsonArray();
        int id = 1;

        for (Graph g : graphs) {
            MSTResult prim = PrimMST.run(g);
            MSTResult kruskal = KruskalMST.run(g);

            JsonObject entry = new JsonObject();
            entry.addProperty("graph_id", id++);
            JsonObject stats = new JsonObject();
            stats.addProperty("vertices", g.getVertexCount());
            stats.addProperty("edges", g.getEdgeCount());
            entry.add("input_stats", stats);

            entry.add("prim", makeResult(prim));
            entry.add("kruskal", makeResult(kruskal));
            results.add(entry);
        }

        JsonObject root = new JsonObject();
        root.add("results", results);
        try (FileWriter fw = new FileWriter("output/output.json")) {
            new GsonBuilder().setPrettyPrinting().create().toJson(root, fw);
        }
        System.out.println("Results written to output/output.json");
    }

    private static JsonObject makeResult(MSTResult r) {
        JsonObject o = new JsonObject();
        JsonArray edges = new JsonArray();
        for (Edge e : r.edges) {
            JsonObject eo = new JsonObject();
            eo.addProperty("from", e.from);
            eo.addProperty("to", e.to);
            eo.addProperty("weight", e.weight);
            edges.add(eo);
        }
        o.add("mst_edges", edges);
        o.addProperty("total_cost", r.totalCost);
        o.addProperty("operations_count", r.operations);
        o.addProperty("execution_time_ms", r.timeMs);
        return o;
    }
}
