package main;

import entities.DomicilioFiscal;
import entities.Empresa;
import service.DomicilioFiscalService;
import service.EmpresaService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AppMenu {
    
    private Scanner sc = new Scanner(System.in);
    private EmpresaService empresaService = new EmpresaService();
    private DomicilioFiscalService domicilioService = new DomicilioFiscalService();
    
    public void mostrarMenu() {
        int opcion = 0;
        
        do {
            try {
                System.out.println("\n========================================");
                System.out.println("           MENU PRINCIPAL");
                System.out.println("========================================");
                System.out.println("  EMPRESAS");
                System.out.println("  1. Crear nueva empresa");
                System.out.println("  2. Buscar empresa por ID");
                System.out.println("  3. Buscar empresa por CUIT");
                System.out.println("  4. Listar todas las empresas");
                System.out.println("  5. Actualizar empresa");
                System.out.println("  6. Eliminar empresa");
                System.out.println("----------------------------------------");
                System.out.println("  DOMICILIOS");
                System.out.println("  7. Listar domicilios");
                System.out.println("  8. Buscar domicilio por ID");
                System.out.println("----------------------------------------");
                System.out.println("  9. Salir");
                System.out.println("========================================");
                System.out.print("Opcion: ");
                
                opcion = sc.nextInt();
                sc.nextLine(); // limpio el buffer
                
                System.out.println();
                
                switch (opcion) {
                    case 1:
                        crearEmpresa();
                        break;
                    case 2:
                        buscarEmpresaPorId();
                        break;
                    case 3:
                        buscarEmpresaPorCuit();
                        break;
                    case 4:
                        listarEmpresas();
                        break;
                    case 5:
                        actualizarEmpresa();
                        break;
                    case 6:
                        eliminarEmpresa();
                        break;
                    case 7:
                        listarDomicilios();
                        break;
                    case 8:
                        buscarDomicilioPorId();
                        break;
                    case 9:
                        System.out.println("Chau!");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine(); // limpio el buffer
                opcion = 0;
            }
            
        } while (opcion != 9);
        
        sc.close();
    }
    
    // ===== METODOS PARA EMPRESAS =====
    
    private void crearEmpresa() {
        try {
            System.out.println("========================================");
            System.out.println("        CREAR NUEVA EMPRESA");
            System.out.println("========================================\n");
            
            // Pido los datos de la empresa
            System.out.print("Razon Social: ");
            String razonSocial = sc.nextLine().toUpperCase();
            
            System.out.print("CUIT (formato XX-XXXXXXXX-X): ");
            String cuit = sc.nextLine();
            
            System.out.print("Actividad Principal: ");
            String actividad = sc.nextLine();
            
            System.out.print("Email: ");
            String email = sc.nextLine().toLowerCase();
            
            // Ahora pido los datos del domicilio
            System.out.println("\n--- Domicilio Fiscal ---");
            System.out.print("Calle: ");
            String calle = sc.nextLine();
            
            System.out.print("Numero: ");
            int numero = sc.nextInt();
            sc.nextLine();
            
            System.out.print("Ciudad: ");
            String ciudad = sc.nextLine();
            
            System.out.print("Provincia: ");
            String provincia = sc.nextLine();
            
            System.out.print("Codigo Postal: ");
            String cp = sc.nextLine();
            
            System.out.print("Pais: ");
            String pais = sc.nextLine();
            
            // Creo los objetos
            DomicilioFiscal domicilio = new DomicilioFiscal(calle, numero, ciudad, provincia, cp, pais);
            Empresa empresa = new Empresa(razonSocial, cuit, actividad, email, domicilio);
            
            // Guardo (aca se hace la transaccion)
            empresaService.insertar(empresa);
            
            System.out.println("\nEmpresa creada con exito! ID: " + empresa.getId());
            
        } catch (SQLException e) {
            System.out.println("\nError al crear empresa: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError: entrada invalida");
            sc.nextLine();
        }
    }
    
    private void buscarEmpresaPorId() {
        try {
            System.out.print("Ingrese ID de la empresa: ");
            Long id = sc.nextLong();
            sc.nextLine();
            
            Empresa empresa = empresaService.obtenerPorId(id);
            
            if (empresa != null) {
                mostrarEmpresa(empresa);
            } else {
                System.out.println("No se encontro empresa con ID: " + id);
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: debe ingresar un numero");
            sc.nextLine();
        }
    }
    
    private void buscarEmpresaPorCuit() {
        try {
            System.out.print("Ingrese CUIT: ");
            String cuit = sc.nextLine();
            
            Empresa empresa = empresaService.buscarPorCuit(cuit);
            
            if (empresa != null) {
                mostrarEmpresa(empresa);
            } else {
                System.out.println("No se encontro empresa con CUIT: " + cuit);
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void listarEmpresas() {
        try {
            List<Empresa> empresas = empresaService.obtenerTodos();
            
            if (empresas.isEmpty()) {
                System.out.println("No hay empresas registradas");
            } else {
                System.out.println("========================================");
                System.out.println("      LISTADO DE EMPRESAS");
                System.out.println("========================================\n");
                
                for (Empresa emp : empresas) {
                    System.out.println("----------------------------------------");
                    mostrarEmpresa(emp);
                }
                System.out.println("----------------------------------------");
                System.out.println("Total: " + empresas.size() + " empresas");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void actualizarEmpresa() {
        try {
            System.out.print("Ingrese ID de la empresa a actualizar: ");
            Long id = sc.nextLong();
            sc.nextLine();
            
            Empresa empresa = empresaService.obtenerPorId(id);
            
            if (empresa == null) {
                System.out.println("No se encontro empresa con ID: " + id);
                return;
            }
            
            System.out.println("\n--- Datos actuales ---");
            mostrarEmpresa(empresa);
            
            System.out.println("\n--- Nuevos datos (Enter para mantener) ---");
            
            System.out.print("Nueva Razon Social [" + empresa.getRazonSocial() + "]: ");
            String razonSocial = sc.nextLine();
            if (!razonSocial.isEmpty()) {
                empresa.setRazonSocial(razonSocial.toUpperCase());
            }
            
            System.out.print("Nueva Actividad [" + empresa.getActividadPrincipal() + "]: ");
            String actividad = sc.nextLine();
            if (!actividad.isEmpty()) {
                empresa.setActividadPrincipal(actividad);
            }
            
            System.out.print("Nuevo Email [" + empresa.getEmail() + "]: ");
            String email = sc.nextLine();
            if (!email.isEmpty()) {
                empresa.setEmail(email.toLowerCase());
            }
            
            empresaService.actualizar(empresa);
            System.out.println("\nEmpresa actualizada con exito");
            
        } catch (SQLException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError: entrada invalida");
            sc.nextLine();
        }
    }
    
    private void eliminarEmpresa() {
        try {
            System.out.print("Ingrese ID de la empresa a eliminar: ");
            Long id = sc.nextLong();
            sc.nextLine();
            
            Empresa empresa = empresaService.obtenerPorId(id);
            
            if (empresa == null) {
                System.out.println("No se encontro empresa con ID: " + id);
                return;
            }
            
            mostrarEmpresa(empresa);
            
            System.out.print("\nEsta seguro de eliminar esta empresa? (S/N): ");
            String confirmacion = sc.nextLine().toUpperCase();
            
            if (confirmacion.equals("S")) {
                empresaService.eliminar(id);
                System.out.println("Empresa eliminada correctamente");
            } else {
                System.out.println("Operacion cancelada");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: entrada invalida");
            sc.nextLine();
        }
    }
    
    // ===== METODOS PARA DOMICILIOS =====
    
    private void listarDomicilios() {
        try {
            List<DomicilioFiscal> domicilios = domicilioService.obtenerTodos();
            
            if (domicilios.isEmpty()) {
                System.out.println("No hay domicilios registrados");
            } else {
                System.out.println("========================================");
                System.out.println("   LISTADO DE DOMICILIOS FISCALES");
                System.out.println("========================================\n");
                
                for (DomicilioFiscal dom : domicilios) {
                    System.out.println("----------------------------------------");
                    System.out.println("ID: " + dom.getId());
                    System.out.println(dom.toString());
                }
                System.out.println("----------------------------------------");
                System.out.println("Total: " + domicilios.size() + " domicilios");
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void buscarDomicilioPorId() {
        try {
            System.out.print("Ingrese ID del domicilio: ");
            Long id = sc.nextLong();
            sc.nextLine();
            
            DomicilioFiscal domicilio = domicilioService.obtenerPorId(id);
            
            if (domicilio != null) {
                System.out.println("\nID: " + domicilio.getId());
                System.out.println(domicilio.toString());
            } else {
                System.out.println("No se encontro domicilio con ID: " + id);
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: debe ingresar un numero");
            sc.nextLine();
        }
    }
    
    // ===== METODO AUXILIAR =====
    
    private void mostrarEmpresa(Empresa empresa) {
        System.out.println("ID: " + empresa.getId());
        System.out.println("Razon Social: " + empresa.getRazonSocial());
        System.out.println("CUIT: " + empresa.getCuit());
        System.out.println("Actividad: " + empresa.getActividadPrincipal());
        System.out.println("Email: " + empresa.getEmail());
        
        if (empresa.getDomicilioFiscal() != null) {
            System.out.println("Domicilio: " + empresa.getDomicilioFiscal().toString());
        } else {
            System.out.println("Domicilio: Sin domicilio");
        }
    }
}