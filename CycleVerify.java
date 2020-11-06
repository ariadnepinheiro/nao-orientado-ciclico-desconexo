/**
 * Resolução da AD2 2020.2 - Prog. Orientada a Objetos - TSC/UFF
 *
 * @author ARIADNE PINHEIRO
 * @since 02/11/2020
 *
 * Esta implementação usa pesquisa em profundidade (também conhecida como DepthFirstSearch
 * ou DFS algorithm).
 *
 */

import java.util.LinkedList;

public class CycleVerify {

    int vertices;
    LinkedList<Integer>[] adjList;

    public CycleVerify(int vertices) {
        this.vertices = vertices;
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination) {
        adjList[source].addFirst(destination);
        adjList[destination].addFirst(source);
    }

    public boolean isCycle() {
        boolean result = false;

        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                if (isCycleUtil(i, visited, -1)) {
                    return true;
                }
            }
        }
        return result;
    }

    boolean isCycleUtil(int currVertex, boolean[] visited, int parent) {
        visited[currVertex] = true;

        for (int i = 0; i < adjList[currVertex].size(); i++) {
            int vertex = adjList[currVertex].get(i);

            if (vertex != parent) {
                if (visited[vertex]) {
                    return true;
                } else {
                    if (isCycleUtil(vertex, visited, currVertex)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
