package compress.implementation;

import compress.Compressor;
import compress.util.ByteArray;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
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
        ByteBuffer buffer = ByteBuffer.wrap(compressed).asReadOnlyBuffer();

        Map<ByteArray, Character> dictionary = decodeDictionary(buffer);

        return decodeMessage(buffer, dictionary);
    }

    private Map<ByteArray, Character> decodeDictionary(ByteBuffer buffer) {
        int entries = buffer.getShort();

        Map<ByteArray, Character> dictionary = new HashMap<>(entries);

        for (int i = 0; i < entries; i++) {
            Character symbol = buffer.getChar();
            short bytesCount = buffer.getShort();

            byte[] bytes = new byte[bytesCount];
            buffer.get(bytes);

            dictionary.put(new ByteArray(bytes), symbol);
        }

        return dictionary;
    }

    private String decodeMessage(ByteBuffer buffer, Map<ByteArray, Character> dictionary) {
        StringBuilder builder = new StringBuilder();
        byte[] encoded = new byte[1];

        while (buffer.hasRemaining()) {
            encoded[encoded.length - 1] = buffer.get();

            Character symbol = dictionary.get(new ByteArray(encoded));

            if (symbol != null) {
                builder.append(symbol);
                encoded = new byte[1];
            } else {
                encoded = Arrays.copyOf(encoded, encoded.length + 1);
            }
        }

        return builder.toString();
    }
}
