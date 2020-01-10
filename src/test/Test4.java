package test;

import com.sun.media.sound.InvalidDataException;
import main.Main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Test4 {

    // the content to be compressed
    private static final String TEST_TEXT = "// Test 4 : ACUM TESTAM TEXTE MARI : \n" + "  ____    _             _                   _   \n" +
            " | __ )  (_)   __ _    | |_    ___  __  __ | |_ \n" +
            " |  _ \\  | |  / _` |   | __|  / _ \\ \\ \\/ / | __|\n" +
            " | |_) | | | | (_| |   | |_  |  __/  >  <  | |_ \n" +
            " |____/  |_|  \\__, |    \\__|  \\___| /_/\\_\\  \\__|\n" +
            "              |___/                              " ; // TODO: change that

    // the file you want to compress
    private static final String DECOMPRESSED_PATH = "./tests/text4.txt"; // TODO: change that

    // the destination file for the compressed text
    private static final String COMPRESSED_PATH = "./tests/compressed4.txt"; // TODO: change that

    // the result file with the decompressed text
    private static final String RESULT_PATH = "./tests/rez4.txt"; // TODO: change that

    public static void main(String[] args) throws IOException {
        generateTestFile(DECOMPRESSED_PATH, TEST_TEXT);

        System.out.println("Test 4 \n");
        System.out.println("Operatia care incepe este compresia.");
        long startTime = System.nanoTime();
        Main.main("COMPRESS", DECOMPRESSED_PATH, COMPRESSED_PATH);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  // milisecunde
        System.out.println("Compresia a durat : " + duration + " milisecunde.");
        System.out.println("\n");

        System.out.println("Operatia care incepe este decompresia");
        long startTime2 = System.nanoTime();
        Main.main("DECOMPRESS", COMPRESSED_PATH, RESULT_PATH);
        long endTime2 = System.nanoTime();
        long duration2 = (endTime2 - startTime2)/1000000;  // milisecunde
        System.out.println("Decompresia a durat : " + duration2 + " milisecunde.");
        System.out.println("\n");


        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is : " + memory + " bytes");

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

