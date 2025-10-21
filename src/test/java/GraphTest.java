
import org.almat.Edge;
import org.almat.Graph;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    void testAddEdgesAndCounts() {
        List<String> nodes = List.of("A", "B", "C");
        Graph g = new Graph(nodes);
        g.addEdge("A", "B", 5);
        g.addEdge("B", "C", 3);

        assertEquals(3, g.getVertexCount());
        assertEquals(2, g.getEdgeCount());
        assertEquals(2, g.getAdjList().get(1).size());
    }

    @Test
    void testAdjacencySymmetry() {
        List<String> nodes = List.of("A", "B");
        Graph g = new Graph(nodes);
        g.addEdge("A", "B", 7);

        List<Edge> adjA = g.getAdjList().get(0);
        List<Edge> adjB = g.getAdjList().get(1);

        assertEquals(1, adjA.size());
        assertEquals(1, adjB.size());
        assertEquals(adjA.get(0), adjB.get(0));
    }

    @Test
    void testNodeMapping() {
        List<String> nodes = List.of("X", "Y");
        Graph g = new Graph(nodes);
        assertEquals("X", g.getNodeName(0));
        assertEquals("Y", g.getNodeName(1));
    }
}
