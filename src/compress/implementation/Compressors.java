package compress.implementation;

import compress.Compressor;

public final class Compressors {

    private Compressors() {
    }

    public static <T> Compressor<T> getFor(Class<T> clazz) {
        if (clazz == String.class) {
            return (Compressor<T>) new HuffmanTextCompressor();
        } else {
            throw new IllegalArgumentException("Not supported");
        }
    }
}
