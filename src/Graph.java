import java.util.*;

public class Graph {
    private Map<Integer, Vertex> vertices;
    private Map<Integer, List<Integer>> adjacencyList;
    private List<Edge> edges;

    public Graph() {
        vertices = new LinkedHashMap<>();
        adjacencyList = new LinkedHashMap<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjacencyList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            throw new IllegalArgumentException("Both vertices must exist before adding an edge.");
        }

        // Directed edge: from -> to
        if (!adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
            edges.add(new Edge(vertices.get(from), vertices.get(to)));
        }
    }

    public void printGraph() {
        System.out.println("Graph structure using adjacency list:");

        for (int vertex : adjacencyList.keySet()) {
            System.out.println(vertex + " -> " + adjacencyList.get(vertex));
        }
    }

    public void bfs(int start) {
        System.out.println("BFS traversal from " + start + ": " + bfsOrder(start));
    }

    public void dfs(int start) {
        System.out.println("DFS traversal from " + start + ": " + dfsOrder(start));
    }

    public List<Integer> bfsOrder(int start) {
        List<Integer> order = new ArrayList<>();

        if (!vertices.containsKey(start)) {
            return order;
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        // BFS uses queue and visits vertices level by level.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (int neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return order;
    }

    public List<Integer> dfsOrder(int start) {
        List<Integer> order = new ArrayList<>();

        if (!vertices.containsKey(start)) {
            return order;
        }

        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        stack.push(start);

        // DFS uses stack and goes deeper before backtracking.
        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                order.add(current);

                List<Integer> neighbors = adjacencyList.get(current);

                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i);

                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return order;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}
