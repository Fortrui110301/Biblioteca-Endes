package biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author akato
 */
public class BibiotecaAPP {
    public static final double MULTA_DIARIA = 0.75;
    public static final int MAXIMO_LIBROS = 3;
    public static final int MAX_DIAS_PRESTAMO_NORMAL = 15;
    public static final int MAX_DIAS_PRESTAMO_ESTUDIANTE = 20;
    public static final int MAX_DIAS_PRESTAMO_PROFESOR = 30;

    private static String nombreUsuario = "";
    private static int edadUsuario = 0;
    private static int librosPrestados = 0;
    private static int diasRetraso = 0;
    private static boolean usuarioSancionado = false;
    private static String tipoUsuario = "normal";
    private static double saldoPendiente = 0;
    private static String codigoLibro = "";
    private static String categoriaLibro = "";
    private static int paginasLibro = 0;
    private static boolean prestamoPermitido = false;
    private static String mensajeFinal = "Sin operaciones";
    private static double ultimaMulta = 0;
    private static int ultimoPlazo = 0;
    private static int renovaciones = 0;
    private static int prioridad = 0;
    private static double descuento = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> incidencias = new ArrayList<>();
        boolean seguir = true;

        System.out.println("========================================");
        System.out.println("   SISTEMA DE PRESTAMOS - LECTURA VIVA");
        System.out.println("========================================");

        while (seguir) {
            mostrarMenu();
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarSolicitud(sc, incidencias);
                    break;
                case 2:
                    mostrarUltimoResumen();
                    break;
                case 3:
                    mostrarIncidencias(incidencias);
                    break;
                case 4:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }

        System.out.println("Programa finalizado.");
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n1. Registrar solicitud de prestamo");
        System.out.println("2. Mostrar ultimo resumen");
        System.out.println("3. Mostrar incidencias detectadas en ejecucion");
        System.out.println("4. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    private static void registrarSolicitud(Scanner sc, List<String> incidencias) {
        System.out.print("Nombre de usuario: ");
        nombreUsuario = sc.nextLine();

        System.out.print("Edad del usuario: ");
        edadUsuario = sc.nextInt();

        System.out.print("Numero de libros ya prestados: ");
        librosPrestados = sc.nextInt();

        System.out.print("Dias de retraso acumulados: ");
        diasRetraso = sc.nextInt();

        System.out.print("Numero de renovaciones del libro: ");
        renovaciones = sc.nextInt();
        sc.nextLine();

        System.out.print("Esta sancionado? (true/false): ");
        usuarioSancionado = sc.nextBoolean();
        sc.nextLine();

        System.out.print("Tipo de usuario (normal/estudiante/profesor): ");
        tipoUsuario = sc.nextLine();

        System.out.print("Saldo pendiente: ");
        saldoPendiente = sc.nextDouble();
        sc.nextLine();

        System.out.print("Codigo del libro: ");
        codigoLibro = sc.nextLine();

        System.out.print("Categoria del libro (INFANTIL/JUVENIL/ADULTOS): ");
        categoriaLibro = sc.nextLine();

        System.out.print("Numero de paginas del libro: ");
        paginasLibro = sc.nextInt();
        sc.nextLine();

        if (nombreUsuario.isEmpty()) {
            incidencias.add("El nombre esta vacio.");
        }
        if (nombreUsuario.length() < 3) {
            incidencias.add("Nombre demasiado corto.");
        }
        if (edadUsuario < 0) {
            incidencias.add("Edad negativa detectada.");
        }
        if (edadUsuario > 120) {
            incidencias.add("Edad poco realista.");
        }
        if (codigoLibro.length() < 5) {
            incidencias.add("Codigo de libro demasiado corto.");
        }
        if (!codigoLibro.startsWith("LIB")) {
            incidencias.add("El codigo no empieza por LIB.");
        }
        if (paginasLibro < 0) {
            incidencias.add("Numero de paginas negativo.");
        }

        prestamoPermitido = true;
        mensajeFinal = "Prestamo aceptado";
        ultimaMulta = 0;
        ultimoPlazo = 0;
        descuento = 0;
        prioridad = 0;

        if (tipoUsuario.equalsIgnoreCase("profesor")) {
            ultimoPlazo = MAX_DIAS_PRESTAMO_PROFESOR;
            descuento = 0.20;
            prioridad = 3;
        } else if (tipoUsuario.equalsIgnoreCase("estudiante")) {
            ultimoPlazo = MAX_DIAS_PRESTAMO_ESTUDIANTE;
            descuento = 0.10;
            prioridad = 2;
        } else {
            ultimoPlazo = MAX_DIAS_PRESTAMO_NORMAL;
            descuento = 0;
            prioridad = 1;
        }

        if (edadUsuario < 12 && categoriaLibro.equalsIgnoreCase("ADULTOS")) {
            prestamoPermitido = true;
            mensajeFinal = "Menor con libro para adultos. Revisar manualmente.";
        }
        if (edadUsuario >= 12 && categoriaLibro.equalsIgnoreCase("INFANTIL")) {
            prestamoPermitido = false;
            mensajeFinal = "Usuario demasiado mayor para libros infantiles.";
        }

        if (usuarioSancionado) {
            prestamoPermitido = false;
            mensajeFinal = "Usuario sancionado. Prestamo denegado.";
        }

        if (librosPrestados >= MAXIMO_LIBROS) {
            prestamoPermitido = false;
            mensajeFinal = "Ha alcanzado o superado el numero maximo de libros prestados.";
        }

        if (saldoPendiente > 0 && saldoPendiente < 5) {
            prestamoPermitido = true;
            mensajeFinal = "Prestamo aceptado con deuda pequena pendiente.";
        }
        if (saldoPendiente >= 5) {
            prestamoPermitido = false;
            mensajeFinal = "Prestamo denegado por deuda.";
        }

        if (diasRetraso > 0) {
            ultimaMulta = diasRetraso * MULTA_DIARIA;
        }
        if (diasRetraso > 10) {
            descuento += 0.15;
        }
        if (diasRetraso > 30) {
            incidencias.add("Retraso excesivo detectado para revisar manualmente.");
        }

        if (renovaciones > 2) {
            prestamoPermitido = true;
            mensajeFinal = "Prestamo aceptado aunque supera renovaciones.";
        }
        if (renovaciones < 0) {
            incidencias.add("Renovaciones negativas.");
        }
        if (paginasLibro > 500 && tipoUsuario.equalsIgnoreCase("normal")) {
            ultimoPlazo -= 5;
        }
        if (paginasLibro < 50) {
            ultimoPlazo += 10;
        }
        if (categoriaLibro.equalsIgnoreCase("JUVENIL") && edadUsuario < 10) {
            prestamoPermitido = false;
            mensajeFinal = "Categoria juvenil no recomendada para esa edad.";
        }

        imprimirResultado();

        int totalCaracteres = 0;
        for (int i = 0; i < nombreUsuario.length(); i++) {
            totalCaracteres++;
        }

        if (totalCaracteres > 20) {
            System.out.println("Nombre largo detectado.");
        }
        if (codigoLibro.toUpperCase().startsWith("LIB")) {
            System.out.println("Codigo con prefijo correcto.");
        }
        if (categoriaLibro.equalsIgnoreCase("ADULTOS") && edadUsuario < 18) {
            System.out.println("Aviso: contenido para adultos.");
        }
    }

    private static void imprimirResultado() {
        String resumen = String.format(
            "Usuario: %s | Edad: %d | Tipo: %s | Categoria: %s | Paginas: %d\n" +
            "Libros actuales: %d | Renovaciones: %d | Dias permitidos: %d | Prioridad: %d\n" +
            "Multa: %.2f | Descuento: %.2f | Codigo: %s | Estado: %s",
            nombreUsuario, edadUsuario, tipoUsuario, categoriaLibro, paginasLibro,
            librosPrestados, renovaciones, ultimoPlazo, prioridad,
            ultimaMulta, descuento, codigoLibro, mensajeFinal
        );

        System.out.println("\n----- RESULTADO DE LA SOLICITUD -----");
        System.out.println(resumen);
        System.out.println(prestamoPermitido ? "Operacion finalizada correctamente." : "Operacion rechazada.");
    }

    private static void mostrarUltimoResumen() {
        System.out.println("\n----- ULTIMO RESUMEN -----");
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Edad: " + edadUsuario);
        System.out.println("Tipo de usuario: " + tipoUsuario.toUpperCase());
        System.out.println("Libros prestados: " + librosPrestados);
        System.out.println("Dias de retraso: " + diasRetraso);
        System.out.println("Renovaciones: " + renovaciones);
        System.out.println("Sancionado: " + usuarioSancionado);
        System.out.println("Saldo pendiente: " + saldoPendiente);
        System.out.println("Codigo del libro: " + codigoLibro);
        System.out.println("Categoria: " + categoriaLibro);
        System.out.println("Paginas: " + paginasLibro);
        System.out.println("Plazo asignado: " + ultimoPlazo);
        System.out.println("Multa aplicada: " + ultimaMulta);
        System.out.println("Estado final: " + mensajeFinal);
    }

    private static void mostrarIncidencias(List<String> incidencias) {
        System.out.println("\n----- INCIDENCIAS EN MEMORIA -----");
        if (incidencias.isEmpty()) {
            System.out.println("No hay incidencias registradas.");
        } else {
            for (int i = 0; i < incidencias.size(); i++) {
                System.out.println((i + 1) + ". " + incidencias.get(i));
            }
        }
    }
}