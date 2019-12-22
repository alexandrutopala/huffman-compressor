package compress.implementation;

import compress.Compressor;
import compress.Type;

public final class Compressors {

    private Compressors() {
    }

    public static <T> Compressor<T> get(Type type) {
        switch (type) {
            case HUFFMAN_TEXT: //return new HuffmanTextCompressor();
            default: throw new IllegalArgumentException("Not supported");
        }
    }
}
