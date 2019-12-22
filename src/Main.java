import compress.Compressor;
import compress.implementation.Compressors;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String test = "Ana are mere";

        Compressor<String> compressor = Compressors.getFor(String.class);

        byte[] encoded = compressor.compress(test);

        System.out.println(Arrays.toString(encoded));

        String decoded = compressor.decompress(encoded);

        System.out.println(decoded);
    }
}
