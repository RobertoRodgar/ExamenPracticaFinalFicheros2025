package GestionVivero;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Main {
	static ArrayList<Empleado> ListaEmpleados = new ArrayList<>();
	public static void main(String[] args) {
		
		crearCarpetas();
		escribirEmpleados();
		escribirPlantas();
		//iniciarSesion();
		
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
	
	
	public static void escribirEmpleados(){
		String ruta = "Empleados/empleado.dat";
		File rutaEmpleado = new File(ruta);
		if (!rutaEmpleado.exists()) {
			try {
				rutaEmpleado.createNewFile();
				anadirEmpleados();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(rutaEmpleado.exists() && rutaEmpleado.length() <= 0) {
			anadirEmpleados();
		}else {
			System.out.println("El archivo empleados.dat se ha cargado exitosamente.");
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
	
	
	private static void escribirPlantas() {
		String ruta = "Plantas/plantas.xml";
		File rutaPlanta = new File(ruta);
		if(!rutaPlanta.exists()) {
			try {
				rutaPlanta.createNewFile();
				anadirPlantas();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(rutaPlanta.exists() && rutaPlanta.length() <= 0) {
			anadirPlantas();
		}else {
			System.out.println("El archivo plantas.xml se ha cargado exitosamente.");
		}
		
		ruta = "Plantas/plantas.dat";
		rutaPlanta = new File(ruta);
		if(!rutaPlanta.exists()) {
			try {
				rutaPlanta.createNewFile();
				//pasarXML_DAT();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(rutaPlanta.exists() && rutaPlanta.length() <= 0) {
			//pasarXML_DAT();
		}else {
			System.out.println("El archivo plantas.dat se ha cargado exitosamente.");
		}
	}
	
	
	private static void anadirPlantas() {
		File rutaPlantas = new File("Plantas/plantas.xml");
		try(FileWriter escPlantas = new FileWriter(rutaPlantas)){
			escPlantas.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			escPlantas.write("<plantas>");
			escPlantas.write("	<planta>");
			escPlantas.write("		<codigo>1</codigo>");
			escPlantas.write("		<nombre>Cactus</nombre>");
			escPlantas.write("		<foto>cactus.jpg</foto>");
			escPlantas.write("		<descripcion>Planta suculenta del desierto.</descripcion>");
			escPlantas.write("	</planta>");
			escPlantas.write("</plantas>");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static void pasarXML_DAT() {
		File rutaPlantasXML = new File("Plantas/plantas.xml");
		ArrayList<Planta> listaPlantas = new ArrayList<Planta>();
		
		try (FileInputStream fis = new FileInputStream(rutaPlantasXML)) {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("planta");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					int codigo = Integer.parseInt(eElement.getElementsByTagName("codigo").item(0).getTextContent());
					String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
					String foto = eElement.getElementsByTagName("foto").item(0).getTextContent();
					String descripcion = eElement.getElementsByTagName("descripcion").item(0).getTextContent();
					float precio = ThreadLocalRandom.current().nextFloat(1, 500);
					precio = Math.round(precio * 100f) / 100f;
					int stock = ThreadLocalRandom.current().nextInt(1, 501);
					
					Planta planta = new Planta(codigo, nombre, foto, descripcion, precio, stock);
					listaPlantas.add(planta);
				}
			}

			System.out.println("Datos del XML leídos correctamente. " + listaPlantas.size() + " planta(s) cargada(s).");

		} catch (Exception e) {
			System.out.println("Error al leer el XML plantas.xml ");
			e.printStackTrace();
			return;
		}
		try (FileOutputStream fos = new FileOutputStream("Plantas/plantas.dat");
				 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				
				oos.writeObject(listaPlantas);
				
				System.out.println("Planta escrito correctamente en Plantas/plantas.dat");
				
			} catch (IOException e) {
				System.out.println("Error al escribir en el archivo DAT plantas.dat");
				e.printStackTrace();
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
