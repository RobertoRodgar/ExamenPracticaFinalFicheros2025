package GestionVivero;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Main {
	static ArrayList<Empleado> ListaEmpleados = new ArrayList<>();
	public static void main(String[] args) {
		
		crearCarpetas();
		escribirEmpleado();
		
		//Crear funcion de crar el archivo plantas y baja
	}
	public static void crearCarpetas() {
		String[] carpetas = {"Plantas","Empleados","Empleados/Baja","Tickets","Devoluciones"};
		
		for(int i=0;i<carpetas.length; i++) {
			String carpetaTemp = carpetas[i];
			File carpeta = new File(carpetaTemp);
			if(!carpeta.exists()) {
				carpeta.mkdir();
			}
		}
	}
	public static void escribirEmpleado(){
		String ruta = "Empleados/empleado.dat";
		File rutaEmpleado = new File(ruta);
		if (!rutaEmpleado.exists()) {
			try {
				rutaEmpleado.createNewFile();
				anadirEmpleados();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(rutaEmpleado.exists() && rutaEmpleado.length() < 0) {
			anadirEmpleados();
		}else {
			System.out.println("El archivo empleados.dat cargado exitosamente");
		}
	}
	
	private static void anadirEmpleados() {
		try (FileOutputStream FicheroEscritura = new FileOutputStream("Empleados/empleado.dat");
				ObjectOutputStream escritura = new ObjectOutputStream(FicheroEscritura)) {

			Empleado empleado1 = new Empleado(1452, "Teresa", "asb123", CARGO.VENDEDOR);
			Empleado empleado2 = new Empleado(0234, "Miguel Angel", "123qwe", CARGO.VENDEDOR);
			Empleado empleado3 = new Empleado(7532, "Natalia", "xs21qw4", CARGO.GESTOR);

			ListaEmpleados.add(empleado1);
			ListaEmpleados.add(empleado2);
			ListaEmpleados.add(empleado3);

			escritura.writeObject(ListaEmpleados);

			System.out.println("Objetos escritos correctamente en empleado.dat");

		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	private static void bajaEmpleados() {
		String ruta = "Empleados/Baja/empleadosBaja.dat";
		File rutaEmpleado = new File(ruta);
		if(!rutaEmpleado.exists()) {
			try {
				rutaEmpleado.createNewFile();
				System.out.println("Archivo empleadosBaja.dat creado y cargado exitosamente en la carpeta Empleados/Baja/");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Archivo empleadosBaja.dat cargado exitosamente en la carpeta Empleados/Baja/");
		}
	}
	private static void bajaPlantas() {
		String ruta = "Plantas/plantasBaja.dat";
		File rutaPlanta = new File(ruta);
		if(!rutaPlanta.exists()) {
			try {
				rutaPlanta.createNewFile();
				System.out.println("Archivo plantasBaja.dat creado y cargado exitosamente en la carpeta Plantas/");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Archivo plantasBaja.dat cargado exitosamente en la carpeta Plantas/");
		}
		
		ruta = "Plantas/plantasBaja.xml";
		rutaPlanta = new File(ruta);
		if(!rutaPlanta.exists()) {
			try {
				rutaPlanta.createNewFile();
				System.out.println("Archivo plantasBaja.xml creado y cargado exitosamente en la carpetaPlantas/");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Archivo plantasBaja.xml cargado exitosamente en la carpeta Plantas/");
		}
	}
}
