package GestionVivero;
	enum CARGO {VENDEDOR, GESTOR};
public class Empleado {
	private int identificacion;
	private String nombre;
	private String contraseña;
	private CARGO cargo;
	
	public Empleado(int identificacion, String nombre, String contraseña, CARGO cargo) {
		super();
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.cargo = cargo;
	}

	public int getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public CARGO getCargo() {
		return cargo;
	}

	public void setCargo(CARGO cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return "Empleado [identificacion=" + identificacion + ", nombre=" + nombre + ", contraseña=" + contraseña + ", cargo=" + cargo + "]";
	}
	
	
}
