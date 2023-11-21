import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;

public class BuscarArchivosPDF {

    public static void main(String[] args) {
        String carpetaOrigen = "URL de origen";
        String carpetaDestino = "URL destido";
        String archivoListaPDF = "URL donde se encuentra la lista_pdf.txt";

        List<String> nombresPDF = new ArrayList<>();
        
        try {
            nombresPDF = Files.readAllLines(Paths.get(archivoListaPDF));

            buscarPDFEnSubcarpetas(carpetaOrigen, nombresPDF, carpetaDestino);

            System.out.println("Proceso completado.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String nombrePDF : nombresPDF) {
            System.out.println("No se encontro el archivo: " + nombrePDF);
        }
    }

    private static void buscarPDFEnSubcarpetas(String carpetaActual, List<String> nombresPDF, String carpetaDestino) throws IOException {
        File[] archivos = new File(carpetaActual).listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    buscarPDFEnSubcarpetas(archivo.getAbsolutePath(), nombresPDF, carpetaDestino);
                } else {
                    if (archivo.getName().toLowerCase().endsWith(".pdf")) {
                        String nombrePDF = archivo.getName();
                        if (nombresPDF.contains(nombrePDF)) {
                            Path origen = archivo.toPath();
                            Path destino = Paths.get(carpetaDestino, nombrePDF);
                            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Se copio " + nombrePDF + " a la carpeta de destino.");
                            nombresPDF.remove(nombrePDF);
                        }
                    }
                }
            }
        }
    }
}
