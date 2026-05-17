package uabc.david.tareaarchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * VisualizadorCSV gestiona la lógica de archivos CSV.
 * Se encarga de procesar flujos de caracteres línea por línea para segmentar
 * los registros basándose en comas.
 */
public class VisualizadorCSV {

    /**
     * Lee un archivo CSV, separa el renglón de cabecera de las filas de datos
     * y encapsula los componentes dentro de un objeto contenedor ElementoCSV.
     * @param archivo El archivo .csv seleccionado.
     * @return Objeto ElementoCSV con los arreglos de datos ya estructurados.
     */
    public ElementoCSV procesarArchivo(File archivo) {
        String[] cabeceras = null;
        ArrayList<String[]> filas = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean esPrimeraLinea = true;

            // Lee cada fila de texto hasta topar con el fin de archivo.
            while ((linea = lector.readLine()) != null) {
                // Descarta líneas vacías o compuestas de espacios en blanco.
                if (linea.trim().isEmpty()) {
                    continue;
                }

                // Descompone la línea en un arreglo de Strings utilizando la coma como separador.
                String[] datos = linea.split(",");

                // Si es el primer registro válido, se guarda como la cabecera de la tabla.
                if (esPrimeraLinea) {
                    cabeceras = datos;
                    esPrimeraLinea = false;
                } else {
                    // Los registros que le siguen se unen a la lista como filas de contenido.
                    filas.add(datos);
                }
            }
        } catch (IOException e) {
            // Captura de error en caso de haber uno con el archivo.
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        // Regresa una nueva instancia con las cabeceras y ya filas leídas.
        return new ElementoCSV(cabeceras, filas);
    }

    /**
     * Clase estática que encapsula los componentes segmentados de un archivo CSV.
     */
    public static class ElementoCSV {
        private String[] cabeceras;
        private ArrayList<String[]> filas;

        /**
         * Constructor que asigna las cabeceras y las filas de registros procesadas.
         * @param cabeceras Arreglo con los nombres de las columnas.
         * @param filas ArrayList de arreglos que representan los registros de celdas.
         */
        public ElementoCSV(String[] cabeceras, ArrayList<String[]> filas) {
            this.cabeceras = cabeceras;
            this.filas = filas;
        }

        // Getters usados para extraer los componentes del modelo.
        public String[] getCabeceras() {
            return cabeceras;
        }

        public ArrayList<String[]> getFilas() {
            return filas;
        }
    }
}
