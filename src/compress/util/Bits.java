package compress.util;

import java.nio.ByteBuffer;

public final class Bits {
    private Bits() {
    }

    /**
     * Compressing an array of bytes that ar either 0 or 1 into bits.
     * Each byte will be represented as a bit.
     * The first 4 bytes in the array represents the number of bytes
     * encoded into bits. Those are followed by the encoded bytes
     *
     * @param arr raw byte array
     * @return compressed array
     * @throws IllegalArgumentException if any byte is different from 0 or 1
     */
    public static byte[] encode(byte[] arr) {
        checkValues(arr);

        int capacity =
                arr.length / 8 +
                        (arr.length % 8 != 0 ? 1 : 0);

        byte[] compressed = new byte[capacity];

        for (int i = 0; i < compressed.length; i++) {
            byte currentByte = 0;

            for (int j = 0; j < 8 && j + i * 8 < arr.length; j++) {
                int index = i * 8 + j;
                byte value = (byte) (arr[index] << j);

                currentByte |= value;
            }

            compressed[i] = currentByte;
        }

        ByteBuffer buffer = ByteBuffer.allocate(4 + compressed.length);

        buffer.putInt(arr.length);
        buffer.put(compressed);

        return buffer.array();
    }

    private static void checkValues(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0 && arr[i] != 1) {
                throw new IllegalArgumentException("Elements must be either 0 or 1");
            }
        }
    }


    /**
     * Decodes an array of bytes in which every bit will be converted
     * into a byte.
     * The method expects that the very first 4 bytes in the array are
     * containing the total number of bits to be decoded
     *
     * @param arr bit-encoded byte array
     * @return decoded byte array
     */
    public static byte[] decode(byte[] arr) {
        ByteBuffer buffer = ByteBuffer.wrap(arr);

        int bytesCount = buffer.getInt();

        ByteBuffer dest = ByteBuffer.allocate(bytesCount);

        int octets = bytesCount / 8 +
                (bytesCount % 8 != 0 ? 1 : 0);

        for (int i = 0; i < octets; i++) {
            byte octet = buffer.get();

            for (int j = 0; j < 8 && j + i * 8 < bytesCount; j++) {
                byte value = (byte) (octet & 1);

                dest.put(value);

                octet >>= 1;
            }
        }

        return dest.array();
    }
}
