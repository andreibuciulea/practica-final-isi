package urjc.isi.practica_final_isi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URISyntaxException;

import org.junit.Before;

import spark.Request;
import spark.Response;

import org.junit.*; //paquete para test

public class AppTest   
{
	//declaramos variable globales
	String peticion1 = "";
	String peticion2 = "";
	
	@Before public void inicializar()
	{
		peticion1 = "Kidman, Nicole";
		peticion2 = "Hanks, Tom";
	}
	
	//TEST 1: Happy path Calc_Dist
	@Test 
	public void Test_Distancia() {
		String resultado = "   Kidman, Nicole<br>   Batman Forever (1995)<br>   Fink, John<br>   Bonfire of the Vanities, The (1990)<br>   Hanks, Tom<br>Distance 4";
		assertEquals( resultado, Main.Calc_Dist("data/other-data/movies.txt","/",peticion1, peticion2));
	}
	
	// TEST 1.1: Ambas peticiones a null
	@Test (expected=IllegalArgumentException.class)
	public void Test_Distancia1() {
		peticion1 = null;
		peticion2= null;
		String resultado = "   Kidman, Nicole<br>   Batman Forever (1995)<br>   Fink, John<br>   Bonfire of the Vanities, The (1990)<br>   Hanks, Tom<br>Distance 4";
		assertEquals( resultado, Main.Calc_Dist("data/other-data/movies.txt","/",peticion1, peticion2));
	}
	
	//TEST 2: Happy path IndexGraph
	@Test 
	public void Test_Vecinos() {
		String busco = "Melson, Sara";
		String resultado = "  Malice (1993)\n";
		assertEquals( resultado, Main.IndexGraph("data/other-data/movies.txt","/", busco));
	}
	
	// TEST 2.2: Fichero vacío
	@Test 
	public void Test_Vecinos1() throws IOException {
		String busco = "Melson, Sara";
		String resultado = "";
		File fichero = new File("data/other-data/NewFile.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
		assertEquals( resultado, Main.IndexGraph("data/other-data/NewFile.txt","/", busco));
		bw.close();
	}
	
	private static Connection connection;
	
	//TEST 3: happy path select2
	@Test
	public void Test_Select2 () throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		String table = "Tabla_vecinos";
		String categ = null;
		String peticion = "Arvanites, Steven";
		String peticion2 = null;
		String resultado = "";
		assertEquals (resultado, Main.select2(connection, table,categ,peticion,peticion2));
	}
	
	//TEST 3.1: Select2 para un actor que no este dentro de la BD
	@Test 
	public void Test_Select2_1 () throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		String table = "Tabla_distancia";
		String categ = null;
		String peticion = "Wilson, Alec";
		String peticion2 = "Kidman, Nicole";
		String resultado = "";
		assertEquals (resultado, Main.select2(connection, table,categ,peticion,peticion2));
	}
	
	//TEST 4: happy path Select
	@Test 
	public void Test_select() throws SQLException{
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		String table = "Tabla_distancia";
		String peticion = "Ducommun, Rick";
		String peticion2 = "Drier, Moosie";
		String resultado = "";
		assertEquals (resultado, Main.select(connection, table,peticion,peticion2));
	}
	
	//TEST 4.1: Select para un actor que no este dentro de la BD 
	@Test 
	public void Test_select_1() throws SQLException{
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		String table = "Tabla_distancia";
		String peticion = "Celia__";
		String peticion2 = "Kidman, Nicole";
		String resultado = "";
		assertEquals (resultado, Main.select(connection, table,peticion,peticion2));
	}
	
	Request request = null;
	Response response= null;
	
	//TEST 5: categoria
	@Test (expected=NullPointerException.class)
	public void Test_categoria()  throws ClassNotFoundException, URISyntaxException {
		Main.Categoria(request, response);
	}
	
	//TEST 6: vecinos
	@Test (expected=NullPointerException.class)
	public void Test_Vecinos_RequestResponse() throws ClassNotFoundException, URISyntaxException {
		Main.Vecinos(request, response);
	}
	
	//TEST 7: Distancia
	@Test (expected=NullPointerException.class)
	public void Test_Distancia_RequestResponse() throws ClassNotFoundException, URISyntaxException {
		Main.Distancia(request, response);
	} 
	
}