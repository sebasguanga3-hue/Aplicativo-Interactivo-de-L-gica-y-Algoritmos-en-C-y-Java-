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

    // Parte 2 — Operaciones matemáticas
    public static void operacionesBasicas() {
        double a, b;
        char operacion;

        System.out.print("Ingrese el primer número: ");
        a = sc.nextDouble();
        System.out.print("Ingrese el segundo número: ");
        b = sc.nextDouble();
        System.out.print("Ingrese la operación (+,-,*,/): ");
        operacion = sc.next().charAt(0);

        double resultado;
        switch (operacion) {
            case '+':
                resultado = a + b;
                System.out.println("Resultado: " + resultado);
                operacionGuardar = a + " + " + b + " = " + resultado;
                break;
            case '-':
                resultado = a - b;
                System.out.println("Resultado: " + resultado);
                operacionGuardar = a + " - " + b + " = " + resultado;
                break;
            case '*':
                resultado = a * b;
                System.out.println("Resultado: " + resultado);
                operacionGuardar = a + " * " + b + " = " + resultado;
                break;
            case '/':
                if (b != 0) {
                    resultado = a / b;
                    System.out.println("Resultado: " + resultado);
                    operacionGuardar = a + " / " + b + " = " + resultado;
                } else {
                    System.out.println("Error: División entre cero.");
                    operacionGuardar = "Intento de división entre cero: " + a + " / " + b;
                }
                break;
            default:
                System.out.println("Operación no válida.");
                operacionGuardar = "Operación inválida";
        }

        operacionRealizada = true;
    }

    // Parte 3 — Registro de notas y cálculo de promedios
    public static void registroNotas() {
        System.out.print("Ingrese el número de estudiantes a registrar: ");
        numEstudiantes = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        nombres = new String[numEstudiantes];
        todasNotas = new double[numEstudiantes][5];
        promedios = new double[numEstudiantes];

        for (int e = 0; e < numEstudiantes; e++) {
            System.out.println("\n--- Estudiante " + (e + 1) + " ---");
            System.out.print("Ingrese el nombre del estudiante: ");
            nombres[e] = sc.nextLine();

            double[] notas = new double[5];
            double suma = 0;

            for (int i = 0; i < 5; i++) {
                do {
                    System.out.print("Ingrese nota " + (i + 1) + " (0 a 10): ");
                    notas[i] = sc.nextDouble();
                    if (notas[i] < 0 || notas[i] > 10) {
                        System.out.println("Nota inválida. Debe estar entre 0 y 10.");
                    }
                } while (notas[i] < 0 || notas[i] > 10);
                suma += notas[i];
            }
            sc.nextLine(); // limpiar buffer

            todasNotas[e] = notas;
            promedios[e] = suma / 5.0;
        }

        notasRegistradas = true;
        System.out.println("\nRegistro de notas completado.");
    }

    // Parte 4 — Guardar resultados en archivo
    public static void guardarResultados() {
        if (operacionRealizada) {
            // Guardar solo operación matemática
            guardarOperacion(operacionGuardar);
            operacionRealizada = false; // reseteamos para próxima operación
        } else if (notasRegistradas) {
            // Guardar solo notas y promedios
            double promedioGeneral = 0;
            double notaMayor = -1;
            double notaMenor = 11;
            int totalAprobados = 0;
            int totalReprobados = 0;

            for (int i = 0; i < numEstudiantes; i++) {
                promedioGeneral += promedios[i];
                if (promedios[i] > notaMayor) notaMayor = promedios[i];
                if (promedios[i] < notaMenor) notaMenor = promedios[i];
                if (promedios[i] >= 7) totalAprobados++;
                else totalReprobados++;
            }

            promedioGeneral /= numEstudiantes;

            // Mostrar resumen final
            System.out.println("\n--- RESUMEN FINAL ---");
            for (int e = 0; e < numEstudiantes; e++) {
                System.out.print("Estudiante: " + nombres[e] + " | Notas: ");
                for (double n : todasNotas[e]) System.out.print(n + " ");
                System.out.println("| Promedio: " + promedios[e]);
            }

            System.out.println("\nPromedio general de todos los estudiantes: " + promedioGeneral);
            System.out.println("Nota mayor global (promedio): " + notaMayor);
            System.out.println("Nota menor global (promedio): " + notaMenor);
            System.out.println("Cantidad de estudiantes aprobados: " + totalAprobados);
            System.out.println("Cantidad de estudiantes reprobados: " + totalReprobados);

            // Guardar en archivo
            guardarResultadosArchivo(nombres, todasNotas, promedios, promedioGeneral,
                    notaMayor, notaMenor, totalAprobados, totalReprobados);

            notasRegistradas = false; // reseteamos para no guardar de nuevo
        } else {
            System.out.println("No hay resultados para guardar.");
        }
    }

    public static void guardarOperacion(String operacion) {
        try {
            FileWriter archivo = new FileWriter("resultados.txt", true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime ahora = LocalDateTime.now();

            archivo.write("--- OPERACION BASICA ---\n");
            archivo.write(operacion + "\n");
            archivo.write("Fecha: " + dtf.format(ahora) + "\n");
            archivo.write("Lenguaje: Java\n");
            archivo.write("---------------------------\n");

            archivo.close();
            System.out.println("Operación guardada correctamente en resultados.txt.");
        } catch (IOException e) {
            System.out.println("Error al guardar la operación: " + e.getMessage());
        }
    }

    public static void guardarResultadosArchivo(String[] nombres, double[][] todasNotas, double[] promedios,
                                                double promedioGeneral, double notaMayor, double notaMenor,
                                                int totalAprobados, int totalReprobados) {
        try {
            FileWriter archivo = new FileWriter("resultados.txt", true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime ahora = LocalDateTime.now();

            archivo.write("--- RESUMEN FINAL ---\n");
            for (int e = 0; e < nombres.length; e++) {
                archivo.write("Estudiante: " + nombres[e] + " | Notas: ");
                for (double n : todasNotas[e]) archivo.write(n + " ");
                archivo.write("| Promedio: " + promedios[e] + "\n");
            }

            archivo.write("\nPromedio general de todos los estudiantes: " + promedioGeneral + "\n");
            archivo.write("Nota mayor global (promedio): " + notaMayor + "\n");
            archivo.write("Nota menor global (promedio): " + notaMenor + "\n");
            archivo.write("Cantidad de estudiantes aprobados: " + totalAprobados + "\n");
            archivo.write("Cantidad de estudiantes reprobados: " + totalReprobados + "\n");
            archivo.write("Fecha: " + dtf.format(ahora) + "\n");
            archivo.write("Lenguaje: Java\n");
            archivo.write("---------------------------\n");

            archivo.close();
            System.out.println("Resultados guardados correctamente en resultados.txt.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}