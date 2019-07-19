package com.elk.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

final class FileUtil {

    private FileUtil() {}

    static File createTempFile(String fileName) {
        try {
            return File.createTempFile(fileName, ".tmp");
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error occurred while creating tmp file %s", fileName), e);
        }
    }

    static File writeToFile(File file, String text) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(text);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error occurred while writing into file %s", file.getName()), e);
        }
        return file;
    }
}
