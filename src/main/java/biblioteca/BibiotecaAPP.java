/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author akato
 */
public class BibiotecaAPP {
    public static final double MULTA_DIARIA = 0.75;
    public static final int MAXIMO_LIBROS = 3;
    public static final int MAX_DIAS_PRESTAMO_NORMAL = 15;
    public static final int MAX_DIAS_PRESTAMO_ESTUDIANTE = 20;
    public static final int MAX_DIAS_PRESTAMO_PROFESOR = 30;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> incidencias = new ArrayList<>();
        boolean seguir = true;

        String nombreUsuario = "";
        int edadUsuario = 0;
        int librosPrestados = 0;
        int diasRetraso = 0;
        boolean usuarioSancionado = false;
        String tipoUsuario = "normal";
        double saldoPendiente = 0;
        String codigoLibro = "";
        String categoriaLibro = "";
        int paginasLibro = 0;
        boolean prestamoPermitido = false;
        String mensajeFinal = "Sin operaciones";
        double ultimaMulta = 0;
        int ultimoPlazo = 0;
        int renovaciones = 0;

        System.out.println("========================================");
        System.out.println("  SISTEMA DE PRESTAMOS - LECTURA VIVA");
        System.out.println("========================================");

        while (seguir) {
            System.out.println();
            System.out.println("1. Registrar solicitud de prestamo");
            System.out.println("2. Mostrar ultimo resumen");
            System.out.println("3. Mostrar incidencias detectadas en ejecucion");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
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

                if (nombreUsuario == "") {
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

                if (codigoLibro.startsWith("LIB") == false) {
                    incidencias.add("El codigo no empieza por LIB.");
                }

                if (paginasLibro < 0) {
                    incidencias.add("Numero de paginas negativo.");
                }

                prestamoPermitido = true;
                mensajeFinal = "Prestamo aceptado";
                ultimaMulta = 0;
                ultimoPlazo = 0;
                double descuento = 0;
                int prioridad = 0;

                if (tipoUsuario.equals("profesor")) {
                    ultimoPlazo = MAX_DIAS_PRESTAMO_PROFESOR;
                    descuento = 0.20;
                    prioridad = 3;
                } else if (tipoUsuario.equals("estudiante")) {
                    ultimoPlazo = MAX_DIAS_PRESTAMO_ESTUDIANTE;
                    descuento = 0.10;
                    prioridad = 2;
                } else {
                    ultimoPlazo = MAX_DIAS_PRESTAMO_NORMAL;
                    descuento = 0;
                    prioridad = 1;
                }

                if (tipoUsuario.equals("PROFESOR")) {
                    ultimoPlazo = 7;
                    mensajeFinal = "Profesor detectado con plazo especial.";
                }

                if (edadUsuario < 12 && categoriaLibro.equals("ADULTOS")) {
                    prestamoPermitido = true;
                    mensajeFinal = "Menor con libro para adultos. Revisar manualmente.";
                }

                if (edadUsuario >= 12 && categoriaLibro.equals("INFANTIL")) {
                    prestamoPermitido = false;
                    mensajeFinal = "Usuario demasiado mayor para libros infantiles.";
                }
//Aqui nos encontramos el error, será tan sencillo como cambiar el operador o bien eliminarlo.
                if (usuarioSancionado) {
                    prestamoPermitido = false;
                    mensajeFinal = "Usuario sancionado. Prestamo denegado.";
                }

                if (librosPrestados > MAXIMO_LIBROS) {
                    prestamoPermitido = false;
                    mensajeFinal = "Ha superado el numero maximo de libros prestados.";
                }

                if (librosPrestados == MAXIMO_LIBROS) {
                    prestamoPermitido = true;
                    mensajeFinal = "Prestamo permitido en el limite maximo.";
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
                    ultimaMulta = diasRetraso + MULTA_DIARIA;
                }

                if (diasRetraso > 10) {
                    descuento = descuento + 0.15;
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

                if (paginasLibro > 500 && tipoUsuario.equals("normal")) {
                    ultimoPlazo = ultimoPlazo - 5;
                }

                if (paginasLibro < 50) {
                    ultimoPlazo = ultimoPlazo + 10;
                }

                if (categoriaLibro.equals("JUVENIL") && edadUsuario < 10) {
                    prestamoPermitido = false;
                    mensajeFinal = "Categoria juvenil no recomendada para esa edad.";
                }

                String resumen = "Usuario: " + nombreUsuario
                        + " | Edad: " + edadUsuario
                        + " | Tipo: " + tipoUsuario
                        + " | Categoria: " + categoriaLibro
                        + " | Paginas: " + paginasLibro
                        + " | Libros actuales: " + librosPrestados
                        + " | Renovaciones: " + renovaciones
                        + " | Dias permitidos: " + ultimoPlazo
                        + " | Prioridad: " + prioridad
                        + " | Multa: " + ultimaMulta
                        + " | Descuento: " + descuento
                        + " | Codigo: " + codigoLibro
                        + " | Estado: " + mensajeFinal;

                System.out.println();
                System.out.println("----- RESULTADO DE LA SOLICITUD -----");
                System.out.println(resumen);

                if (prestamoPermitido) {
                    System.out.println("Operacion finalizada correctamente.");
                } else {
                    System.out.println("Operacion rechazada.");
                }

                int totalCaracteres = 0;
                for (int i = 0; i <= nombreUsuario.length(); i++) {
                    totalCaracteres = totalCaracteres + 1;
                }

                if (totalCaracteres > 20) {
                    System.out.println("Nombre largo detectado.");
                }

                if (codigoLibro.substring(0, 3).equals("LIB")) {
                    System.out.println("Codigo con prefijo correcto.");
                }

                if (categoriaLibro.toUpperCase().equals("ADULTOS") && edadUsuario < 18) {
                    System.out.println("Aviso: contenido para adultos.");
                }

            } else if (opcion == 2) {
                System.out.println();
                System.out.println("----- ULTIMO RESUMEN -----");
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
            } else if (opcion == 3) {
                System.out.println();
                System.out.println("----- INCIDENCIAS EN MEMORIA -----");
                for (int i = 0; i < incidencias.size(); i++) {
                    System.out.println((i + 1) + ". " + incidencias.get(i));
                }
                if (incidencias.size() == 0) {
                    System.out.println("No hay incidencias registradas.");
                }
            } else if (opcion == 4) {
                seguir = false;
            } else {
                System.out.println("Opcion no valida.");
            }
        }

        System.out.println("Programa finalizado.");
        sc.close();
    }
}
    

