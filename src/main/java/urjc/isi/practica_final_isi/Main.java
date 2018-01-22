package urjc.isi.practica_final_isi;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.MultipartConfigElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

// This code is quite dirty. Use it just as a hello world example 
// to learn how to use JDBC and SparkJava to upload a file, store 
// it in a DB, and do a SQL SELECT query
public class Main {
    
    // Connection to the SQLite database. Used by insert and select methods.
    // Initialized in main
    private static Connection connection;

    // Used to illustrate how to route requests to methods instead of
    // using lambda expressions
    public static String select2(Connection conn, String table,String categ, String peticion, String peticion2) {
        String sql = "";
        String result = "";
    	if(table == "Tabla_categoria") {
    		sql = "SELECT * FROM " + table + " WHERE pelicula=?";
    		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        		ResultSet rs = pstmt.executeQuery();
        		result = rs.getString("categoria");
        	} catch (SQLException e) {
        	System.out.println("Parece que no encuentra la peticion:" + e.getMessage());
        	}
    	}else if(table == "Tabla_vecinos") {
    		sql = "SELECT * FROM " + table + " WHERE peti=?";
    		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        		pstmt.setString(1,peticion);
        		ResultSet rs = pstmt.executeQuery();
        		if(categ == "needed") {
        			result = rs.getString("categ");
        			insert_categoria(connection,rs.getString("categ"),peticion); 
        		}else {
        			result = rs.getString("veci");
        		}
        		
        	} catch (SQLException e) {
        	System.out.println("Parece que no encuentra la peticion:" + e.getMessage());
        	}
    		
    	}else if(table == "Tabla_distancia") {
    		sql = "SELECT * FROM " + table + " WHERE peti1 =?" + " AND "  + " peti2 =?";
    		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        		pstmt.setString(1,peticion);
        		pstmt.setString(2,peticion2);
        		ResultSet rs = pstmt.executeQuery();
        		result = rs.getString("saltos");
        	} catch (SQLException e) {
        	System.out.println("Parece que no encuentra la peticion:" + e.getMessage());
        	}
    	}
    	 	
    	return result;
        }

    public static String select(Connection conn, String table, String peticion, String peticion2) {
    
	String sql = "SELECT * FROM " + table;

	String result = new String();
	
	
	//System.out.println(sql);
	
	try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		//pstmt.setString(1, film);
		ResultSet rs = pstmt.executeQuery();
		Boolean encontrado = true;
		String salida = "";
		//connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

		while (rs.next() && encontrado) {	
			try {
				File file = new File("test1.txt");
				FileWriter fileWriter = new FileWriter(file);
				//System.out.println("Esto es el archivo:" + rs.getString("archivo"));
				fileWriter.write(rs.getString("archivo"));
				fileWriter.close();
				
				if(peticion2 == null || peticion2 == "needed") {
					salida = IndexGraph("test1.txt","/", peticion);
					if(salida != "") {
						if(peticion2 == "needed") {
							insert_categoria(connection,rs.getString("categ"),peticion);
							result = rs.getString("categ");
						}else {
							result= salida;			
							insert_vecinos(connection,peticion,salida,rs.getString("categ"));
						}
						encontrado = false;
					}
				}else{
					salida = IndexGraph("test1.txt","/", peticion);
					if (salida != "") {
						salida = Calc_Dist("test1.txt","/", peticion, peticion2);
						result= salida;
						String[] distancia = salida.split("Distance ");
						String dist = distancia[1];
						insert_distancia(connection,peticion,peticion2,distancia[0],dist);
						encontrado = false;
					}
				}
				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    } catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	
	 //System.out.println("esto es result: " + result);
	return result;
    }
    
    
    public static void insert(Connection conn, String categ, String archivo) {
	String sql = "INSERT INTO categorias(categ, archivo) VALUES(?,?)";

	try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		pstmt.setString(1, categ);
		pstmt.setString(2, archivo);
		pstmt.executeUpdate();
	    } catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }
    
    public static void insert_vecinos(Connection conn, String peti, String veci, String categ) {
    	
    	String sql = "INSERT INTO Tabla_vecinos(peti, veci, categ) VALUES(?,?,?)";

    	try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		pstmt.setString(1, peti);
    		pstmt.setString(2, veci);
    		pstmt.setString(3, categ);
    		pstmt.executeUpdate();
    	    } catch (SQLException e) {
    	    System.out.println(e.getMessage());
    	}
        }
    
    public static void insert_distancia(Connection conn, String peti1, String peti2, String saltos,String dist) {
    	String sql = "INSERT INTO Tabla_distancia(peti1, peti2,saltos,dist) VALUES(?,?,?,?)";

    	try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		pstmt.setString(1, peti1);
    		pstmt.setString(2, peti2);
    		pstmt.setString(3, saltos);
    		pstmt.setString(4, dist);
    		pstmt.executeUpdate();
    	    } catch (SQLException e) {
    	    System.out.println(e.getMessage());
    	}
        }
    
    public static void insert_categoria(Connection conn, String categoria, String pelicula) {
    	String sql = "INSERT INTO Tabla_categoria(categoria,pelicula) VALUES(?,?)";

    	try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		pstmt.setString(1, categoria);
    		pstmt.setString(2, pelicula);
    		pstmt.executeUpdate();
    	    } catch (SQLException e) {
    	    System.out.println(e.getMessage());
    	}
        }
    
  public static String Categoria(Request request, Response response) throws ClassNotFoundException, URISyntaxException {
    	
        String result = request.url();
        String[] res = result.split("/");
        String salida = "";
        System.out.println("Estoy en categoria");
        //distancia entre dos opciones que le pasemos calc_dist
        res[4]=res[4].replace("%20"," ");
        salida = res[4];
        
        String[] resultado = salida.split("\\(");
        salida = resultado[resultado.length - 1];
        /////////////////////////////////////////////////////////
            // Hay que ver que pasa que la base de datos aparece como bloqueada y no deja guardar 
        	// en la Tabla_categoria los valores.
        /////////////////
        if (salida.length() == 5) {   	
        	salida = select2(connection,"Tabla_categoria",null,res[4],null);
        	System.out.println("He mirado en la tabla de Tabla_categoria");
        	if(salida == "") {
        		salida = select2(connection, "Tabla_vecinos","needed",res[4],null);
        		System.out.println("He mirado en la tabla de vecinos");
        	}
        	if(salida == "") {
        		salida = select(connection, "categorias", res[4], null);
        		System.out.println("He mirado en la tabla de categorias");
        	}
        	salida = "La película " + res[4] + " es de la categoría: " + salida;
        	
        }else {
        	salida = "Esto no es una película";
        }       
        
        salida  = salida.replaceAll("(\n)", "<br>");
        System.out.println(salida);
        	
      
    	return salida;
        }
  
  
  
    
    public static String Vecinos(Request request, Response response) throws ClassNotFoundException, URISyntaxException {
    	
    String result = request.url();
    String[] res = result.split("/");
    String salida = "";
    System.out.println("Estoy en vecinos");
    res[4]=res[4].replace("%20"," ");
    System.out.println(res[4]);
    
    salida = select2(connection, "Tabla_vecinos", null, res[4],null);
    
    if(salida == "") {
    	salida = select(connection, "categorias", res[4], null);
    }
    
    salida  = salida.replaceAll("(\n)", "<br>");
    System.out.println(salida);   	
  
	return salida;
    }
    
    //cambiar distancia(copiar lo necesario del anterior main)
    public static String Distancia(Request request, Response response) throws ClassNotFoundException, URISyntaxException {
    	
        String result = request.url();
        String[] res = result.split("/");
        String[] res1 = result.split("%20");
        String salida = "";
        System.out.println("Estoy en distancia");
        res[4]=res[4].replace("%20"," ");
        System.out.println(res[4]);
        res[5]=res[5].replace("%20"," ");
        System.out.println(res[5]);

        
        salida = select2(connection, "Tabla_distancia", null, res[4],res[5]);
        
        if (salida == "") {
        	salida = select(connection, "categorias", res[4], res[5]);
        }
        
        
        salida  = salida.replaceAll("(\n)", "<br>");
        System.out.println(salida);
        	
      
    	return salida;
        }
    
    public static String Calc_Dist(String filename, String delimiter, String peticion1, String peticion2) {
        Graph G = new Graph(filename, delimiter);
        System.out.println("p1" + peticion1);
        
        String respuesta = "";
        PathFinder pf = new PathFinder(G, peticion1);
        //System.out.println("p2");
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
    
    
    
    static String categoria = "";
    
    
    public static void main(String[] args) throws 
	ClassNotFoundException, SQLException {
	port(getHerokuAssignedPort());
	
	
	// Connect to SQLite sample.db database
	// connection will be reused by every query in this simplistic example
	connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

	// In this case we use a Java 8 method reference to specify
	// the method to be called when a GET /:table/:film HTTP request
	// Main::doWork will return the result of the SQL select
	// query. It could've been programmed using a lambda
	// expression instead, as illustrated in the next sentence.
	//get("/:table/:film", Main::doSelect);
	get("/vecinos/*", Main::Vecinos);
	
	get("/distancia/*", Main::Distancia);
	
	get("/categoria/*", Main::Categoria);
		
	
	// In this case we use a Java 8 Lambda function to process the
	// GET /upload_films HTTP request, and we return a form
	get("/upload_films/*", (req, res) -> {
		String formulario = "<form action='/upload' method='post' enctype='multipart/form-data'>" 
			    + "    <input type='file' name='uploaded_films_file' accept='.txt'>"
			    + "    <button>Upload file</button>" + "</form>";
		System.out.println("La url");
		String[] cat = req.url().split("/");
		categoria = cat[4];
		System.out.println(cat[4]);
		
		return formulario;
	}); 
	
	get("/inicio", (req, res) -> {
		String inicio = "<h1>Modo de funcionamiento</h1>"
				+ "<h2>Lo primero es necesario cargar archivos</h2>"
				+ "<ul><li>Para cargar archivos:------------------------>/upload_films/(categoría)</li>"
				+ "<li>Buscar los actores de una película--------->/vecinos/(Película)</li>"
				+ "<li>Buscar las películas que tiene un actor---->/vecinos/(Actor)</li>"
				+ "<li>Buscar la distancia entre dos elementos--->/distancia/(Elemento1)/(Elemento2)</li>"
				+ "<li>Buscar la categoría de una pelicula-------->/categoria/(Película)</li></ul>";
		System.out.println("inicio");
		return inicio;
	}); 
	
	
	
	// You must use the name "uploaded_films_file" in the call to
	// getPart to retrieve the uploaded file. See next call:


	// Retrieves the file uploaded through the /upload_films HTML form
	// Creates table and stores uploaded file in a two-columns table
	post("/upload", (req, res) -> {
		req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/tmp"));
		String result = "File uploaded!";

		
		try (InputStream input = req.raw().getPart("uploaded_films_file").getInputStream()) { 
			// getPart needs to use the same name "uploaded_films_file" used in the form

			// Prepare SQL to create table
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			//statement.executeUpdate("drop table if exists films");
			//statement.executeUpdate("drop table if exists categorias");
			//statement.executeUpdate("create table categorias (categ string, archivo string)");
			//statement.executeUpdate("create table Tabla_vecinos (peti string, veci string, categ string)");
			//statement.executeUpdate("create table Tabla_distancia (peti1 string, peti2 string, saltos string, dist string)");
			//statement.executeUpdate("create table Tabla_categoria (categoria string, pelicula string)");

			// Read contents of input stream that holds the uploaded file
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(isr);
			String response = new String();
			for (String line; (line = br.readLine()) != null; response += (line + "\n") );
			System.out.println("cate");
			System.out.println(categoria); // categoria = categoria
			result = response;			   //result = el archivo
			input.close();
		    }
		
		//guardar categoría y archivo en la base de datos insert(conection,categ,archivo)
		insert(connection,categoria,result);
		return result;
	    });

    }

    static int getHerokuAssignedPort() {
	ProcessBuilder processBuilder = new ProcessBuilder();
	if (processBuilder.environment().get("PORT") != null) {
	    return Integer.parseInt(processBuilder.environment().get("PORT"));
	}
	return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
    }
}
