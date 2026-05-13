@@ -0,0 +1,238 @@
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EvaluacionApp {

    static Scanner sc = new Scanner(System.in);

    // Variables globales para registro de estudiantes
    static int numEstudiantes = 0;
    static String[] nombres;
    static double[][] todasNotas;
    static double[] promedios;
    static boolean notasRegistradas = false;

    // Variables globales para operaciones básicas
    static boolean operacionRealizada = false;
    static String operacionGuardar = "";

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n----- MENU INTERACTIVO -----");
            System.out.println("1. Operaciones básicas");
            System.out.println("2. Registro de notas");
            System.out.println("3. Guardar resultados");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    operacionesBasicas();
                    break;
                case 2:
                    registroNotas();
                    break;
                case 3:
                    guardarResultados();
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 4);
    }
}