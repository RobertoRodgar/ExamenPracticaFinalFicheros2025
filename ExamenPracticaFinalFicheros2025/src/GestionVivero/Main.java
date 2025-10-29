package GestionVivero;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Main {

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
			ArrayList<Empleado> ListaEmpleados = new ArrayList<>();

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
		}else {
			System.out.println("El archivo empleados.dat cargado exitosamente");
		}
		//Crear la baja
	}

}
