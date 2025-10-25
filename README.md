
#  Assignment 3 — Optimization of a City Transportation Network (MST)

## 📘 Project Summary
This project implements and compares **Prim’s** and **Kruskal’s** algorithms to find the **Minimum Spanning Tree (MST)** of a city transportation network. It demonstrates the efficiency and correctness of both algorithms using small, medium, and large datasets.

### ✨ Features
- Custom `Graph`, `Edge`, and `DisjointSetUnion` classes
- Supports multiple input graphs (JSON format)
- Exports MSTs, operation counts, and runtimes (JSON + CSV)
- Includes **JUnit tests** for correctness
- Optional MST visualization diagrams and screenshots

---

## 📂 Project Structure
The project structure follows standard Maven conventions:

```

Assignment3-MST/

├── pom.xml
├── README.md
├── .gitignore
│
├── input/
│   ├── input.json
│   ├── small\_graphs.json
│   ├── medium\_graphs.json
│   └── large\_graphs.json
│
├── output/
│   ├── output\_small\_graphs.json
│   ├── output\_medium\_graphs.json
│   ├── output\_large\_graphs.json
│   └── summary.csv
│
├── docs/
│   ├── REPORT.pdf
│   └── diagrams/
│       └── 
│
└── src/
├── main/
│   └── java/
│       └── org/almat/
│           ├── Main.java
│           ├── Graph.java
│           ├── Edge.java
│           ├── PrimMST.java
│           ├── KruskalMST.java
│           ├── DisjointSetUnion.java
│           ├── OperationCounter.java
│           ├── JsonIO.java
│           └── MSTResult.java
│
└── test/
└── java/
└── org/almat/
├── GraphTest.java
├── PrimKruskalTest.java
└── PerformanceTest.java

````

---

## ⚙️ How to Run

### 🧩 Prerequisites
- **Java 11** or later
- **Apache Maven**

### 🏃 Build and Execute

```bash
# Compile the project
mvn compile

# Run the main program
mvn exec:java -Dexec.mainClass="org.almat.Main"

# Alternatively, run from packaged JAR
mvn package
java -cp target/Assignment3_MST-1.0-SNAPSHOT.jar org.almat.Main
````

### 🧪 Run Tests

```bash
mvn test
```

-----

## 📥 Input and Output Overview

### Input Format (JSON)

Each input graph is defined by its vertices and a list of weighted edges:

```json
{
  "vertices": ["A", "B", "C", "D"],
  "edges": [
    { "source": "A", "target": "B", "weight": 5 },
    { "source": "A", "target": "C", "weight": 3 },
    { "source": "B", "target": "C", "weight": 2 },
    { "source": "C", "target": "D", "weight": 4 }
  ]
}
```

### Output Example

Each MST result is stored in `output/*.json`, detailing the performance metrics for both algorithms:

```json
{
  "graph_id": 1,
  "input_stats": { "vertices": 4, "edges": 6 },
  "prim": {
    "total_cost": 14,
    "operations": 25,
    "execution_time_ms": 0.87
  },
  "kruskal": {
    "total_cost": 14,
    "operations": 18,
    "execution_time_ms": 0.42
  }
}
```

-----

## 🧮 Testing and Evaluation

### ✅ Correctness Tests

  - **MST Cost:** Identical cost for both Prim’s and Kruskal’s algorithms.
  - **Tree Properties:** Tree has exactly $V-1$ edges (where $V$ is the number of vertices).
  - **Acyclic:** The resulting tree contains no cycles.
  - **Connectivity:** Every vertex is reachable (for connected input graphs).
  - **Graceful Handling:** Disconnected graphs are handled without failure.

### ⚡ Performance Metrics

  - **Execution time (ms):** Measured wall-clock time for algorithm execution.
  - **Operation count:** Custom metric tracking elementary operations (comparisons, unions, etc.).
  - **Consistency:** Ensured consistent reproducibility across multiple runs.

-----

## 📊 Observations and Analysis

| Aspect | Prim’s Algorithm | Kruskal’s Algorithm |
| :--- | :--- | :--- |
| **Approach** | Greedy (grows the MST from a single vertex) | Greedy (sorts all edges first) |
| **Data Structures** | Priority Queue | Disjoint Set Union (DSU) |
| **Best For** | Dense graphs ($E \approx V^2$) | Sparse graphs ($E \approx V$) |
| **Time Complexity** | $O(E \log V)$ | $O(E \log E)$ |
| **Implementation** | Slightly more complex | Easier to implement |
| **Real-world Use** | Networks often represented with adjacency lists | Road maps, clustering algorithms |

-----
