package urjc.isi.practica_final_isi;



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
		peticion2 = "Titanic";
	}
	
	//primer test: una de las peticiones no existe para calcular la distancia
	/*@Test(expected=IOException.class)
	public void Test_Distancia1() {
		peticion1 = null;
		Main.Calc_Dist("resources/data/other-data/movies.txt","/", peticion1, peticion2);
	}
	
	
	//segundo test: ambas peticiones no existen para calcular la distancia
	@Test //(expected=IOException.class)
	public void Test_Distancia2() {
		peticion1 = null;
		peticion2 = null;
		Main.Calc_Dist("resources/data/other-data/movies.txt","/", peticion1, peticion2);
	}*/
	
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
	
	//happy path vecinos
	@Test(expected=IOException.class)
	public void Test_Vecinos2() {
		Main.IndexGraph("resources/data/other-data/movies.txt","/", peticion1);
	}*/
	
	
}


//dudas: 
//diferencia entre expected=IOException vs excepted=NullPointerException
//porque funciona igual con ello que sin ello 
