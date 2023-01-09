package Final;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DAOArchivo {
	String directorio;
	
	public DAOArchivo(String directorio, String nombreArchivo) {
		directorio += nombreArchivo;
		this.directorio = directorio;
	}
	
	public void cargarArchivo(ArrayList<String> lista) {
		File archivo;
		FileReader lector;
		BufferedReader buffer;
		
		
		String linea;
		try {
			archivo = new File(directorio);
			
			lector = new FileReader(archivo);
			buffer = new BufferedReader(lector);
			
			while((linea = buffer.readLine())!=null) {
				lista.add(linea);
			}
			lector.close();
		}catch(Exception e) {
			System.out.println("Error archivo: " + e);
		}
	}
	
	public void guardarArchivo(String cadena) {
		FileWriter archivo;
		PrintWriter escritor;
		try {
			archivo = new FileWriter(directorio);
			escritor = new PrintWriter(archivo);
			
			escritor.write(cadena);
			
			archivo.close();
			escritor.close();
		}catch(IOException e) {
			System.out.println("Error archivo: " + e);
		}
		
	}
}
