package com.indeema.microphonetest.ThreeGP;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostiantyn Bushko on 2/3/17.
 */

public final class AmpParser {

    private static final String TAG = AmpParser.class.getSimpleName();

    private AmpParser() {}

    /**
     * Parse AMP data byte array to extract frames.</br>
     *
     * @param ampByteArray a byte array contains data from AMP file includes AMP header.
     * @return list of audio frames.
     */
    public static final List<AudioFrame> ParseAmpData(byte[] ampByteArray) {
        List<AudioFrame>audioFrameList = new ArrayList<>();
        Log.d(TAG, "Parse AMP data: size = " + ampByteArray.length);
        /**
         * Set cursor to first byte of the data except amp file header.</br>
         * The cursor will points to the first byte of the first frame.
         */
        int cursor = 0; //ThreeGPHelper.AMR_MAGIC_HEADER.length;
        while (cursor < ampByteArray.length) {
            /**
             * Get first byte in the current frame. Header byte contain CMR (Code Mode Request),</br>
             * determine the bitrate and size for current frame.
             */
            byte frameHeader = ampByteArray[cursor];
            int codecModeRequest = (frameHeader >> 3) & 0x0F;
            float bitrate = Constants.AMP_NB_BITRATE[codecModeRequest];
            int frameSize = Constants.AMP_NB_FRAME_SIZE[codecModeRequest];
            Log.d(TAG, "Cursor = " + cursor);
            Log.d(TAG, " - CRM = " + codecModeRequest);
            Log.d(TAG, " - bitrate = " + bitrate);
            Log.d(TAG, " - frameSize = " + frameSize);

            AudioFrame frame = new AudioFrame(frameSize - 1);
            frame.frameHeader = ampByteArray[cursor];
            System.arraycopy(ampByteArray, cursor + 1, frame.frameData, 0, frameSize - 1);

            cursor += frameSize;
        }

        return audioFrameList;
    }
}