/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 4 : BELLMAN-FORD AND PATH VECTOR ROUTING
 * ----------------------------------------------------------------------------
 *  Title : Develop a program to find the shortest path between vertices
 *          using the Bellman-Ford and Path Vector Routing Algorithm.
 *  Aim   : To develop and execute a Java program to find the shortest path
 *          between vertices in a network using the Bellman-Ford and Path
 *          Vector Routing algorithms.
 * ============================================================================
 */

import java.util.*;

public class BellmanFordPathVector {

    static final int INF = 99999; // represents no direct connection

    // Edge of the graph
    static class Edge {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // ---------------- Bellman-Ford Algorithm ----------------
    static void bellmanFord(int vertices, ArrayList<Edge> edges, int source) {
        int[] distance = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(distance, INF);
        Arrays.fill(parent, -1);
        distance[source] = 0;

        // Relax all edges (V - 1) times
        for (int i = 1; i < vertices; i++) {
            for (Edge edge : edges) {
                if (distance[edge.source] != INF &&
                        distance[edge.source] + edge.weight < distance[edge.destination]) {
                    distance[edge.destination] = distance[edge.source] + edge.weight;
                    parent[edge.destination] = edge.source;
                }
            }
        }

        // Check for negative weight cycles
        for (Edge edge : edges) {
            if (distance[edge.source] != INF &&
                    distance[edge.source] + edge.weight < distance[edge.destination]) {
                System.out.println("Negative weight cycle detected.");
                return;
            }
        }

        System.out.println("\nBellman-Ford Shortest Paths:");
        for (int i = 0; i < vertices; i++) {
            System.out.print("To Vertex " + i + ": ");
            if (distance[i] == INF) {
                System.out.println("No path");
            } else {
                System.out.print("Cost = " + distance[i] + ", Path = ");
                printPath(parent, i);
                System.out.println();
            }
        }
    }

    // Recursively prints the path from source to the given vertex
    static void printPath(int[] parent, int vertex) {
        if (vertex == -1) {
            return;
        }
        printPath(parent, parent[vertex]);
        if (parent[vertex] != -1) {
            System.out.print(" -> ");
        }
        System.out.print(vertex);
    }

    // ---------------- Path Vector Routing ----------------
    static void pathVector(int vertices, int[][] graph, int source) {
        int[] distance = new int[vertices];
        String[] path = new String[vertices];
        Arrays.fill(distance, INF);
        distance[source] = 0;
        path[source] = String.valueOf(source);

        for (int k = 0; k < vertices - 1; k++) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (graph[i][j] != INF &&
                            distance[i] != INF &&
                            distance[i] + graph[i][j] < distance[j]) {
                        distance[j] = distance[i] + graph[i][j];
                        path[j] = path[i] + " -> " + j;
                    }
                }
            }
        }

        System.out.println("\nPath Vector Routing:");
        for (int i = 0; i < vertices; i++) {
            System.out.print("To Vertex " + i + ": ");
            if (distance[i] == INF) {
                System.out.println("No path");
            } else {
                System.out.println("Cost = " + distance[i] + ", Path = " + path[i]);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---------------- Read the cost matrix ----------------
        System.out.print("Enter the number of vertices: ");
        int vertices = sc.nextInt();
        int[][] graph = new int[vertices][vertices];
        ArrayList<Edge> edges = new ArrayList<>();

        System.out.println("Enter the cost matrix:");
        System.out.println("Enter 99999 for no direct connection.");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                graph[i][j] = sc.nextInt();
                if (graph[i][j] != INF && i != j) {
                    edges.add(new Edge(i, j, graph[i][j]));
                }
            }
        }

        System.out.print("Enter the source vertex: ");
        int source = sc.nextInt();

        // ---------------- Run both algorithms ----------------
        bellmanFord(vertices, edges, source);
        pathVector(vertices, graph, source);

        sc.close();
    }
}