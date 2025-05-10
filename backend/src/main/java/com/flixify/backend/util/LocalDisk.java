package com.flixify.backend.util;

import java.io.File;
import java.nio.file.Path;

public class LocalDisk {

    /**
     * Deletes the given file from the disk
     * @param path
     */
    public static void deleteFile(Path path) {

        if(path != null) {
            File file = path.toFile();
            file.delete();
        }
    }

    /**
     * Deletes the given directory from the disk
     * @param path
     */
    public static void deleteDirectory(Path path) {

        File directory = path.toFile();
        if (directory.isDirectory()) {
            File[] allContents = directory.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectory(file.toPath());
                }
            }
        }
        directory.delete(); // Deletes file or empty directory
    }

    /**
     * Creates directory if does not exist.
     * @param directory
     */
    public static void createDirectoryIfNotExists(File directory) {

        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }
    }
}
