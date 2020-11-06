
/**
 * Resolução da AD2 2020.2 - Prog. Orientada a Objetos - TSC/UFF
 *
 * @author ARIADNE PINHEIRO
 * @since 02/11/2020
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConnectedVerify {

    int V;
    int L;
    String auxBuilder;
    LinkedList<Integer>[] adjListArray;
    List<String[]> response;

    // Constructor
    ConnectedVerify(int V, int L) {
        this.V = V;
        this.L = L;
        adjListArray = new LinkedList[V];
        response = new ArrayList<>();
        auxBuilder = "";
        for (int i = 0; i < V; i++) {
            adjListArray[i] = new LinkedList<Integer>();
        }
    }

    void addEdge(int src, int dest) {
        adjListArray[src].add(dest);
        adjListArray[dest].add(src);
    }

    void DFSUtil(int v, boolean[] visited) {
        visited[v] = true;
        auxBuilder += v + " ";
        for (int x : adjListArray[v]) {
            if (!visited[x])
                DFSUtil(x, visited);
        }
    }

    public List<String[]> connectedComponents() {
        boolean[] visited = new boolean[V];
        for (int v = 0; v < V; ++v) {
            if (!visited[v]) {
                DFSUtil(v, visited);
                String[] aux = auxBuilder.split(" ");
                if (aux.length > 1) {
                    response.add(aux);
                }
                auxBuilder = "";

            }
        }
        return response;
    }
}
