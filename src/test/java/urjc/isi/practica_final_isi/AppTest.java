package urjc.isi.practica_final_isi;



import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Before;
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
	//primer test
	@Test //(expected=IOException.class)
	public void Test_Distancia() {
		assertEquals( "Kidman, Nicole<br>   Batman Forever (1995)<br>   Fink, John<br>   Bonfire of the Vanities, The (1990)<br>   Hanks, Tom<br>Distance 4", Main.Calc_Dist("resources/data/other-data/movies.txt","/", peticion1, peticion2));
	}
	
	
	
	//primer test: una de las peticiones no existe para calcular la distancia
	/*@Test(expected=IOException.class)
	public void Test_Distancia1() {
		peticion1 = null;
		Main.Calc_Dist("resources/data/other-data/movies.txt","/", peticion1, peticion2);
	}
	
	*/

	
	/*
	//happy path distancia
	//@Test//(expected=IOException.class)
	/*public void Test_Distancia3() {
		Main.Calc_Dist("resources/data/other-data/movies.txt","/", peticion1, peticion2);
	}
	*/
	
	//tercer test: la peticion no existe por lo que no tiene vecinos
	/*@Test(expected=IOException.class)
	public void Test_Vecinos() {
		peticion1 = null;
		Main.IndexGraph("resources/data/other-data/movies.txt","/", peticion1);
	}
	*/
	
	
}


