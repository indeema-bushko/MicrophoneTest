package com.indeema.microphonetest.AMR;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostiantyn Bushko on 2/2/17.
 */

public class ThreeGPHelper {

    private static final String TAG = ThreeGPHelper.class.getSimpleName();

    public static List<DataChunk> ParseThreeGPFile(byte[] byteArray) {
        int offset = 0;
        List<DataChunk>dataChunkList = new ArrayList<>();
        while (offset <= byteArray.length) {
            Log.d(TAG, "ParseThreeGPFile offset = " + offset);
            DataChunk dataChunk = new DataChunk();
            offset = dataChunk.setData(byteArray, offset);
            Log.d(TAG, "ParseThreeGPFile chunk = " + dataChunk.toString());
            dataChunkList.add(dataChunk);
        }
        return dataChunkList;
    }
}
