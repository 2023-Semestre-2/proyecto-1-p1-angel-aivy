package com.proyecto1.gestordeprocesos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Storage class represents the storage system of the Mini computer,
 * handling both virtual memory (reserved as an extra space) and file storage, including indexing of the files.
 */
public class Storage {
    private int SIZE = 512; // The total size of the storage, todo: to be loaded from a settings file in future implementations
    private final int VIRTUAL_MEMORY_SIZE = 64; // The size of the virtual memory, todo: to be loaded from a settings file in future implementations
    private int INDEX_SPACE_SIZE; // The size of the space reserved for storing file indices
    private final String[] diskStorage = new String[SIZE]; // The array representing the storage disk
    private int start = 0; // The start index for storing new files

    public void SetMemorySize(int size){
        this.SIZE = size;
    }


    /**
     * Adds a file to the storage system.
     *
     * @param fileName    the name of the file to be added
     * @param fileContent the content of the file to be added
     */
    public void addFile(String fileName, List<String> fileContent) {
        if (INDEX_SPACE_SIZE == 0) {
            System.out.println("No index space size reserved!"); //This error msg should be displayed in the UI
            return;
        }

        if (start == 0) {
            start = VIRTUAL_MEMORY_SIZE + INDEX_SPACE_SIZE;
        }

        int end = calculateEndIndex(fileContent.size());

        if (end >= SIZE) {
            System.out.println("Not enough space to store file content");
            return;
        }

        if (!storeFileIndex(fileName, start, end)) {
            System.out.println("No space left to store file index");
            return;
        }

        storeFileContent(fileContent, start, end);
        start = end + 1;
    }

    /**
     * Calculates the end index in the storage array where the file content will end.
     *
     * @param fileContentSize the size of the file content
     * @return the end index in the storage array
     */
    private int calculateEndIndex(int fileContentSize) {
        return start + fileContentSize - 1;
    }

    /**
     * Stores the index information of a file in the reserved index space of the storage array.
     *
     * @param fileName the name of the file
     * @param start    the start index in the storage array where the file content starts
     * @param end      the end index in the storage array where the file content ends
     * @return true if the file index was stored successfully, false otherwise
     */
    private boolean storeFileIndex(String fileName, int start, int end) {
        for (int i = VIRTUAL_MEMORY_SIZE; i < VIRTUAL_MEMORY_SIZE + INDEX_SPACE_SIZE; i++) {
            if (diskStorage[i] == null) {
                diskStorage[i] = fileName + " -> " + start + "," + end;
                return true;
            }
        }
        return false;
    }

    public List<String> getFileContent(String fileName) {
        for (int i = VIRTUAL_MEMORY_SIZE; i < VIRTUAL_MEMORY_SIZE + INDEX_SPACE_SIZE; i++) {
            if (diskStorage[i] != null && diskStorage[i].startsWith(fileName)) {

                String[] parts = diskStorage[i].split("->")[1].trim().split(",");
                int start = Integer.parseInt(parts[0].trim());
                int end = Integer.parseInt(parts[1].trim());

                return new ArrayList<>(Arrays.asList(diskStorage).subList(start, end + 1));
            }
        }
        // Si no se encuentra el archivo, retorna null o una lista vacía
        return null;
    }

    public String getFileIndex(String fileName) {
        for (int i = VIRTUAL_MEMORY_SIZE; i < VIRTUAL_MEMORY_SIZE + INDEX_SPACE_SIZE; i++) {
            if (diskStorage[i] != null && diskStorage[i].startsWith(fileName)) {
                return diskStorage[i];
            }
        }
        return null;
    }

    /**
     * Stores the content of a file in the storage array.
     *
     * @param fileContent the content of the file
     * @param start       the start index in the storage array where the file content starts
     * @param end         the end index in the storage array where the file content ends
     */
    private void storeFileContent(List<String> fileContent, int start, int end) {
        for (int i = start; i <= end; i++) {
            diskStorage[i] = fileContent.get(i - start);
        }
    }

    /**
     * Sets the size of the space reserved for storing file indices.
     *
     * @param size the size of the index space
     */
    public void setIndexSpaceSize(int size) {
        this.INDEX_SPACE_SIZE = size;
    }

    public String[] getDiskStorage() {
        return diskStorage;
    }

    /**
     * Prints the content of the storage array to the console, for debugging purposes.
     */
    public void printStorageContent() {
        for (int i = 0; i < diskStorage.length; i++) {
            if (diskStorage[i] != null) {
                System.out.println(diskStorage[i]);
            } else {
                System.out.println("Index " + i + ": empty");
            }
        }
    }
}
