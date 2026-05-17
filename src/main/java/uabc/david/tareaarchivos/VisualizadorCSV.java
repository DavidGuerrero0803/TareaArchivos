package uabc.david.tareaarchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class VisualizadorCSV {

    public ElementoCSV procesarArchivo(File archivo) {
        String[] cabeceras = null;
        ArrayList<String[]> filas = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean esPrimeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");

                if (esPrimeraLinea) {
                    cabeceras = datos;
                    esPrimeraLinea = false;
                } else {
                    filas.add(datos);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return new ElementoCSV(cabeceras, filas);
    }

    public static class ElementoCSV {
        private String[] cabeceras;
        private ArrayList<String[]> filas;

        public ElementoCSV(String[] cabeceras, ArrayList<String[]> filas) {
            this.cabeceras = cabeceras;
            this.filas = filas;
        }

        public String[] getCabeceras() {
            return cabeceras;
        }

        public ArrayList<String[]> getFilas() {
            return filas;
        }
    }
}
