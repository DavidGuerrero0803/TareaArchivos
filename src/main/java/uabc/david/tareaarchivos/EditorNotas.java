package uabc.david.tareaarchivos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * EditorNotas maneja la lógica para un Editor de Notas.
 * Se encarga exclusivamente de la lectura y escritura de archivos de texto.
 * Utiliza FileReader para cargar y FileWriter para guardar los archivos.
 */
public class EditorNotas {

    /**
     * Guarda una cadena de texto en un archivo.
     * @param archivo El archivo que representa la ruta de destino en el sistema.
     * @param texto El contenido del texto capturado desde la GUI.
     */
    public void guardarArchivo(File archivo, String texto) {
        // Se inicializa el FileWriter dentro del try para un cierre automático.
        try (FileWriter escribir = new FileWriter(archivo)) {
            // Escribe el bloque completo de texto en el archivo.
            escribir.write(texto);
        } catch (IOException e) {
            // Captura errores del sistema y lo muestra en la consola.
            System.err.println("Error al guardar el texto: " + e.getMessage());
        }
    }

    /**
     * Carga el contenido de un archivo de texto.
     * Lee el archivo carácter por carácter hasta alcanzar el final del flujo.
     * @param archivo El archivo donde se guardará la información.
     * @return String con el texto recuperado del archivo.
     */
    public String cargarArchivo(File archivo) {
        StringBuilder contenidoTXT = new StringBuilder();

        // Abre el flujo de lectura asociándolo al archivo.
        try (FileReader leer = new FileReader(archivo)) {
            int caracter;
            // read() extrae un único carácter en su valor entero.
            while ((caracter = leer.read()) != -1) {
                // Convierte el entero a carácter y lo acumula.
                contenidoTXT.append((char) caracter);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el texto: " + e.getMessage());
        }
        return contenidoTXT.toString();
    }
}
