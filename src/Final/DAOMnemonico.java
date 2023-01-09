package Final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOMnemonico {
	static Connection conexion;
	static Statement sentencia;
	static ResultSet resultado;
	
	static String usuario = "";
	static String clave = "";
	static String url = "";
	
	public void cargarDatos(ArrayList<Mnemonico> lista){
		String mn, md, li, co;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("Error 1: " + e);
		}
		
		try {
			conexion = DriverManager.getConnection(url, usuario, clave);
			sentencia = conexion.createStatement();
			resultado = sentencia.executeQuery("SELECT * FROM codigos_operacion");
			//resultado.next();
			while(resultado.next()){
				//System.out.println("Mnemonico: " + resultado.getString(1));
				mn = resultado.getString("Mnemonico");
				md = resultado.getString("modo_direccionamiento");
				li = resultado.getString("longitud_instruccion");
				co = resultado.getString("codigo_operacion");
				
				lista.add(new Mnemonico(mn, md, Integer.parseInt(li), co));
			}
			conexion.close();
		}catch(SQLException e) {
			System.out.println("Error 2: " + e);
		}
	}
}
