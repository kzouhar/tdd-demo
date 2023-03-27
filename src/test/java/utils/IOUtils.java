package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class IOUtils {

    public static File getTestFile(String path) {
        return Paths.get("src", "test", "resources", path).toFile();
    }

    public static InputStream getTestInputStream(String path) throws IOException {
        return Files.newInputStream(Paths.get("src", "test", "resources", path));
    }

    public static String readTestFileContent(String path) throws IOException {
        return Files.readString(Paths.get("src", "test", "resources", path));
    }

    public static String readAndTrimTestFileContent(String path) throws IOException {
        return Files.readAllLines(Paths.get("src", "test", "resources", path)).
                stream().
                map(String::trim).
                collect(Collectors.joining());
    }

}
