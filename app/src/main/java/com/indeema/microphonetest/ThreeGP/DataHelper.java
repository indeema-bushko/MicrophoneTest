package com.indeema.microphonetest.ThreeGP;

import java.io.ByteArrayInputStream;

/**
 * Created by Kostiantyn Bushko on 2/2/17.
 */

public final class DataHelper {

    private DataHelper() {}

    public static long readUint32(ByteArrayInputStream bis) {
        long result = 0;
        result += ((long) readUInt16(bis)) << 16;
        result += readUInt16(bis);
        return result;
    }

    public static int readUInt16(ByteArrayInputStream bis) {
        long result = 0;
        result += bis.read() << 8;
        result += bis.read();
        return (int) result;
    }

    public static long readUint32(byte[] data, int offset) {
        long result = 0;
        result += readUInt16(data, offset) << 16;
        result += readUInt16(data, offset + 2);
        return result;
    }

    public static int readUInt16(byte[] data, int offset) {
        long result = 0;
        result += data[offset] << 8;
        result += data[offset + 1];
        return (int) result;
    }
}
