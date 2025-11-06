package GestionVivero;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Main {
	private static ArrayList<Empleado> ListaEmpleados = new ArrayList<Empleado>();
	public static void main(String[] args) {
		
		crearCarpetas();
		escribirEmpleados();
		cargarEmpleados();
		escribirPlantas();
		iniciarSesion();
		
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
				System.out.println("El archivo empleados.dat se ha cargado exitosamente.");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(rutaEmpleado.exists() && rutaEmpleado.length() <= 0) {
			anadirEmpleados();
			System.out.println("El archivo empleados.dat se ha cargado exitosamente.");
		}else {
			System.out.println("El archivo empleados.dat se ha cargado exitosamente.");
		}
	}
	
	
	private static void anadirEmpleados() {
		try (FileOutputStream FicheroEscritura = new FileOutputStream("Empleados/empleado.dat");
				ObjectOutputStream escritura = new ObjectOutputStream(FicheroEscritura)) {

			Empleado empleado1 = new Empleado(1452, "Teresa", "asb123", CARGO.VENDEDOR);
			Empleado empleado2 = new Empleado(234, "Miguel Angel", "123qwe", CARGO.VENDEDOR);
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
	
	
	private static void cargarEmpleados() {
		File rutaEmpleados = new File("Empleados/empleado.dat");
		try(FileInputStream fis = new FileInputStream(rutaEmpleados)){
		ObjectInputStream ois = new ObjectInputStream(fis);
		ListaEmpleados = (ArrayList<Empleado>) ois.readObject();
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void escribirPlantas() {
		String ruta = "Plantas/plantas.xml";
		File rutaPlanta = new File(ruta);
		if(!rutaPlanta.exists()) {
			try {
				rutaPlanta.createNewFile();
				anadirPlantasPredeterminado();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(rutaPlanta.exists() && rutaPlanta.length() <= 0) {
			anadirPlantasPredeterminado();
		}else {
			System.out.println("El archivo plantas.xml se ha cargado exitosamente.");
		}
		
		ruta = "Plantas/plantas.dat";
		rutaPlanta = new File(ruta);
		if(!rutaPlanta.exists() || rutaPlanta.length() <= 0) {
			try {
				rutaPlanta.createNewFile();
				pasarXML_DAT();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("El archivo plantas.dat se ha cargado exitosamente.");
		}
	}
	
	
	private static void anadirPlantasPredeterminado() {
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
	
	
	private static void darAltaPlantas() {
		Scanner sc = new Scanner(System.in);
		int cantidad = leerEnteroSeguro(sc, "¿Cuántas plantas deseas dar de alta? ");
		for (int i = 0; i < cantidad; i++) {
			int codigo = leerEnteroSeguro(sc, "Código de la planta " + (i + 1) + ": ");
		    System.out.print("Introduce el nombre: ");
		    String nombre = sc.nextLine();
		    System.out.print("Introduce la foto: ");
		    String foto = sc.nextLine();
		    System.out.print("Introduce una descripción: ");
		    String desc = sc.nextLine();
			File rutaPlantas = new File("Plantas/plantas.xml");
			try (FileWriter escPlantas = new FileWriter(rutaPlantas)) {
				escPlantas.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				escPlantas.write("<plantas>");
				escPlantas.write("	<planta>");
				escPlantas.write("		<codigo>"+codigo+"</codigo>");
				escPlantas.write("		<nombre>"+nombre+"</nombre>");
				escPlantas.write("		<foto>"+foto+"</foto>");
				escPlantas.write("		<descripcion>"+desc+"</descripcion>");
				escPlantas.write("	</planta>");
				escPlantas.write("</plantas>");
				pasarXML_DAT();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sc.close();
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
					
					float precio = ThreadLocalRandom.current().nextFloat(1, 500);
					precio = Math.round(precio * 100f) / 100f;
					int stock = ThreadLocalRandom.current().nextInt(1, 501);
					if(stock == 0) {
						//bajaPlantas();
					}else {
						Planta planta = new Planta(codigo, precio, stock);
						listaPlantas.add(planta);
					}
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
	
	
	private static void iniciarSesion() {
		Scanner sc = new Scanner(System.in);
		mostrarEmpleados();
		
		int id = leerEnteroSeguro(sc, "Introduce tu identificación: ");
		System.out.println("Introduce tu contraseña");
		sc.nextLine();
		String contraseña = sc.nextLine();
		boolean encontrado = false;
		for(Empleado e: ListaEmpleados) {
			if(e.getIdentificacion() == id && e.getContraseña().equals(contraseña)) {
				System.out.println("Bienvenido/a, " + e.getNombre());
				encontrado = true;
				if(e.getCargo() == CARGO.GESTOR) {
					//menuGestor();
				}else {
					//menuVendedor();
				}
				break;
			}
		}
		
		if(!encontrado) {
			System.out.println("No se ha encontrado ningún empleado con esa identificación y contraseña.");
		}
		sc.close();
	}
	
	
	public static void mostrarEmpleados() {
		for(Empleado e: ListaEmpleados) {
			System.out.println(e.toString());
		}
	}
	
	
	public static void menuVendedor() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("1- Visualizar el catálogo.");
		System.out.println("2- Generar una venta.");
		System.out.println("3- Buscar un ticket.");
		System.out.println("4- Realizar una devolución.");
		System.out.println("5- Salir.");
		
		int opcion = leerEnteroSeguro(sc, "Selecciona una opción: ");
		switch(opcion) {
			case 1:
				//visualizarCatalogo();
				break;
			case 2:
				//generarVenta();
				break;
			case 3:
				//buscarTicket();
				break;
			case 4:
				//hacerDevolucion();
				break;
			case 5:
				System.out.println("Has decidido salir.");
				break;
			default:
				System.out.println("No es una opción válida. Saliendo...");
		}
		
	}
	
	
	public static void menuGestor() {
		
	}
	
	
	public static void visualizarCatalogo() {
		File rutaPlantasDAT = new File("Plantas/plantas.dat");
		File rutaPlantasXML = new File("Plantas/plantas.xml");

	    if (!rutaPlantasXML.exists() || !rutaPlantasDAT.exists()) {
	        System.out.println("No se encuentran los archivos necesarios (plantas.xml o plantas.dat).");
	        return;
	    }

	    try {
	        ArrayList<Planta> listaPlantasDAT = new ArrayList<>();
	        try (FileInputStream fis = new FileInputStream(rutaPlantasDAT);
	             ObjectInputStream ois = new ObjectInputStream(fis)) {
	            listaPlantasDAT = (ArrayList<Planta>) ois.readObject();
	        }

	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(rutaPlantasXML);
	        doc.getDocumentElement().normalize();

	        NodeList nList = doc.getElementsByTagName("planta");

	        System.out.println("LISTADO DE PLANTAS DISPONIBLES");

	        for (int i = 0; i < nList.getLength(); i++) {
	            Node nNode = nList.item(i);

	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;

	                int codigo = Integer.parseInt(eElement.getElementsByTagName("codigo").item(0).getTextContent());
	                String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
	                String foto = eElement.getElementsByTagName("foto").item(0).getTextContent();
	                String descripcion = eElement.getElementsByTagName("descripcion").item(0).getTextContent();

	                Planta plantaDat = null;
	                for (Planta p : listaPlantasDAT) {
	                    if (p.getCodigo() == codigo) {
	                        plantaDat = p;
	                        break;
	                    }
	                }

	                if (plantaDat != null) {
	                    System.out.println("Código: " + codigo);
	                    System.out.println("Nombre: " + nombre);
	                    System.out.println("Foto: " + foto);
	                    System.out.println("Descripción: " + descripcion);
	                    System.out.println("Precio: " + plantaDat.getPrecio() + " €");
	                    System.out.println("Stock: " + plantaDat.getStock());
	                    System.out.println("-------------------------------------");
	                } else {
	                    System.out.println("Planta con código " + codigo + " no tiene datos de precio/stock en el .dat");
	                }
	            }
	        }

	    } catch (Exception e) {
	        System.out.println("Error al visualizar las plantas:");
	        e.printStackTrace();
	    }
	}
	private static int leerEnteroSeguro(Scanner sc, String mensaje) {
	    int numero;
	    while (true) {
	        System.out.println(mensaje);
	        if (sc.hasNextInt()) {
	            numero = sc.nextInt();
	            sc.nextLine();
	            break;
	        } else {
	            System.out.println("Error: por favor, introduce un número válido.");
	            sc.nextLine();
	        }
	    }
	    return numero;
	}

	
	private static float leerFloatSeguro(Scanner sc, String mensaje) {
	    float numero;
	    while (true) {
	        System.out.println(mensaje);
	        if (sc.hasNextFloat()) {
	            numero = sc.nextFloat();
	            sc.nextLine();
	            break;
	        } else {
	            System.out.println("Error: por favor, introduce un número decimal válido.");
	            sc.nextLine();
	        }
	    }
	    return numero;
	}
}
