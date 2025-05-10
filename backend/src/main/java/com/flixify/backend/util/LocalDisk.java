package com.flixify.backend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocalDisk {

    /**
     * Deletes the given file from the disk
     *
     * @param path
     */
    public static void deleteFile(Path path) {

        if (path != null) {
            File file = path.toFile();
            file.delete();
        }
    }

    /**
     * Deletes the given directory from the disk
     *
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
     *
     * @param directory
     */
    public static void createDirectoryIfNotExists(File directory) {

        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Get the list of all files in the given directory
     *
     * @param directory
     * @return List<File>
     */
    public static List<File> getAllFilesInDirectory(File directory) {

        List<File> filePaths = new ArrayList<>();

        if (directory == null || !directory.exists()) {
            return filePaths;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return filePaths;
        }

        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file);
            } else if (file.isDirectory()) {
                filePaths.addAll(getAllFilesInDirectory(file));
            }
        }

        return filePaths;
    }

    /**
     * Checks whether the given file exist or not
     *
     * @param outputPath
     * @return boolean
     */
    public static boolean fileExist(String outputPath) {

        File file = new File(outputPath);
        return file.exists();
    }

    /**
     * Get the size of the given video file
     *
     * @param videoFile
     * @return
     */
    public static long getFileSize(MultipartFile videoFile) {

        return videoFile.getSize();
    }
}
