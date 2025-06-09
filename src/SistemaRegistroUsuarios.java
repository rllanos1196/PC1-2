import java.util.*;

// Clase Usuario con atributos privados y métodos públicos
class Usuario {
    private String nombre;
    private int edad;
    private String ciudad;

    // Constructor
    public Usuario(String nombre, int edad, String ciudad) {
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Usuario{nombre='" + nombre + "', edad=" + edad + ", ciudad='" + ciudad + "'}";
    }
}

// Clase Controlador de Usuarios
class ControladorUsuarios {
    private ArrayList<Usuario> usuarios;
    private Random random;

    public ControladorUsuarios() {
        this.usuarios = new ArrayList<>();
        this.random = new Random();
    }

    // Método para agregar usuario
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Método estático para calcular promedio de edad
    public static double calcularPromedioEdad(ArrayList<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            return 0.0;
        }

        double sumaEdades = 0;
        for (Usuario usuario : usuarios) {
            sumaEdades += usuario.getEdad();
        }

        return Math.round((sumaEdades / usuarios.size()) * 100.0) / 100.0;
    }

    // Método sobrecargado para buscar usuario por nombre
    public Usuario buscarUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    // Método sobrecargado para buscar usuarios por ciudad
    public ArrayList<Usuario> buscarUsuario(String ciudad, boolean esCiudad) {
        ArrayList<Usuario> usuariosEncontrados = new ArrayList<>();
        if (esCiudad) {
            for (Usuario usuario : usuarios) {
                if (usuario.getCiudad().equalsIgnoreCase(ciudad)) {
                    usuariosEncontrados.add(usuario);
                }
            }
        }
        return usuariosEncontrados;
    }

    // Método para obtener el nombre más largo
    public String obtenerNombreMasLargo() {
        if (usuarios.isEmpty()) {
            return "";
        }

        String nombreMasLargo = usuarios.get(0).getNombre();
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().length() > nombreMasLargo.length()) {
                nombreMasLargo = usuario.getNombre();
            }
        }
        return nombreMasLargo;
    }

    // Método para elegir usuario destacado aleatoriamente
    public Usuario elegirUsuarioDestacado() {
        if (usuarios.isEmpty()) {
            return null;
        }

        int indiceAleatorio = random.nextInt(usuarios.size());
        return usuarios.get(indiceAleatorio);
    }

    // Getter para la lista de usuarios
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    // Método para obtener total de usuarios
    public int getTotalUsuarios() {
        return usuarios.size();
    }

    // Clase anidada estática para mensajes decorativos
    public static class DecoradorMensajes {
        public static void mostrarTitulo(String titulo) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("🌟 " + titulo.toUpperCase() + " 🌟");
            System.out.println("=".repeat(50));
        }

        public static void mostrarSubtitulo(String subtitulo) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("📊 " + subtitulo);
            System.out.println("-".repeat(30));
        }

        public static void mostrarUsuarioDestacado(Usuario usuario) {
            System.out.println("\n🎉 ¡USUARIO DESTACADO! 🎉");
            System.out.println("┌" + "─".repeat(40) + "┐");
            System.out.println("│ " + String.format("%-38s", "Nombre: " + usuario.getNombre()) + " │");
            System.out.println("│ " + String.format("%-38s", "Edad: " + usuario.getEdad() + " años") + " │");
            System.out.println("│ " + String.format("%-38s", "Ciudad: " + usuario.getCiudad()) + " │");
            System.out.println("└" + "─".repeat(40) + "┘");
        }
    }
}

// Clase principal
public class SistemaRegistroUsuarios {
    private static Scanner scanner = new Scanner(System.in);
    private static ControladorUsuarios controlador = new ControladorUsuarios();

    public static void main(String[] args) {
        ControladorUsuarios.DecoradorMensajes.mostrarTitulo("Sistema de Registro de Usuarios");

        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = obtenerOpcion();

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    mostrarEstadisticas();
                    break;
                case 3:
                    buscarUsuarios();
                    break;
                case 4:
                    mostrarTodosLosUsuarios();
                    break;
                case 5:
                    ControladorUsuarios.DecoradorMensajes.mostrarTitulo("¡Gracias por usar el sistema!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\nMENÚ PRINCIPAL:");
        System.out.println("1. Registrar nuevo usuario");
        System.out.println("2. Mostrar estadísticas");
        System.out.println("3. Buscar usuarios");
        System.out.println("4. Mostrar todos los usuarios");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción (1-5): ");
    }

    private static int obtenerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarUsuario() {
        ControladorUsuarios.DecoradorMensajes.mostrarSubtitulo("Registro de Nuevo Usuario");

        try {
            System.out.print("Ingrese el nombre: ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }

            System.out.print("Ingrese la edad: ");
            int edad = Integer.parseInt(scanner.nextLine());

            if (edad < 0 || edad > 110) {
                throw new IllegalArgumentException("La edad debe estar entre 0 y 110 años");
            }

            System.out.print("Ingrese la ciudad: ");
            String ciudad = scanner.nextLine().trim();

            if (ciudad.isEmpty()) {
                throw new IllegalArgumentException("La ciudad no puede estar vacía");
            }

            Usuario nuevoUsuario = new Usuario(nombre, edad, ciudad);
            controlador.agregarUsuario(nuevoUsuario);

            System.out.println("Usuario registrado exitosamente!" + nuevoUsuario);

        } catch (NumberFormatException e) {
            System.out.println("Error: La edad debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void mostrarEstadisticas() {
        ControladorUsuarios.DecoradorMensajes.mostrarSubtitulo("Estadísticas del Sistema");

        if (controlador.getTotalUsuarios() == 0) {
            System.out.println("No hay usuarios registrados aún.");
            return;
        }

        // Total de usuarios
        System.out.println("Total de usuarios registrados: " + controlador.getTotalUsuarios());

        // Promedio de edad
        double promedioEdad = ControladorUsuarios.calcularPromedioEdad(controlador.getUsuarios());
        System.out.println("Promedio de edad: " + promedioEdad + " años");

        // Nombre más largo
        String nombreMasLargo = controlador.obtenerNombreMasLargo();
        System.out.println("Nombre más largo: \"" + nombreMasLargo + "\" (" + nombreMasLargo.length() + " caracteres)");

        // Usuario destacado aleatorio
        Usuario usuarioDestacado = controlador.elegirUsuarioDestacado();
        if (usuarioDestacado != null) {
            ControladorUsuarios.DecoradorMensajes.mostrarUsuarioDestacado(usuarioDestacado);
        }
    }

    private static void buscarUsuarios() {
        ControladorUsuarios.DecoradorMensajes.mostrarSubtitulo("Búsqueda de Usuarios");

        if (controlador.getTotalUsuarios() == 0) {
            System.out.println("No hay usuarios registrados para buscar.");
            return;
        }

        System.out.println("¿Cómo desea buscar?");
        System.out.println("1. Por nombre");
        System.out.println("2. Por ciudad");
        System.out.print("Seleccione una opción (1-2): ");

        int opcionBusqueda = obtenerOpcion();

        switch (opcionBusqueda) {
            case 1:
                buscarPorNombre();
                break;
            case 2:
                buscarPorCiudad();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void buscarPorNombre() {
        System.out.print("Ingrese el nombre a buscar: ");
        String nombre = scanner.nextLine().trim();

        Usuario usuario = controlador.buscarUsuario(nombre);

        if (usuario != null) {
            System.out.println("Usuario encontrado:"+ usuario);
        } else {
            System.out.println("No se encontró ningún usuario con el nombre \"" + nombre + "\"");
        }
    }

    private static void buscarPorCiudad() {
        System.out.print("Ingrese la ciudad a buscar: ");
        String ciudad = scanner.nextLine().trim();

        ArrayList<Usuario> usuariosEncontrados = controlador.buscarUsuario(ciudad, true);

        if (!usuariosEncontrados.isEmpty()) {
            System.out.println("Usuarios encontrados en " + ciudad + ":");
            for (int i = 0; i < usuariosEncontrados.size(); i++) {
                System.out.println((i + 1) + ". " + usuariosEncontrados.get(i));
            }
        } else {
            System.out.println("No se encontraron usuarios en la ciudad \"" + ciudad + "\"");
        }
    }

    private static void mostrarTodosLosUsuarios() {
        ControladorUsuarios.DecoradorMensajes.mostrarSubtitulo("Lista Completa de Usuarios");

        if (controlador.getTotalUsuarios() == 0) {
            System.out.println("No hay usuarios registrados aún.");
            return;
        }

        ArrayList<Usuario> usuarios = controlador.getUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i));
        }
    }
}
