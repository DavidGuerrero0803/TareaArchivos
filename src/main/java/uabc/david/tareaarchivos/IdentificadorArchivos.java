package uabc.david.tareaarchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * IdentificadorArchivos maneja la detección de formatos mediante firmas binarias.
 * Analiza los primeros bytes de un flujo binario para determinar el tipo real
 * de un archivo.
 */
public class IdentificadorArchivos {

    /**
     * Extrae de forma secuencial los primeros 8 bytes de un archivo.
     * @param archivo Archivo que se desea examinar.
     * @return ArrayList con los valores enteros (0-255) de los primeros bytes leídos.
     * @throws IOException Si el archivo no es accesible o el sistema operativo deniega la lectura.
     */
    public ArrayList<Integer> leer8Bytes(File archivo) throws IOException {
        int unByte;
        int contador = 0;
        ArrayList<Integer> ochoBytes = new ArrayList<>();

        try (FileInputStream in = new FileInputStream(archivo)) {
            // Lee byte por byte deteniéndose al llegar al fin de archivo (-1) o alcanzar el límite de 8.
            while ((unByte = in.read()) != -1 && (contador < 8)) {
                ochoBytes.add(unByte);
                contador++;
            }
        }
        return ochoBytes;
    }

    /**
     * Compara los bytes recuperados del archivo con las firmas hexadecimales de formatos conocidos.
     * @param ochoBytes ArrayList de bytes extraídos de la cabecera del archivo.
     * @return String con el nombre del formato identificado o un estado desconocido.
     */
    public String verificarFormato(ArrayList<Integer> ochoBytes) {
        if (ochoBytes == null || ochoBytes.isEmpty()) {
            return "Archivo vacío o ilegible";
        }

        // Guardado de las firmas hexadecimales dentro de cada ArrayList de formatos.
        ArrayList<Integer> formatoPDF = new ArrayList<>(List.of(0x25, 0x50, 0x44, 0x46, 0x2D));
        ArrayList<Integer> formatoJPEG = new ArrayList<>(List.of(0xFF, 0xD8, 0xFF));
        ArrayList<Integer> formatoPNG = new ArrayList<>(List.of(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A));
        ArrayList<Integer> formatoZIP = new ArrayList<>(List.of(0x50, 0x4B, 0x03, 0x04));
        ArrayList<Integer> formatoGIF = new ArrayList<>(List.of(0x47, 0x49, 0x46, 0x38));

        // Evaluación de las firmas por cada ArrayList.
        if (coincideFirma(ochoBytes, formatoPNG)) {
            return "PNG";
        }
        if (coincideFirma(ochoBytes, formatoPDF)) {
            return "PDF";
        }
        if (coincideFirma(ochoBytes, formatoJPEG)) {
            return "JPEG/JPG";
        }
        if (coincideFirma(ochoBytes, formatoZIP)) {
            return "ZIP, DOCX, XLSX";
        }
        if (coincideFirma(ochoBytes, formatoGIF)) {
            return "GIF";
        }

        // Si no llega a coincidir con alguno de los anteriores, entonces será un MP4.
        if (ochoBytes.size() >= 8 &&
                ochoBytes.get(4) == 0x66 && ochoBytes.get(5) == 0x74 &&
                ochoBytes.get(6) == 0x79 && ochoBytes.get(7) == 0x70) {
            return "MP4";
        }

        // Si no llega a ser ninguno de los anteriores, entonces será "formato desconocido".
        return "Formato desconocido";
    }

    /**
     * Compara ordenadamente dos listas de bytes elemento por elemento.
     * Permite validar si la cabecera del archivo inicia exactamente con la secuencia.
     * @param archivoBytes ArrayList de bytes original leída del disco.
     * @param firma ArrayList de la firma con la que se desea comparar.
     * @return true si todos los bytes de la firma coinciden, false en caso contrario.
     */
    private boolean coincideFirma(ArrayList<Integer> archivoBytes, ArrayList<Integer> firma) {
        // Si el archivo tiene menos bytes que el patrón de búsqueda, es imposible que coincida.
        if (archivoBytes.size() < firma.size()) {
            return false;
        }
        // El ciclo for itera sobre la longitud total de la firma de referencia.
        for (int i = 0; i < firma.size(); i++) {
            if (!archivoBytes.get(i).equals(firma.get(i))) {
                return false;
            }
        }
        // Si el bucle concluyó sin problemas, entonces la firma coincide.
        return true;
    }
}
