/**
 * Resolução da AD2 2020.2 - Prog. Orientada a Objetos - TSC/UFF
 *
 * @author ARIADNE PINHEIRO
 * @since 02/11/2020
 * 
 * Esta implementação usa pesquisa em profundidade (também conhecida como DepthFirstSearch
 * ou DFS algorithm).
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.exit;

public class AD2_POO_2020_2 {

    public static void main(String[] args) throws IOException {

        //Pede o nome do arquivo
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o arquivo a ser carregado!");
        String arquivo = entrada.nextLine();

        //Valida se o arquivo possui extensao
        if (arquivo.length() < 5) {
            System.out.println("Arquivo incorreto ou falta parâmetro!");
        } else {
            BufferedReader bufferedReader = null;
            String readLine = "";
            //Criando uma Lista de Vetores de String = String[]
            List<String[]> arestas = new ArrayList<>();

            //Pega o caminho de onde esta executando o java
            String currentDir = System.getProperty("java.class.path");
            String concat = "/";
            //Se for windows devera converter \ para \\ assim sendo lido normalmente como string
            if (System.getProperty("os.name").contains("Windows")) {
                currentDir = currentDir.replace("\\", "\\\\");
                concat = "\\";
            }

            currentDir = currentDir + concat;

            try {
                bufferedReader = new BufferedReader(new FileReader(currentDir + arquivo));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }

            if (bufferedReader != null) {
                try {
                    while (true) {
                        if (readLine != null) {
                            String[] aux = readLine.split(" ");
                            if (aux.length > 1) {
                                arestas.add(aux);
                            }
                        } else {
                            break;
                        }
                        readLine = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                AtomicInteger count = new AtomicInteger();

                String[] vertices = arestas.get(0);

                arestas.remove(0);

                int lastValue = Integer.parseInt(vertices[vertices.length - 1]);

                CycleVerify cycle = new CycleVerify(arestas.size());
                ConnectedVerify connected = new ConnectedVerify(arestas.size(), lastValue);

                ArrayList<String[]> compare = new ArrayList<>();
                AtomicBoolean orientado = new AtomicBoolean(true);
                arestas.forEach(item -> {
                    cycle.addEdge(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
                    connected.addEdge(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
                    if (count.get() > 0) {
                        while (orientado.get()) {
                            if (compare.get(0)[0].equals(item[1]) && compare.get(0)[1].equals(item[0])) {
                                orientado.set(false);
                            }
                        }
                    }
                    compare.add(0, item);
                    count.getAndIncrement();
                });
                StringBuilder resposta = new StringBuilder();
                resposta.append(orientado.get() ? "orientado" : "nao-orientado");
                resposta.append(" - ");
                boolean result = cycle.isCycle();
                resposta.append(result ? "ciclico" : "nao-ciclico");
                resposta.append(" - ");
                List<String[]> lista = connected.connectedComponents();
                resposta.append(lista.size() > 1 ? "desconexo":"conexo");
                System.out.println(resposta.toString());

            } else {
                exit(0);
            }
        }
    }
}
