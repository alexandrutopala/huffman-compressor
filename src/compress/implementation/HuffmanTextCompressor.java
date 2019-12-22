package compress.implementation;

import compress.Compressor;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;

class HuffmanTextCompressor implements Compressor<String> {

    private HuffmanTextDictionary dictionaryComputer = new HuffmanTextDictionary();

    @Override
    public byte[] compress(String message) {
        Map<Character, byte[]> dictionary = dictionaryComputer.compute(message);

        byte[] encodedDictionary = encodeDictionary(dictionary);
        byte[] encodedMessage = encodeMessage(message, dictionary);

        ByteBuffer buffer = ByteBuffer.allocate(encodedDictionary.length + encodedMessage.length);

        buffer.put(encodedDictionary);
        buffer.put(encodedMessage);

        return buffer.array();
    }

    private byte[] encodeMessage(String message, Map<Character, byte[]> dictionary) {
        Byte[] encoded = message.chars()
                .mapToObj(letter -> (char) letter)
                .map(dictionary::get)
                .map(Compressor::wrap)
                .flatMap(Arrays::stream)
                .toArray(Byte[]::new);

        return Compressor.unwrap(encoded);
    }

    private byte[] encodeDictionary(Map<Character, byte[]> dictionary) {
        int capacity = computeBufferCapacity(dictionary);

        ByteBuffer buffer = ByteBuffer.allocate(capacity);

        buffer.putShort((short) dictionary.size());

        dictionary.forEach((symbol, bytes) -> {
            buffer.putChar(symbol);
            buffer.putShort((short) bytes.length);
            buffer.put(bytes);
        });

        return buffer.array();
    }

    private int computeBufferCapacity(Map<Character, byte[]> dicitonary) {
        return 2 // for total entries count (represented on 2B)
             + dicitonary.size() * 2 // for byte counter for each symbol (represented on 2B)
             + dicitonary.size() * 2 // for each encoded symbol (represented on 2B)
             + dicitonary.values().stream().mapToInt(arr -> arr.length).sum(); // for effective encoding byte
    }

    @Override
    public String decompress(byte[] compressed) {
        return null;
    }
}
