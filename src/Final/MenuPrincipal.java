package Final;
import java.util.ArrayList;

public class MenuPrincipal {
	//Para archivos
	private AdminArchivo adminArchivo;
	private AdminArchivo adminArchivoResultados;
	private AdminArchivo adminArchivoEtiquetas;
	
	//Para analizar lineas codigo
	private AdminLineaCodigo adminLineaCodigo;
	private ArrayList<String> lineas; //lineas leidas del archivo
	private ArrayList<String> lineasAnalizadas; //ya analizadas, solo les falta el codigo de operacion
	private ArrayList<String> etiquetas;
	private ArrayList<Integer> valoresEtiquetas;//guardados en decimal
	private ArrayList<String> modosDireccionamiento;
	private ArrayList<String> codigosOperacion; //guarda los codigos ya analizados (incluyendo los _FDR_)
	
	//Para cargar los mnemonicos de la base de datos
	private AdminMnemonico adminMneminico;
	
	private final String ruta = "/home/dany/Escritorio/semestre/seminario traductores/Practicas/14/";
	private final String nombreArchivo = "Prueba.asm";
	private final String nombreArchivoResultado = "prueba.lst";
	private final String nombreArchivoEtiqueta = "etiquetas.tab";
	
	//contador del programa
	private int contadorPrograma;
	private int lineaActual;
	
	MenuPrincipal(){
		adminArchivo = new AdminArchivo(ruta, nombreArchivo);
		adminArchivoResultados = new AdminArchivo(ruta, nombreArchivoResultado);
		adminArchivoEtiquetas = new AdminArchivo(ruta, nombreArchivoEtiqueta);
		adminArchivo.cargarArchivo();
		adminMneminico = new AdminMnemonico();
		adminMneminico.cargarDatos();
		etiquetas = new ArrayList<>();
		valoresEtiquetas = new ArrayList<>();
		modosDireccionamiento = new ArrayList<>();
		codigosOperacion = new ArrayList<>();
		adminLineaCodigo = new AdminLineaCodigo(adminArchivo.dameLineas());
		lineasAnalizadas = new ArrayList<>();
		lineas = adminLineaCodigo.dameLineas();
		contadorPrograma = -1;
		
		int i, tam;
		
		//Paso 1 
		tam = adminArchivo.dameTotalLineas();
		for(i=0; i<tam; i++) {
			analizarLinea(i); //obtiene las lineas ya analizadas solo falta el calcular el codigo de operacion
		}
		
		crearTablaEtiquetas();
		
		//Paso 2
		for(i=0; i<tam; i++) {
			lineaActual = i;
			calcularCodigosOperacion(i);
		}
		
		//Mostrar resultados
		mostrarResultados();
	}
	
	//Paso 1
	public void analizarLinea(int i) {
		String s = "";
		String etiqueta, operacion, operando;
		String sistema, numero;
		int numeroDecimal;
		String numHexa, modoDir;
		String codigoOperacion;
		int longitudInstruccion=-1;
		
		adminLineaCodigo.separarLinea(lineas.get(i));

		//estos datos se obtienen de separar la linea, los demas se calculan
		etiqueta = adminLineaCodigo.dameEtiqueta();
		operacion = adminLineaCodigo.dameOperacion();
		operando = adminLineaCodigo.dameOperando();
		sistema = adminLineaCodigo.dameSistemaNumeracion(operando);
		modoDir = codigoOperacion = "";
		
		//agregar etiqueta (si tiene)
		etiquetas.add(etiqueta);
		valoresEtiquetas.add(0);
		
		//REVISAR SI EXISTE LA OPERACION
		//PUEDE SER UNA PALABRA RESERVADA
		//EL OPERADOR PUDE SER UN NUMERO O ETIQUETA
		//EL OPERANDO PUDE SER UNA ETIQUETA, UN NUMERO, O TENER FORMA r,n
		//ACTUALMENTE SOLO BNE PUEDE TENER ETIQUETA COMO OPERANDO
		/******************************************************************/
		if(adminMneminico.existeMnemonico(operacion)) {//existe el mnemonico
			//Posibles casos
				//1 operacion, numero
				//2 operacion,
				//3 operacion, etiqueta (actualmente solo bne puede hacer esto)
				//4 operacion, (n,r) 
			if(operacion.equals("BNE") || operacion.equals("LBNE")) {//REL
				//hay tres casos
					//el operando es numero
					//el operando es etiqueta
					//el operando tiene forma (a,b)
				modoDir = adminLineaCodigo.dameModoDireccionamiento(operacion, "0");
				codigoOperacion = adminMneminico.dameCodigoOperacion(operacion, modoDir);
				/***** REVISAR SI ESTA EN RANGO, SI ESTA CALULCAR CODIGO OPERACION, SI NO, PONER _FDR_ *****/
				if(false) {
					codigoOperacion = "_FDR";
				}
				else {
				}
			}
			else if(operando.contains(",")) {//Forma (n,r) Solor "LDAA", "IBNE" y "CMPA"
				if(operacion.equals("IBNE")) {
					modoDir = "REL";
					codigoOperacion = adminMneminico.dameCodigoOperacion(operacion, modoDir);
				}
				else if(operacion.equals("LDAA")){//Para LDAA
					String[] modoDir_Y_parteCodigo;
					modoDir_Y_parteCodigo = modoDireccionamientoNueve(modoDir, codigoOperacion, operacion, operando, sistema);
					modoDir = modoDir_Y_parteCodigo[0];
					codigoOperacion = adminMneminico.dameCodigoOperacion(operacion, modoDir);
				}
				else if(operacion.equals("CMPA")){
					//System.out.println("En CMPA:");
					String[] modoDir_Y_parteCodigo;
					modoDir_Y_parteCodigo = modoDireccionamientoNueve(modoDir, codigoOperacion, operacion, operando, sistema);
					modoDir = modoDir_Y_parteCodigo[0];
					codigoOperacion = adminMneminico.dameCodigoOperacion(operacion, modoDir);
				}
				
				
				
				/***** REVISAR SI ESTA EN RANGO, SI ESTA CALULCAR CODIGO OPERACION, SI NO, PONER _FDR_ *****/
				/*
				if(false) {
					codigoOperacion = "_FDR";
				}
				else {
					//codigoOperacion = codigoOperacion.substring(0, 2) + modoDir_Y_parteCodigo[1];
				}
				*/
			}
			else if(operacion.equals("JMP")) {
				modoDir = "EXT";
				codigoOperacion = "06hhll";
			}
			else {//caso general INH, IMM, DIR, EXT
				modoDir = adminLineaCodigo.dameModoDireccionamiento(operacion, operando);
				codigoOperacion = adminMneminico.dameCodigoOperacion(operacion, modoDir);
				/***** REVISAR SI ESTA EN RANGO, SI ESTA CALULCAR CODIGO OPERACION, SI NO, PONER _FDR_ *****/
				if(false) {
					codigoOperacion = "_FDR";
				}
				else {
					//codigoOperacion = calcularCodigoOperacion(codigoOperacion, Convertidor.aDecimal(sistema, adminLineaCodigo.dameNumero(operando)));
				}
			}
			
			//Todos tienen longitud de instruccion
			longitudInstruccion = adminMneminico.dameLongitudInstruccion(operacion, modoDir);
			
			s += Integer.toHexString(contadorPrograma);
			s += "\t" + lineas.get(i) + "\t\tModo: " + modoDir + "\tLI: " + longitudInstruccion + "\tCO: " + codigoOperacion;
		}
		else if(PalabraReservada.exitePalabra(operacion)) {//existe palabra reservada
			if(operacion.equals("ORG")) {
				String a_ = adminLineaCodigo.dameNumero(operando);
				contadorPrograma = Integer.parseInt(a_, 16);
				s += lineas.get(i);
			}
			else {
				s += Integer.toHexString(contadorPrograma);
				s += "\t" + lineas.get(i);
			}
		}
		else if(!etiqueta.equals("")){//Solo es una etiqueta sin nada mas en la linea
			s += Integer.toHexString(contadorPrograma);
			s += lineas.get(i);
		}
		else {//Error (no existe mnemonico, palabra reservada, ni etiqueta)
			s += lineas.get(i);
			s += "<<<<<<<<<<Error";
		}
		
		//Crear listas con datos necesarios para calcular el codigo de operacion
		modosDireccionamiento.add(modoDir);
		
		//if(!operacion.equals("ORG")) {//sumar al contador hasta el final
		contadorPrograma += adminMneminico.dameLongitudInstruccion(operacion, modoDir);
		//}
		
		System.out.println(s);
		lineasAnalizadas.add(s);
		
	}
	
	//Paso 2
	public void calcularCodigosOperacion(int i) {
//		System.out.println("Calculando de:   " + lineas.get(i));
		//String lineaAnalizada;
		String operacion, modoDir, numero, operando, sistema;
		int numeroDecimal;
		
		adminLineaCodigo.separarLinea(lineas.get(i)); //primero tienes que decir que lina vas a anliazar para poder obtener los datos
		//lineaAnalizada = lineasAnalizadas.get(i);//creo que no necesitare esto
		
		//System.out.println("Analizando: " + lineasAnalizadas.get(i));
		
		operacion = adminLineaCodigo.dameOperacion();
		modoDir = modosDireccionamiento.get(i);
		
		String codigoOperacion = adminMneminico.dameCodigoOperacion(operacion, modoDir);
//		System.out.println("Modo: " +  modoDir + ", Codigo: " + codigoOperacion);
		
		if(PalabraReservada.exitePalabra(operacion)) {
			//System.out.println("Entro a palabra reservada");
			codigoOperacion="";
		}
		else if(modoDir.equals("REL")) {//para BNE, IBNE
			operacion = adminLineaCodigo.dameOperacion();
			operando = adminLineaCodigo.dameOperando();
			//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++ESTA EN REL el operando es:" + operando);
			if(etiquetas.contains(operando)) {//El operando es una etiqueta
				int num1, num2;
				num1 = valoresEtiquetas.get(etiquetas.indexOf(operando));				
				num2 = Integer.parseInt(lineasAnalizadas.get(lineaActual+1).substring(0, 4), 16);
				//la operacion de abajo ya tiene incluidos los fuera de rango
				codigoOperacion = calcularCodigoOperacionRelativo(num1, num2, codigoOperacion);
			}
			else if(operando.contains(",")) {//Para IBNE
				int num1=0, num2;
				String registro;
				String operandoAux; //quitar al registro y la coma
				
				operandoAux = operando.substring(2, operando.length());
				if(etiquetas.contains(operandoAux)) {
					num1 = valoresEtiquetas.get(etiquetas.indexOf(operandoAux));
				}
				num2 = Integer.parseInt(lineasAnalizadas.get(lineaActual+1).substring(0, 4), 16);
				
				registro = operando.substring(0,1);
				codigoOperacion = codigoOperacion + registro + "0" + calcularCodigoOperacionRelativo(num1, num2, codigoOperacion);
			}
			else {//El operando es un numero
				int num1, num2;
				operando = adminLineaCodigo.dameOperando();
				sistema = adminLineaCodigo.dameSistemaNumeracion(operando);
				numero = adminLineaCodigo.dameNumero(operando);
				num1 = Convertidor.aDecimal(sistema, numero);
				num2 = Integer.parseInt(lineasAnalizadas.get(lineaActual+1).substring(0, 4), 16);
				//la operacion de abajo ya tiene incluidos los fuera de rango
				codigoOperacion = calcularCodigoOperacionRelativo(num1, num2, codigoOperacion);
			}
		}
		else if(modoDir.contains("IDX")) {//para la forma (n,r)
			String subCodigo;
			operando = adminLineaCodigo.dameOperando();
			sistema = adminLineaCodigo.dameSistemaNumeracion(operando);
			subCodigo = modoDireccionamientoNueve(modoDir, codigoOperacion, codigoOperacion, operando, sistema)[1];
			codigoOperacion = codigoOperacion.substring(0, 2) + subCodigo;
			
			//System.out.println("Comparara modo: "+ modoDir);
			if(modoDir.equals("IDX1")) {
				String numCadena = operando.substring(0, operando.indexOf(","));
				codigoOperacion += Integer.toHexString(Integer.parseInt(numCadena));
			}
			else if(modoDir.equals("IDX2")) {
				////FALTA PROBAR ESTA FORMULA
				//System.out.println("aqui **************: " + operando);
				operando = operando.replace("[", "");
				operando = operando.replace("]", "");
				//System.out.println("OP: " + operando);
				String numCadena = operando.substring(0, operando.indexOf(","));
				String cadAux="";
				numCadena = Integer.toHexString(Integer.parseInt(numCadena));
				for(int aux=numCadena.length(); aux<4; aux++) {
					cadAux += "0";
				}
				if(operando.contains("-")) {
					codigoOperacion = codigoOperacion.substring(0,2) + "_FDR_";
				}
				else {
					codigoOperacion += cadAux + numCadena;
				}
			}
			
//			if(estaEnRango(modoDir, subCodigo, numeroDecimal)) {
//				codigoOperacion = codigoOperacion.substring(0, 2) + subCodigo;
//			}
//			else {
//				codigoOperacion += "_FDR_";
//			}
		}
		else if(adminLineaCodigo.dameOperacion().equals("JMP")) {
			operando = adminLineaCodigo.dameOperando();
			numero = adminLineaCodigo.dameNumero(operando);
			sistema = adminLineaCodigo.dameSistemaNumeracion(operando);
			if(etiquetas.contains(operando)) {
				//System.out.println("->>>>>>>> " + valoresEtiquetas.get(etiquetas.indexOf("E4")));
				numeroDecimal = valoresEtiquetas.get(etiquetas.indexOf("E1"));
			}
			else {
				numeroDecimal = Convertidor.aDecimal(sistema, numero);
			}
			
			codigoOperacion = calcularCodigoOperacion(codigoOperacion, numeroDecimal);
		}
		else {//para INH, IMM, DIR, EXT
			operando = adminLineaCodigo.dameOperando();
			numero = adminLineaCodigo.dameNumero(operando);
			sistema = adminLineaCodigo.dameSistemaNumeracion(operando);
			numeroDecimal = Convertidor.aDecimal(sistema, numero);
			if(estaEnRango(modoDir, codigoOperacion, numeroDecimal)){
				if(modoDir.equals("INH")) {//esto porque los "INH" no se le agrega nada al codigo de operacion
					codigoOperacion = codigoOperacion;
				}
				else {
					codigoOperacion = calcularCodigoOperacion(codigoOperacion, numeroDecimal);
				}
			}
			else {
				codigoOperacion += "_FDR_";
			}
		}
		
		codigosOperacion.add(codigoOperacion);
		
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<Codigo caluclado: " + codigoOperacion);
	}
	
	//es para los que tiene forma (n,r) REGRESA MODO DIRECCIONAMIENTO Y PARTE NO FIJA DEL CODIGO DE OPERACION
	public String[] modoDireccionamientoNueve(String modoDir, String codigoOperacion, String operacion, String operando, String sistema) {
		//System.out.println("Entro a nueve con: " + operacion + ", " + operando);
		String X="00", Y="01", SP="10", PC="11";
		String aux[] = operando.split(",");//separa numero y registro (n, r)
		String n; //el numero para saber en que rango se encuentra
		int nEntero;//el numero pero en entero
		String b; //cadena en binario final
		String numBinario; //el numero (n) en binario
		String respuesta[];
		String parte1Bin, parte2Bin, parte1Hex, parte2Hex;
		
		n = aux[0];
		
		modoDir = b = "";
		//revisar el numero
		nEntero = 0;
		if(!n.equals("")) {//entre si si tiene numero (n, r)
			if(!operando.contains("[")) {
				nEntero = Convertidor.aDecimal(sistema, n);
			}
			
			//System.out.println("El operando es: " + operando);
			if(operando.contains("[")) {
				//System.out.println("esta en if");
				
				b += "111";
				
				if(operando.contains("X")) {
					b += X;
				}
				else if(operando.contains("Y")) {
					b += Y;
				}
				else if(operando.contains("SP")) {
					b += SP;
				}
				else {//pc
					b += PC;
				}
				
				b += "011";
				
				numBinario = Integer.toBinaryString(nEntero);
				
				if(nEntero>0 && numBinario.length()==3) {
					numBinario = "0" + numBinario;
				}
				else if(nEntero>0 && numBinario.length()==1) {
					numBinario = "000" + numBinario;
				}
				
				//b += numBinario.substring(numBinario.length()-4, numBinario.length());
				
				modoDir = "IDX2";
			}
			else if(nEntero>=-16 && nEntero<=15) {//esta en el rango de la primera formula
				if(operando.contains("X")) {
					b = X;
				}
				else if(operando.contains("Y")) {
					b = Y;
				}
				else if(operando.contains("SP")) {
					b = SP;
				}
				else {//pc
					b = PC;
				}
				
				b += "0";//este cero es fijo
				b += nEntero>=0?"0":"1";
				
				numBinario = Integer.toBinaryString(nEntero);
				
				if(nEntero>0 && numBinario.length()==3) {
					numBinario = "0" + numBinario;
				}
				else if(nEntero>0 && numBinario.length()==1) {
					numBinario = "000" + numBinario;
				}
				
				b += numBinario.substring(numBinario.length()-4, numBinario.length());
				modoDir ="IDX"; //este es el modo ya que esta en la primera formula
				//System.out.println("TErmino primera formula: " + b);
			}
			else if(nEntero>=-32768 && nEntero<=65535){//esta en el rango de la segunda formula
				b = "111";
				if(operando.contains("X")) {
					b += X;
				}
				else if(operando.contains("Y")) {
					b += Y;
				}
				else if(operando.contains("SP")) {
					b += SP;
				}
				else {
					b += PC;
				}
				b += "0";
				
				if(nEntero>=-256 && nEntero<=255) {//para agregar z
					b += "0";
				}
				else {
					b += "1";
				}
				
				if(nEntero>0) {//para agregar s
					b += "0";
				}
				else {
					b += "1";
				}
				
				//saber si reservara 1 o dos bites
				if(nEntero>=-256 && nEntero<=255) {
					//reservar 1
					modoDir ="IDX1"; //este es el modo ya que esta en la segunda formula
				}
				else {
					//reservar 2
					modoDir ="IDX2"; //este es el modo ya que esta en la segunda formula
				}
			}
			/*
			else if (operando.contains("(")) {//esta en el rango de la tercera formula
				//System.out.println("esta en if");
			}*/
			//else... revisar las otras formulas
		}
		else {
			//no tiene n, solo r
			//...
		}
		
		//convertir numero binario a hexa;
		parte1Bin = b.substring(0, 4);
		parte2Bin = b.substring(4, 8);
		
		parte1Hex = Integer.toHexString(Integer.parseInt(parte1Bin, 2));
		parte2Hex = Integer.toHexString(Integer.parseInt(parte2Bin, 2));
		
		//codigoOperacion = adminMneminico.dameCodigoOperacion(operacion, modoDir);
		respuesta = new String[3];
		respuesta[0] = modoDir;
		respuesta[1] = parte1Hex+parte2Hex;
		return respuesta;
	}
	
	//para el modod de direccionamiento relativo
	public String calcularCodigoOperacionRelativo(int num1, int num2, String codigo) {
		//necesita el operando, en caso de ser EQU convertir el operando a hexa
		//y el contador actual del programa(siguiente linea)
		int resultado;
		String resultadoHexa;
		boolean esValido;
		
//		System.out.println("NUM 1: " + Integer.toHexString(num1));
//		System.out.println("NUM 2: " + Integer.toHexString(num2));
		
		resultado = num1 - num2;
//		System.out.println("Resultado: " + Integer.toHexString(resultado));
		/*
		 * FF = -1
		 * FE = -2
		 * 
		 * 7F = 
		 */
		
		//Para comprobar si esta en rango //Para BNE
		esValido = false;
		if(codigo.equals("26rr")) {
			if(resultado>=-255 && resultado<=127) {
				resultadoHexa = Integer.toHexString(resultado);
				if(resultado == 0) {
					codigo = codigo.substring(0,2) + "00";
				}
				else {
					codigo = codigo.substring(0,2) + resultadoHexa.substring(resultadoHexa.length()-2, resultadoHexa.length());
				}
			}
			else {
				codigo = codigo.substring(0,2) + "_FDR_";
			}
		}
		
		//Para comprobar si esta en rango //Paa LBNE
		if(codigo.equals("1826qqrr")) {
			if((resultado>=-32768 && resultado<=65535)) {
				resultadoHexa = Integer.toHexString(resultado);
				codigo = codigo.substring(0, 4) + resultadoHexa.substring(resultadoHexa.length()-4, resultadoHexa.length());
			}
			else {
				codigo = codigo.substring(0,4) + "_FDR_";
			}
		}
		
		//comprobar para IBNE
		if(codigo.equals("04")) {
			codigo = Integer.toHexString(resultado);
			if(codigo.length()==1) {
				codigo = "0" + codigo;
			}
			else if(codigo.length()>2) {
				codigo = codigo.substring(codigo.length()-2);
			}
		}
		
		return codigo;
	}
	
	//para los modos INH, IMM, DIR, EXT
	public String calcularCodigoOperacion(String codigo, int numeroDecimal) {
		String soloNumeros = codigo.replaceAll("[a-z]", "");
		String numHexa = Convertidor.aHexadecimal(numeroDecimal);
		int i, total;
		
		total = (codigo.length() - soloNumeros.length()) - numHexa.length();
		for(i=0; i<total; i++) {
			soloNumeros += "0";
		}
		soloNumeros += numHexa;
		
		return soloNumeros;
	}
	
	//saber si el operando esta en rango o es un _FDR_ (de cualquier modo)
	public boolean estaEnRango(String modoDir, String codigo, int numero) {
		//INCLUYENDO RANGOS NEGATIVOS Y POSITIVOS (DIR no acepta negativos)
//		int NUM_MAX_1_BYTE = 127;
//		int NUM_MIN_1_BYTE = 
//		int NUM_MAX_1_BYTE = 
//		int NUM_MIN_1_BYTE = 
//		int NUM_MAX_1_BYTE = 
//		int NUM_MIN_1_BYTE = 
		
		String parteFija;
		int totalBytes;
		
		parteFija = codigo.replaceAll("[a-z]", "");
		totalBytes = (codigo.length()-parteFija.length())/2;
		
		if(modoDir.equals("INH")) {
			//System.out.println("Analizando INH: " + numero);
			if(numero==0) {
				return true;
			}
		}
		else if(modoDir.equals("IMM")) {
			if(totalBytes==1) {//rango de 8 bits
				if(numero>=-256 && numero<=255) {
					return true;
				}
			}
			else {//rango de 16 bits
				if(numero>=-32768 && numero<=65535) {
					return true;
				}
			}
		}
		else if(modoDir.equals("DIR")) {
			if(numero>=0 && numero<=255) {
				return true;
			}
		}
		else if(modoDir.equals("EXT")) {
			if(numero>=-32768 && numero<=65535) {
				return true;
			}
		}
		else if(modoDir.equals("REL")) {
			if(totalBytes==1) {//rango de 8 bits
				if(numero>=-256 && numero<=255) {
					return true;
				}
			}
			else {//rango de 16 bits
				if(numero>=-32768 && numero<=65535) {
					return true;
				}
			}
		}
		else if(modoDir.equals("IDX")) {
			if(numero>=-16 && numero<=15) {
				return true;
			}
		}
		
		return false;
	}
	
	public void mostrarResultados(){
		int i, tam, indice;
		String linea, s="";
		
		System.out.println("En mostrar resultados");
		
		tam = lineas.size();
		for(i=0; i<tam; i++) {
			linea = lineasAnalizadas.get(i);
			if(linea.contains("ORG") || linea.contains("END") || linea.contains("EQU")) {
				System.out.println(linea);
			}
			else {
				indice = linea.indexOf("CO: ");
				linea = linea.substring(0, indice+4) + codigosOperacion.get(i);
				System.out.println(linea);
			}
			s+=linea+"\n";
		}
		//System.out.println("Guardara: \n" + s);
		adminArchivoResultados.guardarArchivo(s);
		
		tam = etiquetas.size();
		s = "";
		if(tam>0) {
			for(i=0; i<tam; i++) {
				if(!etiquetas.get(i).equals("")) {
					s += Integer.toHexString(valoresEtiquetas.get(i)) + " ---> " + etiquetas.get(i) + "\n";
				}
			}
			adminArchivoEtiquetas.guardarArchivo(s);
		}
	}
	
	public void crearTablaEtiquetas() {
		int i, tam;
		String sistema, operando, numero, contadorLinea;
		
		tam = etiquetas.size();
		for(i=0; i<tam; i++) {
			if(!etiquetas.get(i).equals("")) {
				if(lineas.get(i).contains("EQU")) {//convertir el operando a hexadecimal (guardarlo en decimal)
					adminLineaCodigo.separarLinea(lineas.get(i));
					operando = adminLineaCodigo.dameOperando();
					sistema = adminLineaCodigo.dameSistemaNumeracion(operando);
					numero = adminLineaCodigo.dameNumero(operando);
					valoresEtiquetas.set(i, Convertidor.aDecimal(sistema, numero));
					
				}
				else {//convertir el contador de programa de la linea en hexadecimal (guardarlo en decimal)
					contadorLinea = lineasAnalizadas.get(i).substring(0, 4);
					valoresEtiquetas.set(i, Convertidor.aDecimal("$", contadorLinea));
				}
			}
		}
	}
	
	private void cargarDatos() {
		adminArchivo.cargarArchivo();
	}
	
	public boolean esNumero(String cad) {
		try {
			Integer.parseInt(cad);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}




