import java.util.*;
import java.util.Scanner;

// Clase abstracta base
abstract class Figura {
    protected String nombre;
    protected double area;

    public Figura(String nombre) {
        this.nombre = nombre;
    }

    // Método abstracto que deben implementar las clases hijas
    public abstract double calcularArea();

    public String getNombre() {
        return nombre;
    }

    public double getArea() {
        return area;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f unidades²", nombre, area);
    }

    // Clase anidada estática para decoración
    public static class Decorador {
        public static void imprimirCabecera(String titulo) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           " + titulo.toUpperCase());
            System.out.println("=".repeat(50));
        }

        public static void imprimirLinea() {
            System.out.println("-".repeat(50));
        }

        public static void imprimirSeparador() {
            System.out.println("*".repeat(50));
        }
    }
}

// Clase para círculo
class Circulo extends Figura {
    private double radio;

    public Circulo(double radio) throws IllegalArgumentException {
        super("Círculo");
        validarParametro(radio, "Radio");
        this.radio = radio;
        this.area = calcularArea();
    }

    @Override
    public double calcularArea() {
        return Math.PI * Math.pow(radio, 2);
    }

    // Sobrecarga: método estático para calcular sin crear objeto
    public static double calcularArea(double radio) {
        return Math.PI * Math.pow(radio, 2);
    }

    private void validarParametro(double valor, String parametro) {
        if (valor <= 0) {
            throw new IllegalArgumentException(parametro + " debe ser mayor que 0");
        }
    }

    @Override
    public String toString() {
        return String.format("Círculo (r=%.2f): %.2f unidades²", radio, area);
    }
}

// Clase para rectángulo
class Rectangulo extends Figura {
    private double base;
    private double altura;

    public Rectangulo(double base, double altura) throws IllegalArgumentException {
        super("Rectángulo");
        validarParametros(base, altura);
        this.base = base;
        this.altura = altura;
        this.area = calcularArea();
    }

    @Override
    public double calcularArea() {
        return base * altura;
    }

    // Sobrecarga: método para cuadrado (un solo parámetro)
    public static double calcularArea(double lado) {
        return lado * lado;
    }

    // Sobrecarga: método estático para rectángulo
    public static double calcularArea(double base, double altura) {
        return base * altura;
    }

    private void validarParametros(double base, double altura) {
        if (base <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Base y altura deben ser mayores que 0");
        }
    }

    @Override
    public String toString() {
        return String.format("Rectángulo (%.2f x %.2f): %.2f unidades²", base, altura, area);
    }
}

// Clase para triángulo
class Triangulo extends Figura {
    private double base;
    private double altura;

    public Triangulo(double base, double altura) throws IllegalArgumentException {
        super("Triángulo");
        validarParametros(base, altura);
        this.base = base;
        this.altura = altura;
        this.area = calcularArea();
    }

    @Override
    public double calcularArea() {
        return (base * altura) / 2.0;
    }

    // Sobrecarga: método estático
    public static double calcularArea(double base, double altura) {
        return (base * altura) / 2.0;
    }

    // Sobrecarga: fórmula de Herón para tres lados
    public static double calcularArea(double lado1, double lado2, double lado3) throws IllegalArgumentException {
        if (lado1 <= 0 || lado2 <= 0 || lado3 <= 0) {
            throw new IllegalArgumentException("Todos los lados deben ser mayores que 0");
        }

        // Verificar que los lados pueden formar un triángulo
        if (lado1 + lado2 <= lado3 || lado1 + lado3 <= lado2 || lado2 + lado3 <= lado1) {
            throw new IllegalArgumentException("Los lados no pueden formar un triángulo válido");
        }

        double s = (lado1 + lado2 + lado3) / 2.0; // Semi-perímetro
        return Math.sqrt(s * (s - lado1) * (s - lado2) * (s - lado3));
    }

    private void validarParametros(double base, double altura) {
        if (base <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Base y altura deben ser mayores que 0");
        }
    }

    @Override
    public String toString() {
        return String.format("Triángulo (b=%.2f, h=%.2f): %.2f unidades²", base, altura, area);
    }
}

// Clase principal del sistema
public class SistemaCalculoAreas {
    private static ArrayList<Figura> historial = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Figura.Decorador.imprimirCabecera("SISTEMA DE CÁLCULO DE ÁREAS");

        boolean continuar = true;

        while (continuar) {
            try {
                mostrarMenu();
                int opcion = leerOpcion();

                switch (opcion) {
                    case 1:
                        calcularAreaCirculo();
                        break;
                    case 2:
                        calcularAreaRectangulo();
                        break;
                    case 3:
                        calcularAreaTriangulo();
                        break;
                    case 4:
                        mostrarHistorial();
                        break;
                    case 5:
                        mostrarEstadisticas();
                        break;
                    case 6:
                        continuar = false;
                        Figura.Decorador.imprimirCabecera("¡GRACIAS POR USAR EL SISTEMA!");
                        break;
                    default:
                        System.out.println(" Opción no válida. Intente nuevamente.");
                }

                if (continuar && opcion >= 1 && opcion <= 3) {
                    preguntarContinuar();
                }

            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
                scanner.nextLine(); // Limpiar buffer
            }
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        Figura.Decorador.imprimirLinea();
        System.out.println("           MENÚ PRINCIPAL");
        Figura.Decorador.imprimirLinea();
        System.out.println("1.  Calcular área de círculo");
        System.out.println("2.  Calcular área de rectángulo");
        System.out.println("3.  Calcular área de triángulo");
        System.out.println("4.  Ver historial de cálculos");
        System.out.println("5.  Ver estadísticas");
        System.out.println("6.  Salir");
        Figura.Decorador.imprimirLinea();
        System.out.print("Seleccione una opción (1-6): ");
    }

    private static int leerOpcion() {
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // Consumir entrada inválida
            throw new IllegalArgumentException("Debe ingresar un número válido");
        }
        return scanner.nextInt();
    }

    private static void calcularAreaCirculo() {
        System.out.println("\n CÁLCULO DE ÁREA - CÍRCULO");
        Figura.Decorador.imprimirLinea();

        try {
            System.out.print("Ingrese el radio: ");
            double radio = leerDouble();

            Circulo circulo = new Circulo(radio);
            historial.add(circulo);

            System.out.println("\n Resultado:");
            System.out.println("   " + circulo.toString());

        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void calcularAreaRectangulo() {
        System.out.println("\n CÁLCULO DE ÁREA - RECTÁNGULO");
        Figura.Decorador.imprimirLinea();

        try {
            System.out.print("Ingrese la base: ");
            double base = leerDouble();

            System.out.print("Ingrese la altura: ");
            double altura = leerDouble();

            Rectangulo rectangulo = new Rectangulo(base, altura);
            historial.add(rectangulo);

            System.out.println("\n Resultado:");
            System.out.println("   " + rectangulo.toString());

            // Mostrar si es un cuadrado
            if (Math.abs(base - altura) < 0.001) {
                System.out.println("     Nota: Es un cuadrado perfecto!");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void calcularAreaTriangulo() {
        System.out.println("\n CÁLCULO DE ÁREA - TRIÁNGULO");
        Figura.Decorador.imprimirLinea();
        System.out.println("Seleccione el método de cálculo:");
        System.out.println("1. Base y altura");
        System.out.println("2. Tres lados (Fórmula de Herón)");
        System.out.print("Opción: ");

        try {
            int metodo = leerInt();

            if (metodo == 1) {
                System.out.print("Ingrese la base: ");
                double base = leerDouble();

                System.out.print("Ingrese la altura: ");
                double altura = leerDouble();

                Triangulo triangulo = new Triangulo(base, altura);
                historial.add(triangulo);

                System.out.println("\n Resultado:");
                System.out.println("   " + triangulo.toString());

            } else if (metodo == 2) {
                System.out.print("Ingrese el primer lado: ");
                double lado1 = leerDouble();

                System.out.print("Ingrese el segundo lado: ");
                double lado2 = leerDouble();

                System.out.print("Ingrese el tercer lado: ");
                double lado3 = leerDouble();

                double area = Triangulo.calcularArea(lado1, lado2, lado3);

                // Crear un triángulo temporal para el historial usando altura calculada
                double baseTemp = lado1;
                double alturaTemp = (2 * area) / baseTemp;
                Triangulo triangulo = new Triangulo(baseTemp, alturaTemp);
                historial.add(triangulo);

                System.out.println("\n Resultado:");
                System.out.printf("   Triángulo (lados: %.2f, %.2f, %.2f): %.2f unidades²%n",
                        lado1, lado2, lado3, area);

            } else {
                System.out.println(" Opción no válida");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void mostrarHistorial() {
        Figura.Decorador.imprimirCabecera("HISTORIAL DE CÁLCULOS");

        if (historial.isEmpty()) {
            System.out.println(" No hay cálculos registrados aún.");
            return;
        }

        System.out.println("Total de figuras calculadas: " + historial.size());
        Figura.Decorador.imprimirLinea();

        for (int i = 0; i < historial.size(); i++) {
            System.out.printf("%2d. %s%n", (i + 1), historial.get(i).toString());
        }
    }

    // Métodos estáticos para estadísticas
    private static void mostrarEstadisticas() {
        Figura.Decorador.imprimirCabecera("ESTADÍSTICAS DEL SISTEMA");

        if (historial.isEmpty()) {
            System.out.println(" No hay datos para mostrar estadísticas.");
            return;
        }

        int totalFiguras = contarTotalFiguras();
        double promedioAreas = calcularPromedioAreas();
        double areaMaxima = encontrarAreaMaxima();
        double areaMinima = encontrarAreaMinima();

        System.out.println(" RESUMEN ESTADÍSTICO:");
        Figura.Decorador.imprimirLinea();
        System.out.printf("Total de figuras calculadas: %d%n", totalFiguras);
        System.out.printf("Promedio de áreas: %.2f unidades²%n", promedioAreas);
        System.out.printf("Área máxima: %.2f unidades²%n", areaMaxima);
        System.out.printf("Área mínima: %.2f unidades²%n", areaMinima);

        Figura.Decorador.imprimirLinea();
        mostrarDistribucionPorTipo();
    }

    private static int contarTotalFiguras() {
        return historial.size();
    }

    private static double calcularPromedioAreas() {
        if (historial.isEmpty()) return 0.0;

        double suma = 0.0;
        for (Figura figura : historial) {
            suma += figura.getArea();
        }
        return suma / historial.size();
    }

    private static double encontrarAreaMaxima() {
        return historial.stream()
                .mapToDouble(Figura::getArea)
                .max()
                .orElse(0.0);
    }

    private static double encontrarAreaMinima() {
        return historial.stream()
                .mapToDouble(Figura::getArea)
                .min()
                .orElse(0.0);
    }

    private static void mostrarDistribucionPorTipo() {
        System.out.println(" DISTRIBUCIÓN POR TIPO:");

        Map<String, Integer> contadores = new HashMap<>();
        Map<String, Double> sumaAreas = new HashMap<>();

        for (Figura figura : historial) {
            String tipo = figura.getNombre();
            contadores.put(tipo, contadores.getOrDefault(tipo, 0) + 1);
            sumaAreas.put(tipo, sumaAreas.getOrDefault(tipo, 0.0) + figura.getArea());
        }

        for (String tipo : contadores.keySet()) {
            int cantidad = contadores.get(tipo);
            double promedio = sumaAreas.get(tipo) / cantidad;
            System.out.printf("• %s: %d figuras (promedio: %.2f unidades²)%n",
                    tipo, cantidad, promedio);
        }
    }

    private static double leerDouble() {
        if (!scanner.hasNextDouble()) {
            scanner.nextLine(); // Consumir entrada inválida
            throw new IllegalArgumentException("Debe ingresar un número válido");
        }
        double valor = scanner.nextDouble();
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor que 0");
        }
        return valor;
    }

    private static int leerInt() {
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // Consumir entrada inválida
            throw new IllegalArgumentException("Debe ingresar un número entero válido");
        }
        return scanner.nextInt();
    }

    private static void preguntarContinuar() {
        System.out.print("\n¿Desea realizar otro cálculo? (s/n): ");
        scanner.nextLine(); // Consumir salto de línea pendiente
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("n") || respuesta.equals("no")) {
            mostrarHistorial();
            mostrarEstadisticas();
        }
    }
}
