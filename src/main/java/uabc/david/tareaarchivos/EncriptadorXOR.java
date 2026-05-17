package uabc.david.tareaarchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * EncriptadorXOR administra la lógica de cifrado mediante flujos de bytes.
 * Usa el operador XOR para enmascarar la información de cualquier archivo.
 */
public class EncriptadorXOR {

    /**
     * Procesa un archivo byte por byte aplicando una clave numérica mediante la operación XOR.
     * Si el archivo ya estaba cifrado con esta misma clave, el proceso lo desencriptará automáticamente.
     * @param origen Archivo de entrada que se desea procesar.
     * @param destino Archivo de salida donde se guardará el resultado transformado.
     * @param textoMascara Cadena de texto que contiene la clave numérica a evaluar.
     * @throws NumberFormatException Si la clave no es un número válido o se sale del rango de un byte (0-255).
     * @throws IOException Si ocurre un fallo en la apertura, lectura o escritura de los flujos físicos.
     */
    public void procesarXOR(File origen, File destino, String textoMascara) throws NumberFormatException, IOException {
        // Convierte la entrada de texto a un valor entero.
        int clave = Integer.parseInt(textoMascara);

        // Se valida que solamente se pueda tomar valores entre 0 y 255.
        if (clave < 0 || clave > 255) {
            // Lanza excepción para ser atrapada por la interfaz gráfica.
            throw new NumberFormatException();
        }

        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {

            int byteLeido;
            // read() extrae un único byte (0-255) y devuelve -1 cuando llega al final del archivo.
            while ((byteLeido = in.read()) != -1) {
                // El operador '^' realiza la comparación bit a bit (XOR) modificando el estado del byte.
                int byteCifrado = byteLeido ^ clave;
                // Se escribe el byte modificado en el flujo de salida.
                out.write(byteCifrado);
            }
            // Se asegura el vaciado de cualquier byte que haya quedado guardado.
            out.flush();
        }

    }
}
