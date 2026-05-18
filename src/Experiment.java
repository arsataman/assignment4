import java.util.Random;

public class Experiment {
    private int[] sizes = {10, 30, 100};
    private long[][] results = new long[3][2];

    public long[] runTraversals(Graph g) {
        int start = 0;

        long startBfs = System.nanoTime();
        g.bfs(start);
        long endBfs = System.nanoTime();

        long startDfs = System.nanoTime();
        g.dfs(start);
        long endDfs = System.nanoTime();

        long bfsTime = endBfs - startBfs;
        long dfsTime = endDfs - startDfs;

        return new long[]{bfsTime, dfsTime};
    }

    public void runMultipleTests() {
        for (int i = 0; i < sizes.length; i++) {
            Graph g = buildGraph(sizes[i], new Random(42));

            System.out.println();
            System.out.println("Size: " + sizes[i]);

            results[i] = runTraversals(g);
        }
    }

    public void printResults() {
        System.out.println();
        System.out.println("Results:");
        System.out.printf("%-10s %-15s %-15s%n", "Vertices", "BFS (ns)", "DFS (ns)");

        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("%-10d %-15d %-15d%n",
                    sizes[i],
                    results[i][0],
                    results[i][1]);
        }
    }

    public static Graph buildGraph(int n, Random rand) {
        Graph g = new Graph();

        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }

        if (n == 10) {
            g.addEdge(0, 1);
            g.addEdge(0, 2);

            g.addEdge(1, 3);
            g.addEdge(1, 4);

            g.addEdge(2, 5);

            g.addEdge(3, 6);
            g.addEdge(4, 6);

            g.addEdge(5, 7);
            g.addEdge(6, 8);
            g.addEdge(7, 9);
            g.addEdge(8, 9);
        } else {
            for (int i = 0; i < n - 1; i++) {
                g.addEdge(i, i + 1);
            }

            for (int i = 0; i < n - 2; i += 2) {
                g.addEdge(i, i + 2);
            }
        }

        return g;
    }
}