package test;

import com.sun.media.sound.InvalidDataException;
import main.Main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Test1 {

    // the content to be compressed
    private static final String TEST_TEXT = " !"; // TODO: change that

    // the file you want to compress
    private static final String DECOMPRESSED_PATH = "./tests/text1.txt"; // TODO: change that

    // the destination file for the compressed text
    private static final String COMPRESSED_PATH = "./tests/compressed1.txt"; // TODO: change that

    // the result file with the decompressed text
    private static final String RESULT_PATH = "./tests/rez1.txt"; // TODO: change that

    public static void main(String[] args) throws IOException {
        generateTestFile(DECOMPRESSED_PATH, TEST_TEXT);

        Main.main("COMPRESS", DECOMPRESSED_PATH, COMPRESSED_PATH);
        Main.main("DECOMPRESS", COMPRESSED_PATH, RESULT_PATH);

        String result = getContentFromResultFile(RESULT_PATH);

        if (!TEST_TEXT.equals(result)) {
            throw new InvalidDataException("String");
        }
    }

    private static void generateTestFile(String path, String text) throws IOException {
        Path dest = Paths.get(path);

        byte[] content = text.getBytes(Charset.forName("UTF-8"));

        Files.createDirectories(dest.getParent());

        Files.write(dest, content, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String getContentFromResultFile(String resultFilePath) throws IOException {
        Path src = Paths.get(resultFilePath);

        byte[] byteContent = Files.readAllBytes(src);

        return new String(byteContent, Charset.forName("UTF-8"));
    }
}
