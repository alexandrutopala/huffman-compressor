package compress;

public interface Compressor<T> {

    byte[] compress(T t);

    T decompress(byte[] compressed);
}
