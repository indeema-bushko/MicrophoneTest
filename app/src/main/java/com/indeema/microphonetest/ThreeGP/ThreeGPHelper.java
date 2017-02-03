package com.indeema.microphonetest.ThreeGP;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Helper class for work with 3gp format.
 *
 * Created by Kostiantyn Bushko on 2/2/17.
 */

public class ThreeGPHelper {

    private static final String TAG = ThreeGPHelper.class.getSimpleName();

    /**
     * Header size contains four bytes of block size and four bytes for type.
     */
    private static final int HEADER_SIZE = 8;

    /**
     * A header type of AMR block data.
     */
    public static final char[] HEADER_TYPE_AMR = new char[] { 'm', 'd', 'a', 't' };


    /**
     * A header block of bytes for .amr file.
     */
    public static final byte[] AMR_MAGIC_HEADER = new byte[] {0x23, 0x21, 0x41, 0x4d, 0x52, 0x0a};


    /**
     * Extract block data for specific type.
     * @param inputByteArray
     * @return
     */
    public static byte[] ExtractRawDataBlockByType(byte[] inputByteArray, char[] headerType) {
        int cursor = 0;
        int count = 0;
        byte[] dataChunk = null;
        while (cursor < inputByteArray.length) {
            if (count == headerType.length) {
                int size = (int)DataHelper.readUint32(inputByteArray, (cursor - headerType.length - 4));
                size -= HEADER_SIZE;
                dataChunk = new byte[size];
                System.arraycopy(inputByteArray, cursor, dataChunk, 0, size);
                Log.d(TAG,"CHUNK SIZE = " + dataChunk.length);
                break;
            }
            if (inputByteArray[cursor] == (byte) headerType[count]) {
                cursor ++;
                count ++;
            } else {
                cursor ++;
                count = 0;
            }
        }
        return dataChunk;
    }

    /**
     * Create AMR file from raw data and save to disk.
     *
     * @param rawData an byte array contain raw data.
     * @param filePath a full path with file name and .amr extension when data should be stored.
     * @return return true if file success created.
     */
    public static boolean CreateAmrFile(byte[] rawData, String filePath) {
        int dataLength = AMR_MAGIC_HEADER.length + rawData.length;
        byte[] amrData = new byte[dataLength];

        System.arraycopy(AMR_MAGIC_HEADER, 0, amrData, 0, AMR_MAGIC_HEADER.length);
        System.arraycopy(rawData, 0, amrData, AMR_MAGIC_HEADER.length, rawData.length);

        File amrFile = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(amrFile);
            fileOutputStream.write(amrData);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
