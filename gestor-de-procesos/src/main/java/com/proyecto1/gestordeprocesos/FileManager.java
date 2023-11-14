package com.proyecto1.gestordeprocesos;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileManager {
    private FileChooser fileChooser;

    public FileManager() {
        fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
    }

    // Configura el FileChooser para seleccionar solo archivos .txt y .asm
    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Seleccionar archivos");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.asm"),
                new FileChooser.ExtensionFilter("ASM Files", "*.asm")
        );
    }

    /**
     * Abre un cuadro de diálogo para seleccionar múltiples archivos y devuelve una lista de ellos.
     *
     * @param stage El escenario principal de la aplicación.
     * @return Lista de archivos seleccionados.
     */
    public List<File> openMultipleFiles() {
        return fileChooser.showOpenMultipleDialog(null);
    }

    /**
     * Lee el contenido de un archivo y lo devuelve como una lista de cadenas.
     *
     * @param file El archivo a leer.
     * @return Lista de cadenas con el contenido del archivo.
     * @throws IOException Si hay un error al leer el archivo.
     */
    public List<String> readFile(File file) throws IOException, IOException {
        return Files.readAllLines(file.toPath());
    }

    /**
     * Realiza una validación básica del contenido del archivo. Puedes ampliar esta función según tus necesidades.
     *
     * @param lines Las líneas del archivo.
     * @return Verdadero si el archivo es válido, falso en caso contrario.
     */
    public boolean validateFileContent(List<String> lines) {
        if (lines.isEmpty()) {
            return false;
        }

//        // Expresión regular para validar las instrucciones
//        String regexMOV = "^MOV [AB]X, \\d+$"; // Por ejemplo: MOV AX, 7
//        String regexLOAD = "^LOAD [AB]X$";     // Por ejemplo: LOAD AX
//        String regexADD = "^ADD [AB]X$";       // Por ejemplo: ADD BX
//        String regexSTORE = "^STORE [AB]X$";   // Por ejemplo: STORE AX
//
//        for (String line : lines) {
//            // Se elimina el espacio adicional y se comprueba si la línea coincide con alguno de los formatos validos
//            String trimmedLine = line.trim();
//            if (!trimmedLine.matches(regexMOV) && !trimmedLine.matches(regexLOAD) &&
//                    !trimmedLine.matches(regexADD) && !trimmedLine.matches(regexSTORE)) {
//                return false; // Si alguna línea no coincide con los formatos válidos, se retorna falso
//            }
//        }
        return true; // Todas las líneas son válidas
    }


}
