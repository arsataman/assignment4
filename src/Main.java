import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Experiment experiment = new Experiment();

        System.out.println("Small Graph:");
        Graph smallGraph = Experiment.buildGraph(10, new Random(1));

        smallGraph.printGraph();
        System.out.println();

        smallGraph.bfs(0);
        smallGraph.dfs(0);

        experiment.runMultipleTests();
        experiment.printResults();
    }
}