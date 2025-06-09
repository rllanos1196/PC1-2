import java.util.*;
import java.util.Scanner;


//Cambios para la rama BranchJunior

// Clase abstracta base
abstract class Figura {
    protected String nombre;
    protected double area;

    public Figura(String nombre) {
        this.nombre = nombre;
    }

    // MÃ©todo abstracto que deben implementar las clases hijas
    public abstract double calcularArea();

    public String getNombre() {
        return nombre;
    }

    public double getArea() {
        return area;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f unidadesÂ²", nombre, area);
    }

    // Clase anidada estÃ¡tica para decoraciÃ³n
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

// Clase para cÃ­rculo
class Circulo extends Figura {
    private double radio;

    public Circulo(double radio) throws IllegalArgumentException {
        super("CÃ­rculo");
        validarParametro(radio, "Radio");
        this.radio = radio;
        this.area = calcularArea();
    }

    @Override
    public double calcularArea() {
        return Math.PI * Math.pow(radio, 2);
    }

    // Sobrecarga: mÃ©todo estÃ¡tico para calcular sin crear objeto
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
        return String.format("CÃ­rculo (r=%.2f): %.2f unidadesÂ²", radio, area);
    }
}

// Clase para rectÃ¡ngulo
class Rectangulo extends Figura {
    private double base;
    private double altura;

    public Rectangulo(double base, double altura) throws IllegalArgumentException {
        super("RectÃ¡ngulo");
        validarParametros(base, altura);
        this.base = base;
        this.altura = altura;
        this.area = calcularArea();
    }

    @Override
    public double calcularArea() {
        return base * altura;
    }

    // Sobrecarga: mÃ©todo para cuadrado (un solo parÃ¡metro)
    public static double calcularArea(double lado) {
        return lado * lado;
    }

    // Sobrecarga: mÃ©todo estÃ¡tico para rectÃ¡ngulo
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
        return String.format("RectÃ¡ngulo (%.2f x %.2f): %.2f unidadesÂ²", base, altura, area);
    }
}

// Clase para triÃ¡ngulo
class Triangulo extends Figura {
    private double base;
    private double altura;

    public Triangulo(double base, double altura) throws IllegalArgumentException {
        super("TriÃ¡ngulo");
        validarParametros(base, altura);
        this.base = base;
        this.altura = altura;
        this.area = calcularArea();
    }

    @Override
    public double calcularArea() {
        return (base * altura) / 2.0;
    }

    // Sobrecarga: mÃ©todo estÃ¡tico
    public static double calcularArea(double base, double altura) {
        return (base * altura) / 2.0;
    }

    // Sobrecarga: fÃ³rmula de HerÃ³n para tres lados
    public static double calcularArea(double lado1, double lado2, double lado3) throws IllegalArgumentException {
        if (lado1 <= 0 || lado2 <= 0 || lado3 <= 0) {
            throw new IllegalArgumentException("Todos los lados deben ser mayores que 0");
        }

        // Verificar que los lados pueden formar un triÃ¡ngulo
        if (lado1 + lado2 <= lado3 || lado1 + lado3 <= lado2 || lado2 + lado3 <= lado1) {
            throw new IllegalArgumentException("Los lados no pueden formar un triÃ¡ngulo vÃ¡lido");
        }

        double s = (lado1 + lado2 + lado3) / 2.0; // Semi-perÃ­metro
        return Math.sqrt(s * (s - lado1) * (s - lado2) * (s - lado3));
    }

    private void validarParametros(double base, double altura) {
        if (base <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Base y altura deben ser mayores que 0");
        }
    }

    @Override
    public String toString() {
        return String.format("TriÃ¡ngulo (b=%.2f, h=%.2f): %.2f unidadesÂ²", base, altura, area);
    }
}

// Clase principal del sistema
public class SistemaCalculoAreas {
    private static ArrayList<Figura> historial = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Figura.Decorador.imprimirCabecera("SISTEMA DE CÃ�LCULO DE Ã�REAS");

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
                        Figura.Decorador.imprimirCabecera("Â¡GRACIAS POR USAR EL SISTEMA!");
                        break;
                    default:
                        System.out.println(" OpciÃ³n no vÃ¡lida. Intente nuevamente.");
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
        System.out.println("           MENÃš PRINCIPAL");
        Figura.Decorador.imprimirLinea();
        System.out.println("1.  Calcular Ã¡rea de cÃ­rculo");
        System.out.println("2.  Calcular Ã¡rea de rectÃ¡ngulo");
        System.out.println("3.  Calcular Ã¡rea de triÃ¡ngulo");
        System.out.println("4.  Ver historial de cÃ¡lculos");
        System.out.println("5.  Ver estadÃ­sticas");
        System.out.println("6.  Salir");
        Figura.Decorador.imprimirLinea();
        System.out.print("Seleccione una opciÃ³n (1-6): ");
    }

    private static int leerOpcion() {
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // Consumir entrada invÃ¡lida
            throw new IllegalArgumentException("Debe ingresar un nÃºmero vÃ¡lido");
        }
        return scanner.nextInt();
    }

    private static void calcularAreaCirculo() {
        System.out.println("\n CÃ�LCULO DE Ã�REA - CÃ�RCULO");
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
        System.out.println("\n CÃ�LCULO DE Ã�REA - RECTÃ�NGULO");
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
        System.out.println("\n CÃ�LCULO DE Ã�REA - TRIÃ�NGULO");
        Figura.Decorador.imprimirLinea();
        System.out.println("Seleccione el mÃ©todo de cÃ¡lculo:");
        System.out.println("1. Base y altura");
        System.out.println("2. Tres lados (FÃ³rmula de HerÃ³n)");
        System.out.print("OpciÃ³n: ");

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

                // Crear un triÃ¡ngulo temporal para el historial usando altura calculada
                double baseTemp = lado1;
                double alturaTemp = (2 * area) / baseTemp;
                Triangulo triangulo = new Triangulo(baseTemp, alturaTemp);
                historial.add(triangulo);

                System.out.println("\n Resultado:");
                System.out.printf("   TriÃ¡ngulo (lados: %.2f, %.2f, %.2f): %.2f unidadesÂ²%n",
                        lado1, lado2, lado3, area);

            } else {
                System.out.println(" OpciÃ³n no vÃ¡lida");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private static void mostrarHistorial() {
        Figura.Decorador.imprimirCabecera("HISTORIAL DE CÃ�LCULOS");

        if (historial.isEmpty()) {
            System.out.println(" No hay cÃ¡lculos registrados aÃºn.");
            return;
        }

        System.out.println("Total de figuras calculadas: " + historial.size());
        Figura.Decorador.imprimirLinea();

        for (int i = 0; i < historial.size(); i++) {
            System.out.printf("%2d. %s%n", (i + 1), historial.get(i).toString());
        }
    }

    // MÃ©todos estÃ¡ticos para estadÃ­sticas
    private static void mostrarEstadisticas() {
        Figura.Decorador.imprimirCabecera("ESTADÃ�STICAS DEL SISTEMA");

        if (historial.isEmpty()) {
            System.out.println(" No hay datos para mostrar estadÃ­sticas.");
            return;
        }

        int totalFiguras = contarTotalFiguras();
        double promedioAreas = calcularPromedioAreas();
        double areaMaxima = encontrarAreaMaxima();
        double areaMinima = encontrarAreaMinima();

        System.out.println(" RESUMEN ESTADÃ�STICO:");
        Figura.Decorador.imprimirLinea();
        System.out.printf("Total de figuras calculadas: %d%n", totalFiguras);
        System.out.printf("Promedio de Ã¡reas: %.2f unidadesÂ²%n", promedioAreas);
        System.out.printf("Ã�rea mÃ¡xima: %.2f unidadesÂ²%n", areaMaxima);
        System.out.printf("Ã�rea mÃ­nima: %.2f unidadesÂ²%n", areaMinima);

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
        System.out.println(" DISTRIBUCIÃ“N POR TIPO:");

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
            System.out.printf("â€¢ %s: %d figuras (promedio: %.2f unidadesÂ²)%n",
                    tipo, cantidad, promedio);
        }
    }

    private static double leerDouble() {
        if (!scanner.hasNextDouble()) {
            scanner.nextLine(); // Consumir entrada invÃ¡lida
            throw new IllegalArgumentException("Debe ingresar un nÃºmero vÃ¡lido");
        }
        double valor = scanner.nextDouble();
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor que 0");
        }
        return valor;
    }

    private static int leerInt() {
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // Consumir entrada invÃ¡lida
            throw new IllegalArgumentException("Debe ingresar un nÃºmero entero vÃ¡lido");
        }
        return scanner.nextInt();
    }

    private static void preguntarContinuar() {
        System.out.print("\nÂ¿Desea realizar otro cÃ¡lculo? (s/n): ");
        scanner.nextLine(); // Consumir salto de lÃ­nea pendiente
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("n") || respuesta.equals("no")) {
            mostrarHistorial();
            mostrarEstadisticas();
        }
    }
}
