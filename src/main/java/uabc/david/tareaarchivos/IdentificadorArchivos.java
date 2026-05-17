package uabc.david.tareaarchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IdentificadorArchivos {

    public ArrayList<Integer> leer8Bytes(File archivo) throws IOException {
        int unByte;
        int contador = 0;
        ArrayList<Integer> ochoBytes = new ArrayList<>();

        try (FileInputStream in = new FileInputStream(archivo)) {
            while ((unByte = in.read()) != -1 && (contador < 8)) {
                ochoBytes.add(unByte);
                contador++;
            }
        }
        return ochoBytes;
    }

    public String verificarFormato(ArrayList<Integer> ochoBytes) {
        if (ochoBytes == null || ochoBytes.isEmpty()) {
            return "Archivo vacío o ilegible";
        }
        ArrayList<Integer> formatoPDF = new ArrayList<>(List.of(0x25, 0x50, 0x44, 0x46, 0x2D));
        ArrayList<Integer> formatoJPEG = new ArrayList<>(List.of(0xFF, 0xD8, 0xFF));
        ArrayList<Integer> formatoPNG = new ArrayList<>(List.of(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A));
        ArrayList<Integer> formatoZIP = new ArrayList<>(List.of(0x50, 0x4B, 0x03, 0x04));
        ArrayList<Integer> formatoGIF = new ArrayList<>(List.of(0x47, 0x49, 0x46, 0x38));

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

        if (ochoBytes.size() >= 8 &&
                ochoBytes.get(4) == 0x66 && ochoBytes.get(5) == 0x74 &&
                ochoBytes.get(6) == 0x79 && ochoBytes.get(7) == 0x70) {
            return "MP4";
        }

        return "Formato desconocido";
    }

    private boolean coincideFirma(ArrayList<Integer> archivoBytes, ArrayList<Integer> firma) {
        if (archivoBytes.size() < firma.size()) {
            return false;
        }
        for (int i = 0; i < firma.size(); i++) {
            if (!archivoBytes.get(i).equals(firma.get(i))) {
                return false;
            }
        }
        return true;
    }
}
