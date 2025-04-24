import java.util.*;
import java.io.*;

// Importamos todas las clases principales
import Clases.Principales.*;
import Clases.LeerJson;

public class PrinColmena {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Colmena> Colmenas = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // TODO: Cargar datos si existen

            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                continuar = manejarOpcion(scanner.nextLine());
            }

            // LeerJson.Guardar(Colmenas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Función que muestra el menú
    public static void mostrarMenu() {
        System.out.println("""
        ╔═════════════════════════════════════════════════════════════╗
            🌼🐝  BIENVENIDO AL SISTEMA DE GESTIÓN APÍCOLA  🐝🌼
                     ¡Administra & Registra tus colmenas! 🍯
        ╚═════════════════════════════════════════════════════════════╝
       
       1️👥  Registrar nueva Colmena
       2️‍🆕  Registrar nuevo Apicultor
       3️👑  Asignar Abeja Reina a una Colmena
       4️👀  Realizar Inspección a Colmena
       5️📝  Mostrar Información Registrada
       6️🧑🏻‍🌾  Asignar Apicultor a Colmena
       7️📩  Editar Información Existente
       8️🔚  Salir del Sistema
       """);
        System.out.print("📜 Indica tu próximo movimiento en esta jornada apícola. Elige una opción del 1 al 8: ");
    }

    // Función que maneja la opción del menú
    public static boolean manejarOpcion(String opcion) {
        switch (opcion) {
            case "1" ->
                    GestorColmenas.registrarColmena();
            case "2" ->
                    GestorColmenas.registrarApicultor();
            case "3" ->
                    GestorColmenas.asignarAbejaReina();
            case "4" ->
                    GestorColmenas.realizarInspeccion();
            case "5" ->
                    GestorColmenas.mostrarInformacion();
            case "6" ->
                    GestorColmenas.asignarApicultorAColmena();
            case "7" ->
                    GestorColmenas.editarInformacion();
            case "8" -> {
                // TODO: Confirmar salida, guardar datos si es necesario
                return false;
            }
            default -> System.out.println("⚠️ Opción no válida. Intenta nuevamente."); // los de menu, cambien eso
        }
        return true;
    }
}

public class GestorColmenas {
    public static void registrarColmena() {
        System.out.println("\n🐝 REGISTRO DE NUEVA COLMENA");

        try {
            // Se solicita el ID para identificar de forma única la colmena.
            String id = Utils.solicitarCampo("Ingrese ID de la colmena: ");

            // Verifica si ya existe una colmena con ese ID para evitar duplicados.
            if (Utils.idExiste(colmenas, id)) {
                System.out.println("El ID " + id + " ya está registrado.\n");
                return; // Se detiene el proceso si el ID ya está en uso.
            }

            // Se pide la ubicación porque es fundamental saber dónde se encuentra la colmena físicamente.
            String ubicacion = Utils.solicitarCampo("Ingrese ubicación de la colmena: ");

            // El estado de salud es necesario para monitorear el bienestar de la colmena desde su registro.
            String estadoSalud = solicitarEstadoSalud();

            // El tipo define la estructura de la colmena, lo cual puede afectar su mantenimiento y producción.
            String tipo = Utils.solicitarCampo("Ingrese tipo de colmena (Ej: Langstroth, Warre, Top-Bar): ");

            // Se solicita la cantidad de abejas como dato básico sobre la población de la colmena.
            int cantidadAbejas = solicitarCantidadAbejas();

            // Se solicita la produccion de miel en la colmena
            float produccionMiel = solicitarProduccionMiel();

            // Crea una nueva colmena
            Colmena nuevaColmena = new Colmena(id, ubicacion, tipo, estadoSalud, cantidadAbejas, produccionMiel);
            colmenas.add(nuevaColmena); // Se agrega la nueva colmena a la lista global.

            System.out.println("✅ Colmena registrada correctamente.");
        } catch (Exception e) {
            // Captura cualquier error inesperado durante el proceso de registro.
            System.out.println("❌ Error al registrar la colmena: " + e.getMessage());
        }
    }

    // Función para registrar un nuevo apicultor
    public static void registrarApicultor() {
       System.out.println("\n🐝 REGISTRO DE NUEVO APICULTOR");

    try {
        // Solicitar el nombre del apicultor
        String nombre = Utils.solicitarCampo("Ingrese el nombre del apicultor: ");
        
        // Validar que el nombre no esté vacío
        if (nombre.isEmpty()) {
            System.out.println("⚠️ El nombre no puede estar vacío. Intenta nuevamente.");
            return;
        }

        // Solicitar la edad del apicultor
        int edad = -1;
        while (edad < 18) {
            try {
                edad = Integer.parseInt(Utils.solicitarCampo("Ingrese la edad del apicultor (mayor de 18): "));
                if (edad < 18) {
                    System.out.println("⚠️ El apicultor debe ser mayor de 18 años.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, ingrese un número válido para la edad.");
            }
        }

        // Solicitar los años de experiencia del apicultor
        int aniosExperiencia = -1;
        while (aniosExperiencia < 0) {
            try {
                aniosExperiencia = Integer.parseInt(Utils.solicitarCampo("Ingrese los años de experiencia del apicultor: "));
                if (aniosExperiencia < 0) {
                    System.out.println("⚠️ Los años de experiencia no pueden ser negativos.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, ingrese un número válido para los años de experiencia.");
            }
        }

        // Crear un nuevo objeto apicultor con los datos proporcionados
        Apicultor nuevoApicultor = new Apicultor(nombre, edad, aniosExperiencia);
        
        // Agregar el nuevo apicultor a la lista
        apicultores.add(nuevoApicultor);

        // Confirmar que el apicultor fue registrado correctamente
        System.out.println("✅ Apicultor registrado correctamente.");
    } catch (Exception e) {
        // Capturar cualquier error inesperado
        System.out.println("❌ Error al registrar al apicultor: " + e.getMessage());
    }
}

    // Función para asignar una abeja reina a una colmena
    public static void asignarAbejaReina() {
        // TODO: Mostrar lista de colmenas sin reina
        // TODO: Permitir elegir colmena
        // TODO: Pedir datos de la abeja reina (nombre, edad, etc.)
        // TODO: Validar datos
        // TODO: Asignar la abeja reina a la colmena seleccionada
        // TODO: Confirmar asignación
    }

    // Función para realizar una inspección
    public static void realizarInspeccion() {
        // TODO: Mostrar lista de colmenas disponibles
        // TODO: Permitir al usuario seleccionar una
        // TODO: Pedir datos de la inspección (fecha, observaciones, etc.)
        // TODO: Agregar inspección a la colmena seleccionada
        // TODO: Confirmar que la inspección fue registrada
    }

    // Función para mostrar información
    public static void mostrarInformacion() {
        // TODO: Mostrar submenú con tipos de información a mostrar
        // TODO: Dependiendo de la opción, mostrar:
        //   - Colmenas registradas
        //   - Apicultores
        //   - Abejas reinas
        //   - Historial de inspecciones
        // TODO: Permitir volver al menú principal
    }

    // Función para asignar un apicultor a una colmena
    public static void asignarApicultorAColmena() {
        // TODO: Mostrar lista de colmenas
        // TODO: Mostrar lista de apicultores
        // TODO: Permitir seleccionar ambos
        // TODO: Validar que el apicultor no esté ya asignado
        // TODO: Asignar apicultor a la colmena
        // TODO: Confirmar asignación
    }

    // Función para editar información existente
    public static void editarInformacion() {
        // TODO: Mostrar submenú: ¿qué desea editar? (colmena, apicultor, abeja reina, inspección)
        // TODO: Según elección, mostrar lista de elementos
        // TODO: Permitir seleccionar uno
        // TODO: Pedir nuevos datos
        // TODO: Validar y aplicar cambios
        // TODO: Confirmar edición
    }

    private static String solicitarEstadoSalud() {
        // Este mensaje guía al usuario a ingresar un estado de salud válido para la colmena.
        String mensaje = """
            Estado de Salud:
            ├─ En plenitud
            ├─ Zumbido estable
            └─ Colmena en riesgo
            👉 Ingresa una opción:""";

        // Se valida que el input esté entre las opciones permitidas para mantener la coherencia de datos.
        String input = Utils.solicitarCampo(mensaje);

        // Se normaliza el texto para que comience con mayúscula y continúe en minúscula.
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }


    private static byte solicitarCantidadAbejas() {
        while (true) {
            try {
                // Se solicita al usuario que indique la cantidad de abejas en la colmena.
                String input = Utils.solicitarCampo("Cantidad de abejas (0–25): ");
                byte cantidad = Byte.parseByte(input);

                // Se valida que el número esté dentro del rango permitido para evitar inconsistencias.
                if (cantidad < 0 || cantidad > 25) {
                    System.out.println("La cantidad no puede ser negativa.");
                    continue; // Reintenta si el valor está fuera de rango.
                }

                return cantidad;
            } catch (NumberFormatException e) {
                // Si el input no es un número, se informa al usuario.
                System.out.println("❌ Entrada inválida. Debe ser un número.");
            } catch (IllegalArgumentException e) {
                // Muestra cualquier otro error específico lanzado por el programa.
                System.out.println(e.getMessage());
            }
        }
    }

    private static float solicitarProduccionMiel() {
        while (true) {
            try {
                // Se solicita la producción de miel porque es un indicador clave del rendimiento de la colmena.
                String input = Utils.solicitarCampo("Producción estimada de miel (kg): ");
                float produccion = Float.parseFloat(input);

                // Se valida que no sea un valor negativo, ya que no tiene sentido en este contexto.
                if (produccion < 0) {
                    System.out.println("❌ La producción no puede ser negativa.");
                    continue;
                }

                return produccion;
            } catch (NumberFormatException e) {
                // Si el usuario no introduce un número válido, se le informa del error.
                System.out.println("❌ Entrada inválida. Debe ser un número decimal (usa punto, no coma).");
            }
        }
    }
}
