package uabc.david.tareaarchivos;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ClonadorImagen gestiona la clonación binaria de archivos mediante flujos de bytes.
 */
public class ClonadorImagen {

    /**
     * Copia un archivo origen a un destino, notificando el avance a la GUI.
     * @param origen Archivo que se desea clonar.
     * @param destino Archivo destino en donde se hará la copia.
     * @param progressBar Componente visual de JavaFX para reflejar la barra de carga.
     * @param estado Etiqueta de texto para mostrar el porcentaje de la barra.
     * @param onFinished Acción ejecutable en caso de completarse la copia con éxito.
     * @param onError Consumidor que intercepta y traslada mensajes en caso de fallos.
     */
    public void clonar(File origen, File destino, ProgressBar progressBar, Label estado, Runnable onFinished, java.util.function.Consumer<String> onError) {

        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {

            // Tamaño total del archivo origen para cálculos.
            long bytesTotales = origen.length();
            // Acumulador de control de bytes transferidos.
            long bytesCopiados = 0;
            // Matriz intermedia de lectura (8 KB).
            byte[] buffer = new byte[8192];
            int leidos;

            // Lee el flujo de entrada y los convierte en arreglo de bytes.
            while ((leidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
                bytesCopiados += leidos;

                // Calcula el porcentaje de la barra de progreso.
                final double progreso = (double) bytesCopiados / bytesTotales;

                // Se envía la actualización visual al hilo principal.
                Platform.runLater(() -> {
                    progressBar.setProgress(progreso);
                    estado.setText("Progreso: " + (int) (progreso * 100) + "%");
                });

                // Se usa un delay de 5ms a propósito para el manejo del progreso.
                try { Thread.sleep(5);
                } catch (InterruptedException ignored) {

                }
            }
            // Lleva el callback de finalizado en el hilo de la GUI.
            Platform.runLater(onFinished);

        } catch (IOException ex) {
            // Maneja la excepción en caso de haber errores y lo manda de manera visual.
            Platform.runLater(() -> onError.accept(ex.getMessage()));
        }
    }
}
