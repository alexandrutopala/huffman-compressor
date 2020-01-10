package test;

import com.sun.media.sound.InvalidDataException;
import main.Main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Test6 {

    // the content to be compressed
    private static final String TEST_TEXT = "// Test 6 : Text simplu cu multe caractere : " + "Huffman coding\n" +
            "In computer science and information theory, a Huffman code is a particular type of optimal prefix code that is commonly used for lossless data compression. " +
            "The process of finding or using such a code proceeds by means of Huffman coding, an algorithm developed by David A. Huffman while he was a Sc.D. student at MIT, and published in the 1952 paper \"A Method for the Construction of Minimum-Redundancy Codes\".\n" +
            "The output from Huffman's algorithm can be viewed as a variable-length code table for encoding a source symbol (such as a character in a file). The algorithm derives this table from the estimated probability or frequency of occurrence (weight) for each possible value of the source symbol. As in other entropy encoding methods, more common symbols are generally represented using fewer bits than less common symbols. Huffman's method can be efficiently implemented, finding a code in time linear to the number of input weights if these weights are sorted. However, although optimal among methods encoding symbols separately, Huffman coding is not always optimal among all compression methods. "
            + "Decompression\n" +
            "Generally speaking, the process of decompression is simply a matter of translating the stream of prefix codes to individual byte values, usually by traversing the Huffman tree node by node as each bit is read from the input stream (reaching a leaf node necessarily terminates the search for that particular byte value). Before this can take place, however, the Huffman tree must be somehow reconstructed. In the simplest case, where character frequencies are fairly predictable, the tree can be preconstructed (and even statistically adjusted on each compression cycle) and thus reused every time, at the expense of at least some measure of compression efficiency. " +
            "Otherwise, the information to reconstruct the tree must be sent a priori. A naive approach might be to prepend the frequency count of each character to the compression stream. Unfortunately, the overhead in such a case could amount to several kilobytes, so this method has little practical use. If the data is compressed using canonical encoding, the compression model can be precisely reconstructed with just bits of information (where is the number of bits per symbol). Another method is to simply prepend the Huffman tree, bit by bit, to the output stream. \n " +
            "For example, assuming that the value of 0 represents a parent node and 1 a leaf node, whenever the latter is encountered the tree building routine simply reads the next 8 bits to determine the character value of that particular leaf. The process continues recursively until the last leaf node is reached; at that point, the Huffman tree will thus be faithfully reconstructed. The overhead using such a method ranges from roughly 2 to 320 bytes (assuming an 8-bit alphabet). \n" +
            "Many other techniques are possible as well. In any case, since the compressed data can include unused \"trailing bits\" the decompressor must be able to determine when to stop producing output. This can be accomplished by either transmitting the length of the decompressed data along with the compression model or by defining a special code symbol to signify the end of input (the latter method can adversely affect code length optimality, however). " +
            "Compression\n" +
            "The technique works by creating a binary tree of nodes. These can be stored in a regular array, the size of which depends on the number of symbols, {\\displaystyle n}n. A node can be either a leaf node or an internal node. Initially, all nodes are leaf nodes, which contain the symbol itself, the weight (frequency of appearance) of the symbol and optionally, a link to a parent node which makes it easy to read the code (in reverse) starting from a leaf node. Internal nodes contain a weight, links to two child nodes and an optional link to a parent node. As a common convention, bit '0' represents following the left child and bit '1' represents following the right child. A finished tree has up to {\\displaystyle n}n leaf nodes and {\\displaystyle n-1}n-1 internal nodes. A Huffman tree that omits unused symbols produces the most optimal code lengths.\n" +
            "\n" +
            "The process begins with the leaf nodes containing the probabilities of the symbol they represent. Then, the process takes the two nodes with smallest probability, and creates a new internal node having these two nodes as children. The weight of the new node is set to the sum of the weight of the children. We then apply the process again, on the new internal node and on the remaining nodes (i.e., we exclude the two leaf nodes), we repeat this process until only one node remains, which is the root of the Huffman tree. "; // TODO: change that

    // the file you want to compress
    private static final String DECOMPRESSED_PATH = "./tests/text6.txt"; // TODO: change that

    // the destination file for the compressed text
    private static final String COMPRESSED_PATH = "./tests/compressed6.txt"; // TODO: change that

    // the result file with the decompressed text
    private static final String RESULT_PATH = "./tests/rez6.txt"; // TODO: change that

    public static void main(String[] args) throws IOException {
        generateTestFile(DECOMPRESSED_PATH, TEST_TEXT);

        System.out.println("Test 6 \n");
        System.out.println("Operatia care incepe este compresia.");
        long startTime = System.nanoTime();
        Main.main("COMPRESS", DECOMPRESSED_PATH, COMPRESSED_PATH);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  // milisecunde
        System.out.print("Compresia a durat : " + duration + " milisecunde.");
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
