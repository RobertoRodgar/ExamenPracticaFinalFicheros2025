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
	private static ArrayList<Planta> ListaPlantas = new ArrayList<Planta>();
	private static ArrayList<Empleado> ListaEmpleadosBaja = new ArrayList<Empleado>();
	private static ArrayList<Planta> ListaPlantasBaja = new ArrayList<Planta>();
	public static void main(String[] args) {
		
		crearCarpetas();
		escribirEmpleados();
		cargarEmpleados();
		cargarEmpleadosBaja();
		escribirPlantas();
		cargarPlantas();
		cargarPlantasBaja();
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
		if (rutaEmpleados.exists() && rutaEmpleados.length() > 0) {
			try(FileInputStream fis = new FileInputStream(rutaEmpleados)){
				ObjectInputStream ois = new ObjectInputStream(fis);
				ListaEmpleados = (ArrayList<Empleado>) ois.readObject();
			}catch(IOException | ClassNotFoundException e) {
				System.err.println("Error al cargar empleados.dat. Inicializando lista vacía.");
				ListaEmpleados = new ArrayList<>();
			}
		} else {
			ListaEmpleados = new ArrayList<>();
		}
	}
	
	private static void cargarPlantas() {
		File rutaPlantas = new File("Plantas/plantas.dat");
		if (rutaPlantas.exists() && rutaPlantas.length() > 0) {
			try(FileInputStream fis = new FileInputStream(rutaPlantas)){
				ObjectInputStream ois = new ObjectInputStream(fis);
				ListaPlantas = (ArrayList<Planta>) ois.readObject();
			}catch(IOException | ClassNotFoundException e) {
				System.err.println("Error al cargar plantas.dat. Inicializando lista vacía.");
				ListaPlantas = new ArrayList<>();
			}
		} else {
			ListaPlantas = new ArrayList<>();
		}
	}
	
	private static void cargarEmpleadosBaja() {
		File rutaEmpleadosBaja = new File("Empleados/Baja/empleadosBaja.dat"); 
		if (rutaEmpleadosBaja.exists() && rutaEmpleadosBaja.length() > 0) {
			try(FileInputStream fis = new FileInputStream(rutaEmpleadosBaja)){
				ObjectInputStream ois = new ObjectInputStream(fis);
				ListaEmpleadosBaja = (ArrayList<Empleado>) ois.readObject();
			}catch(IOException | ClassNotFoundException e) {
				System.err.println("Error al cargar empleadosBaja.dat. Inicializando lista vacía.");
				ListaEmpleadosBaja = new ArrayList<>();
			}
		} else {
			ListaEmpleadosBaja = new ArrayList<>();
		}
	}
	
	private static void cargarPlantasBaja() {
		File rutaPlantasBaja = new File("Plantas/plantasBaja.dat");
		if (rutaPlantasBaja.exists() && rutaPlantasBaja.length() > 0) {
			try(FileInputStream fis = new FileInputStream(rutaPlantasBaja)){
				ObjectInputStream ois = new ObjectInputStream(fis);
				ListaPlantasBaja = (ArrayList<Planta>) ois.readObject();
			}catch(IOException | ClassNotFoundException e) {
				System.err.println("Error al cargar plantasBaja.dat. Inicializando lista vacía.");
				ListaPlantasBaja = new ArrayList<>();
			}
		} else {
			ListaPlantasBaja = new ArrayList<>();
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
	
	
	public static void darAltaPlanta() {
	    Scanner sc = new Scanner(System.in);
	    int codigo = obtenerSiguienteCodigo();
	    
	    System.out.println("Estás dando de alta a una nueva planta con código: " + codigo );
	    
	    System.out.print("Introduce el nombre: ");
	    String nombre = sc.nextLine();
	    
	    System.out.print("Introduce la foto (ej: flor.jpg): ");
	    String foto = sc.nextLine();
	    
	    System.out.print("Introduce una descripción: ");
	    String descripcion = sc.nextLine();

	    float precio = leerFloatSeguro(sc, "Introduce el precio: ");
	    int stock = leerEnteroSeguro(sc, "Introduce el stock inicial: ");

	    File rutaPlantasXML = new File("Plantas/plantas.xml");
	    File rutaTemporal = new File("Plantas/temp.xml");
	    
	    try (BufferedReader lector = new BufferedReader(new FileReader(rutaPlantasXML));
	         FileWriter fwTemp = new FileWriter(rutaTemporal)) {

	        String linea;
	        
	        while ((linea = lector.readLine()) != null) {
	            if (!linea.trim().equals("</plantas>")) {
	                fwTemp.write(linea + "\n");
	            }
	        }
	        fwTemp.write("	<planta>\n");
	        fwTemp.write("		<codigo>" + codigo + "</codigo>\n");
	        fwTemp.write("		<nombre>" + nombre + "</nombre>\n");
	        fwTemp.write("		<foto>" + foto + "</foto>\n");
	        fwTemp.write("		<descripcion>" + descripcion + "</descripcion>\n");
	        fwTemp.write("	</planta>\n");
	        fwTemp.write("</plantas>");   
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;
	    }

	    if (rutaPlantasXML.delete()) {
	        rutaTemporal.renameTo(rutaPlantasXML);
	        System.out.println("Planta " + nombre + " añadida correctamente al archivo plantas.xml.");
	    } else {
	        System.out.println("Error al agregar la nueva planta.");
	        return;
	    }

	    Planta nuevaPlanta = new Planta(codigo, precio, stock);
	    ListaPlantas.add(nuevaPlanta);

	    guardarPlantas();
	    
	    System.out.println("Planta con nombre: " + nombre + ", código: " + codigo + ", añadida correctamnete al .dat.");
	}
	
	
	public static void darAltaEmpleado() {
		Scanner sc = new Scanner(System.in);
		
		int codigo; 
		while(true) {
	        codigo = leerEnteroSeguro(sc, "Introduce el código del nuevo empleado:");
	        
	        if (buscarEmpleado(codigo) != null || buscarEmpleadoBaja(codigo) != null) {
	            System.out.println("El código " + codigo + " ya está siendo utilizado. Por favor, introduce un código que no se repita.");
	        } else {
	            break;
	        }
	    }
		
		System.out.println("Introduce el nombre del empleado;");
		String nombre = sc.nextLine();
		
		System.out.println("Introcude la contraseña del nuevo empleado:");
		String contraseña = sc.nextLine();
		
		CARGO cargo = null;
		while(true) {
			int opcion = leerEnteroSeguro(sc, "¿Qué cargo tiene el nuevo empleado?(1 = Vendedor / 2 = Gestor)");
			if (opcion == 1) {
				cargo = CARGO.VENDEDOR;
				break;
			}else if(opcion == 2) {
				cargo = CARGO.GESTOR;
				break;
			}else {
				System.out.println("Ese no es un número válido, ingresa uno nuevo.");
			}
		}
		
		Empleado empleado = new Empleado(codigo, nombre, contraseña, cargo);
		ListaEmpleados.add(empleado);
		
		guardarEmpleados();
		
		System.out.println("Empleado: " + nombre + " añadido correctamente a la base de datos.");
	}
	
	
	private static void pasarXML_DAT() {
		File rutaPlantasXML = new File("Plantas/plantas.xml");
		
		ListaPlantas.clear();
		
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
						ListaPlantas.add(planta);
					}
				}
			}

			System.out.println("Datos del XML leídos correctamente. " + ListaPlantas.size() + " planta(s) cargada(s).");

		} catch (Exception e) {
			System.out.println("Error al leer el XML plantas.xml ");
			e.printStackTrace();
			return;
		}
		try (FileOutputStream fos = new FileOutputStream("Plantas/plantas.dat");
				 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				
				oos.writeObject(ListaPlantas);
				
				System.out.println("Planta escrito correctamente en Plantas/plantas.dat");
				
			} catch (IOException e) {
				System.out.println("Error al escribir en el archivo DAT plantas.dat");
				e.printStackTrace();
			}
	}
	
	
	private static void darBajaEmpleado() {
		Scanner sc = new Scanner(System.in);
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
		
		int codigoBuscar;
	    Empleado empleado = null;
	    while (true) {
	        codigoBuscar = leerEnteroSeguro(sc, "Introduce el código del empleado a buscar");
	        empleado = buscarEmpleado(codigoBuscar); 
	        if (empleado != null) {
	            break;
	        } else {
	            System.out.println("No se ha encontrado ningún empleado con dicho código.");
	        }
	    }
	    
	    
	    ListaEmpleados.remove(empleado);
	    ListaEmpleadosBaja.add(empleado);
	    guardarEmpleados();
	    guardarEmpleadosBaja();
	    
	    System.out.println("Empleado dado de baja correctamente y guardado en Empleados/Baja/empleadoBaja.dat");
	}
	
	
	private static void darBajaPlanta() {
	    Scanner sc = new Scanner(System.in);
	    String rutaDAT = "Plantas/plantasBaja.dat";
	    String rutaXML = "Plantas/plantasBaja.xml";
	    File rutaPlantaDAT = new File(rutaDAT);
	    File rutaPlantaXML = new File(rutaXML);
	    
	    if (!rutaPlantaDAT.exists()) {
	        try {
	            rutaPlantaDAT.createNewFile();
	            System.out.println("Archivo plantasBaja.dat creado y cargado exitosamente en la carpeta Plantas/");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Archivo plantasBaja.dat cargado exitosamente en la carpeta Plantas/");
	    }

	    if (!rutaPlantaXML.exists()) {
	        try {
	            rutaPlantaXML.createNewFile();
	            System.out.println("Archivo plantasBaja.xml creado y cargado exitosamente en la carpeta Plantas/");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Archivo plantasBaja.xml cargado exitosamente en la carpeta Plantas/");
	    }

	    int codigoBuscar;
	    Planta planta = null;
	    while (true) {
	        codigoBuscar = leerEnteroSeguro(sc, "Introduce el código de la planta a buscar");
	        planta = buscarPlanta(codigoBuscar); 
	        if (planta != null) {
	            break;
	        } else {
	            System.out.println("No se ha encontrado ninguna planta con dicho código.");
	        }
	    }
	    Planta plantaCopia = new Planta(planta.getCodigo(), planta.getPrecio(), planta.getStock());
	    ListaPlantasBaja.add(plantaCopia);
	    guardarPlantasBaja();
	    planta.setPrecio(0);
	    planta.setStock(0);
	    guardarPlantas();


	    try {
	        ArrayList<String> lineas = new ArrayList<>();
	        if (rutaPlantaXML.exists()) {
	            try (BufferedReader br = new BufferedReader(new FileReader(rutaPlantaXML))) {
	                String linea;
	                while ((linea = br.readLine()) != null) {
	                    lineas.add(linea);
	                }
	            }
	            if (!lineas.isEmpty() && lineas.get(lineas.size() - 1).trim().equals("</plantas>")) {
	                lineas.remove(lineas.size() - 1);
	            }
	        } else {
	            lineas.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	            lineas.add("<plantas>");
	        }

	        try (FileWriter fw = new FileWriter(rutaPlantaXML)) {
	            for (String linea : lineas) {
	                fw.write(linea + "\n");
	            }

	            fw.write("	<planta>\n");
	            fw.write("		<codigo>" + planta.getCodigo() + "</codigo>\n");
	            fw.write("		<nombre>" + planta.getNombre() + "</nombre>\n"); 
	            fw.write("		<foto>" + planta.getFoto() + "</foto>\n");
	            fw.write("		<descripcion>" + planta.getDescripcion() + "</descripcion>\n");
	            fw.write("	</planta>\n");
	            fw.write("</plantas>");
	        }      
	        System.out.println("Archivo plantasBaja.xml actualizado exitosamente con la nueva baja.");
	    } catch (IOException e) {
	        System.err.println("Error al procesar el archivo XML de baja.");
	        e.printStackTrace();
	    }
	}
	
	
	private static void iniciarSesion() {
		Scanner sc = new Scanner(System.in);
		mostrarEmpleados();
		
		int id = leerEnteroSeguro(sc, "Introduce tu identificación: ");
		System.out.println("Introduce tu contraseña");
		String contraseña = sc.nextLine();
		boolean encontrado = false;
		for(Empleado e: ListaEmpleados) {
			if(e.getIdentificacion() == id && e.getContraseña().equals(contraseña)) {
				System.out.println("Bienvenido/a, " + e.getNombre());
				encontrado = true;
				if(e.getCargo() == CARGO.GESTOR) {
					menuGestor();
				}else {
					menuVendedor(id);
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
	
	
	public static void menuVendedor(int id) {
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		do {
		System.out.println("1- Visualizar el catálogo.");
		System.out.println("2- Generar una venta.");
		System.out.println("3- Realizar una devolución.");
		System.out.println("4- Salir.");
		
			opcion = leerEnteroSeguro(sc, "Selecciona una opción: ");
			switch (opcion) {
			case 1:
				visualizarCatalogo();
				break;
			case 2:
				generarVenta(id);
				break;
			case 3:
				hacerDevolucion();
				break;
			case 4:
				System.out.println("Has decidido salir.");
				break;
			default:
				System.out.println("No es una opción válida. Saliendo...");
			}
		} while (opcion != 4);
	}
	
	
	public static void menuGestor() {
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		do {
		System.out.println("1- Dar de alta planta.");
		System.out.println("2- Dar de baja planta.");
		System.out.println("3- Rescatar planta.");
		System.out.println("4- Dar de alta empleado.");
		System.out.println("5- Dar de baja empleado.");
		System.out.println("6- Rescatar empleado.");
		System.out.println("7- Total entre los tickets.");
		System.out.println("8- Planta mas vendida.");
		System.out.println("9- Salir");
		
			opcion = leerEnteroSeguro(sc, "Selecciona una opción: ");
			switch (opcion) {
			case 1:
				darAltaPlanta();
				break;
			case 2:
				darBajaPlanta();
				break;
			case 3:
				rescatarPlanta();
				break;
			case 4:
				darAltaEmpleado();
				break;
			case 5:
				darBajaEmpleado();
				break;
			case 6:
				rescatarEmpleado();
				break;
			case 7:
				//totalTickets();
				break;
			case 8:
				//masVendida();
				break;
			case 9:
				System.out.println("Has decidido salir. Saliendo...");
				break;
			default:
				System.out.println("No es una opción válida.");
			}
		} while (opcion != 9);
	}
	
	
	public static void visualizarCatalogo() {
		File rutaPlantasXML = new File("Plantas/plantas.xml");

	    if (!rutaPlantasXML.exists() || ListaPlantas.isEmpty()) {
	        System.out.println("No se encuentran el archivo necesario plantas.xml o no hay datos cargados de stock o precio (el ArrayList ListaPlantas está vacío).");
	        return;
	    }

	    try {
	        ArrayList<Planta> listaPlantasDAT = ListaPlantas;
	        

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
	                
	                if(buscarPlantaBaja(codigo) != null) {
	                	continue;
	                }
	                
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
	
	
	private static void generarVenta(int id) {
		Scanner sc = new Scanner(System.in);
		Empleado vendedor = null;
		Planta planta = null;
		int codigoPlanta;
		float precio;
		int cantidad;
		float total = 0;
		
		for(Empleado e : ListaEmpleados) {
			if(e.getIdentificacion() == id) {
				vendedor = e;
				break;
			}
		}
		
		System.out.println("¿Quieres visualizar el catálogo?(si / no)");
		String siNo = sc.nextLine();
		while (true) {
			if (siNo.toLowerCase().equals("si")) {
				visualizarCatalogo();
				break;
			} else if (siNo.toLowerCase().equals("no")) {
				System.out.println("Se continuará la ejecución del programa.");
				break;
			} else {
				System.out.println("Esa no es una opcion válida");
				siNo = sc.nextLine();
			}
		}
		
		int numTicket =  crearTicket();
		String rutaTicket = "Tickets/" + numTicket + ".txt";
		File ticket = new File(rutaTicket);
		
		try (FileWriter fw = new FileWriter(ticket)){
			fw.write("Número Ticket: " + numTicket + "\n");
			fw.write("——————————————//—————————————————————————————————\n");
			fw.write("Empleado que ha atendido: " + id + "\n");
			fw.write("Nombre del empleado: " + vendedor.getNombre() + "\n\n");
			fw.write("CodigoProducto\tCantidad\tPrecioUnitario\n");
			
			while(true) {
				int opcion = leerEnteroSeguro(sc,"¿Cuántas plantas quieres añadir? (Introduzca 0 para salir)");
				if(opcion > 0) {
					for(int i = 0; i < opcion; i++) {
						while(true) {
							codigoPlanta = leerEnteroSeguro(sc, "Introduce el código de la planta que quieras comprar en " + (i + 1) + "ª posición;");
							planta = buscarPlanta(codigoPlanta);
							
							if(planta != null) {
								precio = planta.getPrecio();
								while(true) {
									cantidad = leerEnteroSeguro(sc, "¿Cuánto quieres comprar ?");
									if(cantidad > planta.getStock()) {
										System.out.println("Estás intentando comprar más de lo que hay actualmente en el stock.");
									}else {
										break;
									}
								}
								total += precio*cantidad;
								fw.write(planta.getCodigo() + "\t\t" + cantidad + "\t\t" + String.format("%.2f", planta.getPrecio()) + "\n");
								planta.setStock((planta.getStock() - cantidad));
								break;
							}else {
								System.out.println("El código de planta introducido no es válido. Inténtalo de nuevo.");
							}
						}
					}
					break;
				}else if(opcion == 0) {
					System.out.println("Saliendo...");
					break;
				}else {
					System.out.println("No es una opción válida.");
				}
			}
			fw.write("\n——————————————//—————————————————————————————————\n");
			fw.write("Total: " + String.format("%.2f", total) + " €");
			guardarPlantas();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static int crearTicket() {
		int numeroTicket;
		String ticket = "Tickets/";
		File rutaTickets = new File(ticket);
		File rutaDevoluciones = new File("Devoluciones/");
		numeroTicket = (rutaTickets.listFiles().length + rutaDevoluciones.listFiles().length) + 1;

		ticket = "Tickets/" + numeroTicket + ".txt";
		rutaTickets = new File(ticket);
		
		try {
			rutaTickets.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numeroTicket;
	}
	
	
	public static void hacerDevolucion() {
	    Scanner sc = new Scanner(System.in);
	    int numeroTicket;
	    File rutaTicketOg;
	    
	    while(true) {
	        numeroTicket = leerEnteroSeguro(sc, "¿De qué ticket quieres hacer la devolución?");
	        rutaTicketOg = new File("Tickets/" + numeroTicket + ".txt");
	        if(!rutaTicketOg.exists()) {
	            System.out.println("El número de ticket introducido no existe. Introduce un nuevo número.");
	        }else {
	            break;
	        }
	    }
	    
	    File rutaTicketFinal = new File("Devoluciones/" + numeroTicket + ".txt");
	    ArrayList<String[]> detallesDevueltos = new ArrayList<>(); 
	    
	    try (BufferedReader lector = new BufferedReader(new FileReader(rutaTicketOg));
	         FileWriter fw = new FileWriter(rutaTicketFinal)) {
	        
	        fw.write("---DEVOLUCIÓN---\n");
	        
	        String linea;
	        
	        while ((linea = lector.readLine()) != null) {
	            
	            if (linea.contains("\t") && !linea.startsWith("CodigoProducto") && !linea.startsWith("—")) {
	                
	                String[] partes = linea.split("\t"); 
	                
	                ArrayList<String> partesLimpia = new ArrayList<>();
	                for (String parte : partes) {
	                    if (!parte.trim().isEmpty()) {
	                        partesLimpia.add(parte.trim());
	                    }
	                }
	                
	                if (partesLimpia.size() >= 3) { 
	                    detallesDevueltos.add(partesLimpia.toArray(new String[0]));
	                }
	            }
	            
	            fw.write(linea + "\n"); 
	        }
	        
	        System.out.println("Devolución correcta del ticket " + numeroTicket + ".txt, se ha modificado y movido a la carpeta Devoluciones/.");
	        
	    } catch (IOException e) {
	        System.err.println("Error al leer o escribir el ticket durante la devolución.");
	        e.printStackTrace();
	        return;
	    }
	    
	    rutaTicketOg.delete();
	    
	    if (!detallesDevueltos.isEmpty()) {
	        for (String[] detalle : detallesDevueltos) {
	            try {
	                int codigo = Integer.parseInt(detalle[0]); 
	                int cantidad = Integer.parseInt(detalle[1]); 
	                
	                Planta planta = buscarPlanta(codigo); 
	                
	                if (planta != null) {
	                    planta.setStock(planta.getStock() + cantidad);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        guardarPlantas();
	    }
	}
	
	
	public static void rescatarPlanta() {
		Scanner sc = new Scanner(System.in);
		String rutaXMLBaja = "Plantas/plantasBaja.xml";
		File rutaPlantasXMLBaja = new File(rutaXMLBaja);
		
		System.out.println("Las plantas actualmente de baja son: ");
		verPlantasBaja();
		
		int codigoBuscar;
	    Planta plantaBaja = null;
	    
	    while (true) {
	    	
	        codigoBuscar = leerEnteroSeguro(sc, "Introduce el código de la planta a buscar en el archivo de bajas.");
	        plantaBaja = buscarPlantaBaja(codigoBuscar); 
	        if (plantaBaja != null) {
	            break;
	        } else {
	            System.out.println("No se ha encontrado ninguna planta con dicho código.");
	        }
	    }
	    
	    Planta plantaActiva = buscarPlanta(codigoBuscar); 
	    
	    if (plantaActiva == null) {
	        System.out.println("Error.La planta existe en el registro de baja pero fue eliminada del catálogo. No se puede restaurar.");
	        return;
	    }
	    plantaActiva.setStock(plantaBaja.getStock()); 
	    plantaActiva.setPrecio(plantaBaja.getPrecio());
	    
	    ListaPlantasBaja.remove(plantaBaja);
	    
	    guardarPlantas();
	    guardarPlantasBaja();
	    
	    try {
	        if (rutaPlantasXMLBaja.exists()) {
	            ArrayList<String> lineas = new ArrayList<>();
	            String codigoStr = String.valueOf(codigoBuscar);
	            boolean omitirLinea = false;
	            
	            try (BufferedReader br = new BufferedReader(new FileReader(rutaPlantasXMLBaja))) {
	                String linea;
	                
	                while ((linea = br.readLine()) != null) {
	                    String lineaTrim = linea.trim();

	                    if (lineaTrim.equals("<codigo>" + codigoStr + "</codigo>")) {
	                        omitirLinea = true; 
	                        if (!lineas.isEmpty() && lineas.get(lineas.size() - 1).trim().equals("<planta>")) {
	                             lineas.remove(lineas.size() - 1);
	                        }
	                    }
	                    if (omitirLinea) {
	                        if (lineaTrim.equals("</planta>")) {
	                            omitirLinea = false; 
	                        }
	                        continue; 
	                    }
	                    lineas.add(linea); 
	                }
	            }

	            try (FileWriter fw = new FileWriter(rutaPlantasXMLBaja)) {
	                for (String linea : lineas) {
	                    fw.write(linea + "\n");
	                }
	                if (!lineas.isEmpty() && !lineas.get(lineas.size() - 1).trim().equals("</plantas>")) {
	                     fw.write("</plantas>");
	                }
	            }
	            System.out.println("Registro de XML de baja eliminado.");
	        } else {
	            System.out.println("El archivo XML de bajas no existe.");
	        }
	    } catch (Exception e) {
	        System.err.println("Error al modificar o reescribir el archivo XML de bajas: " + e.getMessage());
	    }
	    System.out.println("La planta con código: " + plantaActiva.getCodigo() + " rescatada con éxito.");
	}
	
	
	public static void rescatarEmpleado() {
		Scanner sc = new Scanner(System.in);
		int codigoBuscar;
	    Empleado empleado = null;
	    
	    while (true) {
	        codigoBuscar = leerEnteroSeguro(sc, "Introduce el código del empleado a buscar");
	        empleado = buscarEmpleadoBaja(codigoBuscar); 
	        if (empleado != null) {
	            break;
	        } else {
	            System.out.println("No se ha encontrado ningún empelado con dicho código.");
	        }
	    }
	    
	    ListaEmpleados.add(empleado);
	    ListaEmpleadosBaja.remove(empleado);
	    guardarEmpleados();
	    guardarEmpleadosBaja();
	}
	
	
	public static void totalTickets() {
		File carpetaTickets = new File("Tickets/"); 
	    File[] listaTickets = carpetaTickets.listFiles();

	    if (listaTickets == null || listaTickets.length == 0) {
	        System.out.println("No hay tickets de venta activos para procesar en la carpeta Tickets/.");
	        return;
	    }

	    double totalRecaudado = 0.0;
	    int ticketsProcesados = 0;

	    for (File ticket : listaTickets) {
	        if (ticket.isFile() && ticket.getName().toLowerCase().endsWith(".txt")) { 
	            try (BufferedReader br = new BufferedReader(new FileReader(ticket))) {
	                String linea;
	                
	                while ((linea = br.readLine()) != null) {
	                    if (linea.trim().startsWith("Total:")) {
	                        String totalStr = linea.replaceAll("Total:\\s*", "").replaceAll("€.*", "").trim();

	                        try {
	                            double totalTicket = Double.parseDouble(totalStr.replace(',', '.'));
	                            totalRecaudado += totalTicket; 
	                            ticketsProcesados++;
	                        } catch (NumberFormatException e) {
	                        }
	                        break;
	                    }
	                }
	            } catch (IOException e) {
	                System.err.println("Error al leer el archivo " + ticket.getName());
	            }
	        }
	    }
	    System.out.println("\n===== RESUMEN DE LOS TICKETS =====");
	    System.out.println("Total de tickets de venta procesados: " + ticketsProcesados);
	    System.out.println(String.format("Total recaudado (Ventas Activas): %.2f €", totalRecaudado));
	}
	
	
	public static void masVendido() {
		
	}
	
	
	private static int obtenerSiguienteCodigo() {
	    int maxCodigo = 0;
	    
	    for (Planta p : ListaPlantas) {
	        if (p.getCodigo() > maxCodigo) {
	            maxCodigo = p.getCodigo();
	        }
	    }
	    return maxCodigo + 1;
	}
	
	
	private static void guardarEmpleados() {
	    try (FileOutputStream fos = new FileOutputStream("Empleados/empleado.dat");
	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

	        oos.writeObject(ListaEmpleados);
	        
	        System.out.println("Datos de empleados guardados exitosamente en empleados.dat.");
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	private static void guardarPlantas() {
	    try (FileOutputStream fos = new FileOutputStream("Plantas/plantas.dat");
	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

	        oos.writeObject(ListaPlantas);
	        
	        System.out.println("Datos de plantas guardados exitosamente en plantas.dat.");
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	private static void guardarEmpleadosBaja() {
	    try (FileOutputStream fos = new FileOutputStream("Empleados/Baja/empleadosBaja.dat");
	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

	        oos.writeObject(ListaEmpleadosBaja);
	        
	        System.out.println("Datos de empleados guardados exitosamente en Empleados/Baja/empleadosBaja.dat.");
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	private static void guardarPlantasBaja() {
	    try (FileOutputStream fos = new FileOutputStream("Plantas/plantasBaja.dat");
	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

	        oos.writeObject(ListaPlantasBaja);
	        
	        System.out.println("Datos de plantas guardados exitosamente en Plantas/plantasBaja.dat.");
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	private static Planta buscarPlanta(int codigo) {
	    for (Planta planta : ListaPlantas) {
	        if (planta.getCodigo() == codigo) {
	            return planta;
	        }
	    }
	    return null;
	}
	
	
	private static Empleado buscarEmpleado(int codigo) {
	    for (Empleado empleado : ListaEmpleados) {
	        if (empleado.getIdentificacion() == codigo) {
	            return empleado;
	        }
	    }
	    return null;
	}
	
	
	private static Planta buscarPlantaBaja(int codigo) {
	    for (Planta planta : ListaPlantasBaja) {
	        if (planta.getCodigo() == codigo) {
	            return planta;
	        }
	    }
	    return null;
	}
	
	
	private static Empleado buscarEmpleadoBaja(int codigo) {
	    for (Empleado empleado : ListaEmpleadosBaja) {
	        if (empleado.getIdentificacion() == codigo) {
	            return empleado;
	        }
	    }
	    return null;
	}
	
	
	public static void verPlantasBaja() {
		File rutaPlantasXML = new File("Plantas/plantasBaja.xml");

	    if (!rutaPlantasXML.exists() || ListaPlantasBaja.isEmpty()) {
	        System.out.println("No se encuentran el archivo necesario plantasBaja.xml o no hay datos cargados de stock o precio (el ArrayList ListaPlantasBaja está vacío).");
	        return;
	    }

	    try {
	        ArrayList<Planta> listaPlantasBajaDAT = ListaPlantasBaja;
	        

	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(rutaPlantasXML);
	        doc.getDocumentElement().normalize();

	        NodeList nList = doc.getElementsByTagName("planta");

	        System.out.println("LISTADO DE PLANTAS DE BAJA");

	        for (int i = 0; i < nList.getLength(); i++) {
	            Node nNode = nList.item(i);

	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;

	                int codigo = Integer.parseInt(eElement.getElementsByTagName("codigo").item(0).getTextContent());
	                String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
	                String foto = eElement.getElementsByTagName("foto").item(0).getTextContent();
	                String descripcion = eElement.getElementsByTagName("descripcion").item(0).getTextContent();

	                Planta plantaDat = null;
	                for (Planta p : listaPlantasBajaDAT) {
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
