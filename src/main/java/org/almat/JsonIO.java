package org.almat;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class JsonIO {
    public static List<Graph> readGraphs(String path) throws IOException {
        List<Graph> graphs = new ArrayList<>();
        JsonObject root = JsonParser.parseReader(new FileReader(path)).getAsJsonObject();
        for (JsonElement gEl : root.getAsJsonArray("graphs")) {
            JsonObject gObj = gEl.getAsJsonObject();
            List<String> nodes = new ArrayList<>();
            for (JsonElement n : gObj.getAsJsonArray("nodes")) nodes.add(n.getAsString());
            Graph g = new Graph(nodes);
            for (JsonElement eEl : gObj.getAsJsonArray("edges")) {
                JsonObject e = eEl.getAsJsonObject();
                g.addEdge(e.get("from").getAsString(), e.get("to").getAsString(), e.get("weight").getAsInt());
            }
            graphs.add(g);
        }
        return graphs;
    }
}

