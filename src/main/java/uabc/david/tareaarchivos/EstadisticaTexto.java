package uabc.david.tareaarchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class EstadisticaTexto {

    public HashMap<String, Integer> realizarEstadistica(File archivo) {
        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))){
            String linea;

            while ((linea = lector.readLine()) != null) {
                lineas++;
                caracteres += linea.length();
                if (!linea.trim().isEmpty()) {
                    palabras += linea.trim().split("\\s+").length;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el texto: " + e.getMessage());
        }

        HashMap<String, Integer> estadisticas = new HashMap<>();
        estadisticas.put("Lineas", lineas);
        estadisticas.put("Palabras", palabras);
        estadisticas.put("Caracteres", caracteres);

        return estadisticas;
    }
}
