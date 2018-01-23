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
<<<<<<< HEAD
	
	@Test //(expected=IOException.class)
	public void Test_Distancia() {
		String resultado = "   Kidman, Nicole<br>   Batman Forever (1995)<br>   Fink, John<br>   Bonfire of the Vanities, The (1990)<br>   Hanks, Tom<br>Distance 4";
		assertEquals( resultado, Main.Calc_Dist("movies.txt","/",peticion1, peticion2));
=======
	//primer test
	@Test //(expected=IOException.class)
	public void Test_Distancia() {
		assertEquals( "Kidman, Nicole<br>   Batman Forever (1995)<br>   Fink, John<br>   Bonfire of the Vanities, The (1990)<br>   Hanks, Tom<br>Distance 4", Main.Calc_Dist("resources/data/other-data/movies.txt","/", peticion1, peticion2));
>>>>>>> ed8b7c747128aebb041a3806afe8a6542a4ffcfa
	}
	
	
	//segundo test
	@Test //(expected=IOException.class)
	public void Test_Vecinos() {
		assertEquals( "Batman Forever (1995)<br>  Bewitched (2005)<br>  Billy Bathgate (1991)<br>  Birth (2004)<br>  Birthday Girl (2001)<br>  Cold Mountain (2003)<br>  Days of Thunder (1990)<br>  Dead Calm (1989)<br>  Dogville (2003)<br>  Eyes Wide Shut (1999)<br>  Far and Away (1992)<br>  Hours, The (2002)<br>  Human Stain, The (2003)<br>  Interpreter, The (2005)<br>  Malice (1993)<br>  Moulin Rouge! (2001)<br>  My Life (1993 I)<br>  Others, The (2001)<br>  Panic Room (2002)<br>  Peacemaker, The (1997)<br>  Portrait of a Lady, The (1996)<br>  Practical Magic (1998)<br>  Stanley Kubrick: A Life in Pictures (2001)<br>  Stepford Wives, The (2004)<br>  To Die For (1995)<br>", Main.IndexGraph("resources/data/other-data/movies.txt","/", peticion1));
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


