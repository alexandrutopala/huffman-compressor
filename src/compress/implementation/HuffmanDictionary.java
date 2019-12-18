package compress.implementation;

import java.util.Map;

public interface HuffmanDictionary<T, U> {

    Map<T, byte[]> compute(U u);
}
