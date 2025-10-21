

import org.almat.Graph;
import org.almat.KruskalMST;
import org.almat.MSTResult;
import org.almat.PrimMST;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PrimKruskalTest {

    private Graph makeSampleGraph() {
        List<String> nodes = List.of("A","B","C","D");
        Graph g = new Graph(nodes);
        g.addEdge("A","B",1);
        g.addEdge("A","C",4);
        g.addEdge("B","C",2);
        g.addEdge("C","D",3);
        g.addEdge("B","D",5);
        return g;
    }

    @Test
    void testSameTotalCost() {
        Graph g = makeSampleGraph();
        MSTResult prim = PrimMST.run(g);
        MSTResult kruskal = KruskalMST.run(g);

        assertEquals(prim.totalCost, kruskal.totalCost,
                "Prim and Kruskal MST costs must match");
        assertEquals(g.getVertexCount() - 1, prim.edges.size());
    }

    @Test
    void testEdgesCountAndNonEmpty() {
        Graph g = makeSampleGraph();
        MSTResult prim = PrimMST.run(g);
        assertEquals(3, prim.edges.size());
        assertTrue(prim.totalCost > 0);
    }

    @Test
    void testDisconnectedGraphHandled() {
        List<String> nodes = List.of("A","B","C");
        Graph g = new Graph(nodes);
        g.addEdge("A","B",5);
        MSTResult prim = PrimMST.run(g);
        MSTResult kruskal = KruskalMST.run(g);

        assertTrue(prim.edges.size() < g.getVertexCount() - 1);
        assertTrue(kruskal.edges.size() < g.getVertexCount() - 1);
    }
}
