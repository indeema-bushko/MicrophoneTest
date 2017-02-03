package com.indeema.microphonetest.ThreeGP;

/**
 * Created by Kostiantyn Bushko on 2/2/17.
 */

public class DataChunk {

    private String mTypeName = "unknown";
    private int mSize;
    private int mType;

    public byte[] mData;

    public DataChunk() { }

    public int setData(byte[] data, int offset) {
        char[] chars = new char[] { (char)data[offset + 4], (char)data[offset + 5], (char)data[offset + 6], (char)data[offset + 7] };
        mTypeName = new String(chars);
        mSize = (int)DataHelper.readUint32(data, offset);
        mType = (int)DataHelper.readUint32(data, offset + 4);
        mData = new byte[mSize];
        System.arraycopy(data, offset, mData, 0, mSize);
        return offset + mSize;
    }

    @Override
    public String toString() {
        String string = this.getClass().getSimpleName() + " : "
                + "mTypeName = " + mTypeName
                + ", mSize = " + mSize + "(bytes)"
                + ", mType = " + mType
                + ", mData = " + mData.toString();
        return string;
    }
}
