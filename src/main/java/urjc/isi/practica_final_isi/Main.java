package urjc.isi.practica_final_isi;

import static spark.Spark.*;

import spark.Request;
import spark.Response;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

public class Main {
  
    public static String doWork(Request request, Response response) throws ClassNotFoundException, URISyntaxException {
	
    String result = request.url();
    String[] res = result.split("/");
    String[] res1 = result.split("%20");
    //System.out.println(res[3]); //argumento[3] = distancia o vecinos
    String salida = "";
    if(res[3].equals("vecinos")) {
    	System.out.println("Estoy en vecinos");
    	//a index graph se le pasa un autor y devuelve las peliculas en las que sale
    				  //se le pasa una pelicula y devuelve los actores
  // 	System.out.println(res[4]);
    	res[4]=res[4].replace("%20"," ");
    	System.out.println(res[4]);
    	salida = IndexGraph("resources/data/other-data/movies.txt","/",res[4]);
    	System.out.println(salida);
    	
    }else if(res[3].equals("distancia")) {
    	System.out.println("Estoy en distancia");
    	res[4]=res[4].replace("%20"," ");
    	res[5]=res[5].replace("%20"," ");
    	System.out.println(res[4]);
    	System.out.println(res[5]);
    	salida = Calc_Dist("resources/data/other-data/movies.txt","/",res[4],res[5]);
    	System.out.println(salida);
    }else{
    	System.out.print("Error: Parámetros erroneos ");
    	System.out.println(res[3]);
    }
	return salida;
    }
    
    public static String Calc_Dist(String filename, String delimiter, String peticion1, String peticion2) {
        Graph G = new Graph(filename, delimiter);
        String respuesta = "";
        PathFinder pf = new PathFinder(G, peticion1);
        for (String v : pf.pathTo(peticion2)) {
            respuesta += "   " + v + "<br>";
        }
        respuesta += "Distance " + pf.distanceTo(peticion2);
        return respuesta; 
    }
    
	public static String IndexGraph(String filename, String delimiter, String peticion) {

            // read in the graph from a file
            Graph G = new Graph(filename, delimiter);
            String respuesta = "";
            // read a vertex and print its neighbors
            if (G.hasVertex(peticion)) {
            	for (String w : G.adjacentTo(peticion)) {
            		respuesta += "  " + w + "\n";
            		//StdOut.println("  " + w);
                }
            }
            return respuesta;

    }

	public static void main(String[] args) throws ClassNotFoundException {
        port(getHerokuAssignedPort());

        // spark server
        get("/*", Main::doWork);
        
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
