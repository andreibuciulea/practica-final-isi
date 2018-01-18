package urjc.isi.practica_final_isi;
import static org.junit.Assert.*;
import java.io.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import urjc.isi.practica_final_isi.resources.code.IndexGraph;

public class IndexGraphTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        
        // Checkout what's going on with the static attributes of Stutter.
        // Perhaps you have to implement something here. 

    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
    
    @Test
    public void testFromFile() throws IOException {
        // If run from Eclipse the file inputFile must be in the directory of the
        // project (../src)

    	
    	try {//test para probar stuter con un fichero vacio
    		IndexGraph.main(new String[] { "inputFile","/","peticion" });
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals("Repeated word on line 2: word word\n", outContent.toString());
    }
}