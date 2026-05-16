package uabc.david.tareaarchivos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EditorNotas {

    public void guardarArchivo(File archivo, String texto) {
        try (FileWriter escribir = new FileWriter(archivo)) {
            escribir.write(texto);
        } catch (IOException e) {
            System.err.println("Error al guardar el texto: " + e.getMessage());
        }
    }

    public String cargarArchivo(File archivo) {
        StringBuilder contenidoTXT = new StringBuilder();
        try (FileReader leer = new FileReader(archivo)) {
            int caracter;
            while ((caracter = leer.read()) != -1) {
                contenidoTXT.append((char) caracter);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el texto: " + e.getMessage());
        }
        return contenidoTXT.toString();
    }
}
