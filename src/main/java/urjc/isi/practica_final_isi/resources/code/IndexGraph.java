package urjc.isi.practica_final_isi.resources.code;
/******************************************************************************
 *  Compilation:  javac IndexGraph.java
 *  Dependencies: Graph.java In.java
 *  Execution:    java IndexGraph movies.txt "/"
 *  Data files:   https://introcs.cs.princeton.edu/45graph/tinyGraph.txt
 *                https://introcs.cs.princeton.edu/45graph/movies.txt
 *                https://introcs.cs.princeton.edu/45graph/amino.csv
 *  
 *  Builds a graph, then accepts vertex names from standard input
 *  and prints its neighbors.
 *
 *  % java IndexGraph tinyGraph.txt " "
 *  C
 *    A
 *    B
 *    G
 *  A
 *    B
 *    C
 *    G
 *    H
 *
 ******************************************************************************/
//javac IndexGraph.java
//java IndexGraph ../data/other-data/movies.txt "/"
//Kidman, Nicole 

public class IndexGraph {

    public static String main(String filename, String delimiter, String peticion) {

        // read in the graph from a file
        Graph G = new Graph(filename, delimiter);
        String respuesta = "";
        // read a vertex and print its neighbors
        if (G.hasVertex(peticion)) {
        	for (String w : G.adjacentTo(peticion)) {
        		respuesta += w + "\n";
        		//StdOut.println("  " + w);
            }
        }
        return respuesta;
    }

}
