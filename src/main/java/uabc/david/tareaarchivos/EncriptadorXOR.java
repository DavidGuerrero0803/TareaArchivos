package uabc.david.tareaarchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncriptadorXOR {

    public void procesarXOR(File origen, File destino, String textoMascara) throws NumberFormatException, IOException {
        int clave = Integer.parseInt(textoMascara);
        if (clave < 0 || clave > 255) {
            throw new NumberFormatException();
        }

        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {

            int byteLeido;
            while ((byteLeido = in.read()) != -1) {
                int byteCifrado = byteLeido ^ clave;
                out.write(byteCifrado);
            }
            out.flush();
        }

    }
}
