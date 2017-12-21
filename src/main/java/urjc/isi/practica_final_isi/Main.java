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
    System.out.println(res[3]); //argumento[3] = distancia o vecinos
    
    if(res[3].equals("vecinos")) {
    	System.out.println("Estoy en vecinos");
    	//a index graph se le pasa un autor y devuelve las peliculas en las que sale
    				  //se le pasa una pelicula y devuelve los actores
    	res[4]=res[4].replace("%20"," ");
    	System.out.println(res[4]);
    	
    	
    }else if(res[3].equals("distancia")) {
    	System.out.println("Estoy en distancia");
    	res[4]=res[4].replace("%20"," ");
    	res[5]=res[5].replace("%20"," ");
    }else{
    	System.out.print("Error: Parámetros erroneos ");
    	System.out.println(res[3]);
    }
	return result;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        port(getHerokuAssignedPort());

        // spark server
        get("/*", Main::doWork);
        
        //get("/hello/*/to/*", (request, response) -> {
        //    return "Number of splat parameters: " + request.splat().length;
        //});

    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
