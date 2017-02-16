package com.indeema.microphonetest.ThreeGP;

/**
 * Created by Kostiantyn Bushko on 2/3/17.
 */

public class AudioFrame {

    public byte frameHeader;

    public byte[] frameData;

    public AudioFrame(int frameSize) {
        frameData = new byte[frameSize];
    }

    public int getFrameSize() {
        if (frameData == null)
            return -1;
        return frameData.length;
    }
}
