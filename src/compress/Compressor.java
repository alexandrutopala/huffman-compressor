package compress;

public interface Compressor<T> {

    byte[] compress(T t);

    T decompress(byte[] compressed);

    static Byte[] wrap(byte[] arr) {
        Byte[] wrapped = new Byte[arr.length];

        for (int i = 0; i < arr.length; i++) {
            wrapped[i] = arr[i];
        }

        return wrapped;
    }

    static byte[] unwrap(Byte[] arr) {
        byte[] unwrapped = new byte[arr.length];

        for (int i = 0; i < arr.length; i++) {
            unwrapped[i] = arr[i];
        }

        return unwrapped;
    }
}
