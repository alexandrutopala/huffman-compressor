package compress.util;

import java.util.Arrays;
import java.util.Objects;

public class ByteArray {
    private byte[] array;

    public ByteArray(byte[] array) {
        this.array = Objects.requireNonNull(array);
    }

    public byte[] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteArray byteArray = (ByteArray) o;
        return Arrays.equals(array, byteArray.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}
