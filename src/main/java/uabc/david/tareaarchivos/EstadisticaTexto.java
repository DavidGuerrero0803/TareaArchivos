package uabc.david.tareaarchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * EstadisticaTexto maneja la lógica de un archivo mediante flujos de caracteres.
 * Lee un archivo de texto línea por línea para calcular
 * la cantidad total de líneas, palabras y caracteres presentes.
 */
public class EstadisticaTexto {

    /**
     * Analiza el archivo y genera un mapa con los totales de los componentes del texto.
     * Usa el BufferedReader para una lectura eficiente por líneas.
     * @param archivo Archivo de texto que se va a escanear.
     * @return HashMap donde las llaves son "Lineas", "Palabras" y "Caracteres",
     * y los valores representan sus conteos correspondientes.
     */
    public HashMap<String, Integer> realizarEstadistica(File archivo) {
        // Se inicializan los contadores internos.
        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;

        // Abre el flujo de caracteres envuelto en un buffer de lectura.
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))){
            String linea;

            // El ciclo lee el texto delimitado por saltos de línea hasta el fin de archivo.
            while ((linea = lector.readLine()) != null) {
                // Incrementa el contador por cada iteración exitosa.
                lineas++;

                // Acumula la cantidad de caracteres de la línea actual.
                caracteres += linea.length();

                // Esta validación sirve para evitar procesar líneas vacías.
                if (!linea.trim().isEmpty()) {
                    palabras += linea.trim().split("\\s+").length;
                }
            }
        } catch (IOException e) {
            // Manejo de la excepción en caso de producirse un error.
            System.err.println("Error al cargar el texto: " + e.getMessage());
        }

        // Guarda toda la información procesada en el HashMap y regresa los resultados.
        HashMap<String, Integer> estadisticas = new HashMap<>();
        estadisticas.put("Lineas", lineas);
        estadisticas.put("Palabras", palabras);
        estadisticas.put("Caracteres", caracteres);

        return estadisticas;
    }
}
