package compress.implementation;

import compress.Compressor;

import java.util.Map;

class HuffmanTextCompressor implements Compressor<String> {

    private HuffmanTextDictionary dictionaryComputer = new HuffmanTextDictionary();

    @Override
    public byte[] compress(String s) {
        Map<Character, byte[]> dictionary = dictionaryComputer.compute(s);


        return new byte[0];
    }b

    @Override
    public String decompress(byte[] compressed) {
        return null;
    }
}
