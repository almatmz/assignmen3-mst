

import org.almat.Graph;
import org.almat.KruskalMST;
import org.almat.MSTResult;
import org.almat.PrimMST;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTest {

    private Graph generateRandomGraph(int vertices, int density) {
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < vertices; i++) nodes.add("V" + i);
        Graph g = new Graph(nodes);

        Random rnd = new Random(42);
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (rnd.nextInt(100) < density) {
                    g.addEdge("V" + i, "V" + j, rnd.nextInt(50) + 1);
                }
            }
        }
        return g;
    }

    @Test
    void testExecutionTimesNonNegative() {
        Graph g = generateRandomGraph(10, 50);
        MSTResult prim = PrimMST.run(g);
        MSTResult kruskal = KruskalMST.run(g);

        assertTrue(prim.timeMs >= 0, "Prim time must be non-negative");
        assertTrue(kruskal.timeMs >= 0, "Kruskal time must be non-negative");
    }

    @Test
    void testSameMSTCostOnReRun() {
        Graph g = generateRandomGraph(12, 60);
        MSTResult r1 = KruskalMST.run(g);
        MSTResult r2 = KruskalMST.run(g);
        assertEquals(r1.totalCost, r2.totalCost, "Kruskal result must be deterministic");
    }

    @Test
    void testPrimAndKruskalConsistency() {
        Graph g = generateRandomGraph(15, 40);
        MSTResult prim = PrimMST.run(g);
        MSTResult kruskal = KruskalMST.run(g);
        assertEquals(prim.totalCost, kruskal.totalCost,
                "Prim and Kruskal should produce identical MST cost");
    }
}
