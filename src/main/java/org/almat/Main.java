package org.almat;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class Main {
    private static final String INPUT_DIR = "input/";
    private static final String OUTPUT_DIR = "output/";

    public static void main(String[] args) throws Exception {
        new File(OUTPUT_DIR).mkdirs();

        String[] datasets = {"small_graphs.json", "medium_graphs.json", "large_graphs.json"};
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (String dataset : datasets) {
            String inputPath = INPUT_DIR + dataset;
            String outputPath = OUTPUT_DIR + "output_" + dataset;

            System.out.println("Processing: " + inputPath);

            List<Graph> graphs = JsonIO.readGraphs(inputPath);
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

                System.out.printf(
                        "Graph %d | Vertices: %d | Edges: %d | Prim Cost: %d | Kruskal Cost: %d%n",
                        id - 1, g.getVertexCount(), g.getEdgeCount(),
                        prim.totalCost, kruskal.totalCost
                );
            }

            JsonObject root = new JsonObject();
            root.add("results", results);

            try (FileWriter fw = new FileWriter(outputPath)) {
                gson.toJson(root, fw);
            }

            System.out.println("✅ Results written to " + outputPath + "\n");
        }

        generateSummaryCSV();
        System.out.println(" All datasets processed and summary created successfully!");
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

    private static void generateSummaryCSV() throws IOException {
        String[] datasets = {"small_graphs.json", "medium_graphs.json", "large_graphs.json"};

        try (FileWriter csv = new FileWriter(OUTPUT_DIR + "summary.csv")) {
            csv.write("Dataset,Graph ID,Vertices,Edges,Algorithm,Total Cost,Operations Count,Execution Time (ms)\n");

            for (String dataset : datasets) {
                String datasetName = dataset.replace("_graphs.json", "");
                String outputFile = OUTPUT_DIR + "output_" + dataset;
                JsonObject root = JsonIO.readJson(outputFile);
                JsonArray results = root.getAsJsonArray("results");

                for (JsonElement e : results) {
                    JsonObject obj = e.getAsJsonObject();
                    int id = obj.get("graph_id").getAsInt();
                    JsonObject stats = obj.getAsJsonObject("input_stats");
                    int v = stats.get("vertices").getAsInt();
                    int ed = stats.get("edges").getAsInt();

                    for (String algo : new String[]{"prim", "kruskal"}) {
                        JsonObject res = obj.getAsJsonObject(algo);
                        csv.write(String.format(
                                "%s,%d,%d,%d,%s,%.0f,%d,%.4f\n",
                                datasetName,
                                id,
                                v,
                                ed,
                                capitalize(algo),
                                res.get("total_cost").getAsDouble(),
                                res.get("operations_count").getAsInt(),
                                res.get("execution_time_ms").getAsDouble()
                        ));
                    }
                }
            }
        }

        System.out.println(" Summary written to " + OUTPUT_DIR + "summary.csv");
    }

    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
