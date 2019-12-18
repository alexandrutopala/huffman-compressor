package compress.implementation;

import compress.Compressor;

class HuffmanTextCompressor<T> implements Compressor<String> {

    private HuffmanTextDictionary dictionary = new HuffmanTextDictionary();

    @Override
    public byte[] compress(String s) {
        return new byte[0];
    }

    @Override
    public String decompress(byte[] compressed) {
        return null;
    }
}
