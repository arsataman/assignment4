import java.util.*;

public class Graph {
    private Map<Integer, Vertex> vertices;
    private Map<Integer, List<Edge>> adjacencyList;
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
        addEdge(from, to, 1);
    }

    public void addEdge(int from, int to, int weight) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            throw new IllegalArgumentException("Both vertices must exist before adding an edge.");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Dijkstra's Algorithm does not support negative edge weights.");
        }

        for (Edge edge : adjacencyList.get(from)) {
            if (edge.getDestination().getId() == to) {
                return;
            }
        }

        Edge edge = new Edge(vertices.get(from), vertices.get(to), weight);
        adjacencyList.get(from).add(edge);
        edges.add(edge);
    }

    public void printGraph() {
        System.out.println("Weighted graph structure using adjacency list:");

        for (int vertex : adjacencyList.keySet()) {
            System.out.print(vertex + " -> ");

            List<String> neighbors = new ArrayList<>();

            for (Edge edge : adjacencyList.get(vertex)) {
                int destination = edge.getDestination().getId();
                int weight = edge.getWeight();
                neighbors.add(destination + "(w=" + weight + ")");
            }

            System.out.println(neighbors);
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

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (Edge edge : adjacencyList.get(current)) {
                int neighbor = edge.getDestination().getId();

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

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                order.add(current);

                List<Edge> neighbors = adjacencyList.get(current);

                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i).getDestination().getId();

                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return order;
    }

    public void dijkstra(int start) {
        if (!vertices.containsKey(start)) {
            System.out.println("Start vertex does not exist.");
            return;
        }

        int maxId = Collections.max(vertices.keySet());
        int[] distance = new int[maxId + 1];
        int[] previous = new int[maxId + 1];
        boolean[] visited = new boolean[maxId + 1];
        int infinity = Integer.MAX_VALUE / 2;

        Arrays.fill(distance, infinity);
        Arrays.fill(previous, -1);

        distance[start] = 0;

        for (int i = 0; i < vertices.size(); i++) {
            int current = findClosestUnvisitedVertex(distance, visited);

            if (current == -1) {
                break;
            }

            visited[current] = true;

            for (Edge edge : adjacencyList.get(current)) {
                int neighbor = edge.getDestination().getId();
                int newDistance = distance[current] + edge.getWeight();

                if (!visited[neighbor] && newDistance < distance[neighbor]) {
                    distance[neighbor] = newDistance;
                    previous[neighbor] = current;
                }
            }
        }

        printDijkstraResult(start, distance, previous, infinity);
    }

    private int findClosestUnvisitedVertex(int[] distance, boolean[] visited) {
        int closestVertex = -1;
        int shortestDistance = Integer.MAX_VALUE;

        for (int vertex : vertices.keySet()) {
            if (!visited[vertex] && distance[vertex] < shortestDistance) {
                shortestDistance = distance[vertex];
                closestVertex = vertex;
            }
        }

        return closestVertex;
    }

    private void printDijkstraResult(int start, int[] distance, int[] previous, int infinity) {
        System.out.println("Dijkstra shortest paths from vertex " + start + ":");
        System.out.printf("%-10s %-15s %-25s%n", "Vertex", "Distance", "Path");

        for (int vertex : vertices.keySet()) {
            if (distance[vertex] == infinity) {
                System.out.printf("%-10d %-15s %-25s%n", vertex, "INF", "Not reachable");
            } else {
                System.out.printf("%-10d %-15d %-25s%n", vertex, distance[vertex], buildPath(start, vertex, previous));
            }
        }
    }

    private String buildPath(int start, int target, int[] previous) {
        List<Integer> path = new ArrayList<>();
        int current = target;

        while (current != -1) {
            path.add(current);

            if (current == start) {
                break;
            }

            current = previous[current];
        }

        Collections.reverse(path);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < path.size(); i++) {
            result.append(path.get(i));

            if (i < path.size() - 1) {
                result.append(" -> ");
            }
        }

        return result.toString();
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}