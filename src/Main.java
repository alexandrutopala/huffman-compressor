import compress.Compressor;
import compress.implementation.Compressors;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) throws IOException {

        String command = args[0].trim();
        Path src = Paths.get(args[1].trim());
        Path dest = Paths.get(args[2].trim());

        checkPaths(src, dest);

        switch (command.toUpperCase()) {
            case "COMPRESS":
                compress(src, dest);
                break;
            case "DECOMPRESS":
                decompress(src, dest);
                break;
            default: throw new IllegalArgumentException("Command not supported: " + command);
        }
    }

    private static void checkPaths(Path src, Path dest) throws IOException {
        src.toRealPath();

        Path destFolder = dest.getParent();

        Files.createDirectories(destFolder);
    }

    private static void compress(Path src, Path dest) throws IOException {
        byte[] byteContent = Files.readAllBytes(src);
        String content = new String(byteContent, Charset.forName("UTF-8"));

        Compressor<String> compressor = Compressors.getFor(String.class);

        byte[] encoded = compressor.compress(content);

        Files.write(dest, encoded, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        printStats(byteContent.length, encoded.length);
    }

    private static void decompress(Path src, Path dest) throws IOException {
        byte[] encoded = Files.readAllBytes(src);

        Compressor<String> compressor = Compressors.getFor(String.class);

        String decoded = compressor.decompress(encoded);
        byte[] content = decoded.getBytes(Charset.forName("UTF-8"));

        Files.write(dest, content, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        printStats(content.length, encoded.length);
    }

    private static void printStats(long decompressed, long compressed) {
        System.out.println("Decompressed: " + decompressed + " bytes");
        System.out.println("Compressed:   " + compressed + " bytes\n");

        double ratio = (double) compressed * 100 / decompressed;

        System.out.printf("Compression ration: %.2f%%\n", ratio);
    }
}