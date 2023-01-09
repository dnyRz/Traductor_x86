package Final;

public class Mnemonico {
	String mnemonico;
	String modoDireccionamiento;
	int longitudInstruccion;
	String codigoOperacion;
	
	public Mnemonico(
			String mnemonico,
			String modoDireccionamiento,
			int longitudInstruccion,
			String codigoOperacion) 
	{
		if(!fijaMnemonico(mnemonico)) {
			this.mnemonico = "Error";
		}
		if(!fijaModoDireccionamiento(modoDireccionamiento)) {
			this.modoDireccionamiento = "Error";
		}
		if(!fijaLongitudInstruccion(longitudInstruccion)) {
			this.longitudInstruccion = -1;
		}
		if(!fijaCodigoOperacion(codigoOperacion)) {
			this.codigoOperacion = "Error";
		}
	}	
	public boolean fijaMnemonico(String m) {
		if(m.length()>0) {
			mnemonico = m;
			return true;
		}
		return false;
	}
	public boolean fijaModoDireccionamiento(String m) {
		if(m.length()>0) {
			modoDireccionamiento = m;
			return true;
		}
		return false;
	}
	public boolean fijaLongitudInstruccion(int l) {
		if(l>0) {
			longitudInstruccion = l;
			return true;
		}
		return false;
	}
	public boolean fijaCodigoOperacion(String c) {
		if(c.length()>0) {
			codigoOperacion = c;
			return true;
		}
		return false;
	}
	public String dameMnemonico() {
		return mnemonico;
	}
	public String dameModoDireccionamiento() {
		return modoDireccionamiento;
	}
	public int dameLongitudInstruccion() {
		return longitudInstruccion;
	}
	public String dameCodigoOperacion() {
		return codigoOperacion;
	}
}
