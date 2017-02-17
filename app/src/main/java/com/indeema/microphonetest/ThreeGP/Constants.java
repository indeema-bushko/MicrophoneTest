package com.indeema.microphonetest.ThreeGP;

/**
 * Created by Kostiantyn Bushko on 2/3/17.
 */

public final class Constants {

    private Constants() {}

    /**
     * AMR available bit rates for frame. The array index corresponds to CMR (Code Mode Request).
     */
    public static float AMR_NB_BITRATE[] = new float[] {

            4.75f,  // Mode 0: Encodes at 4.75 kbit/s
            5.15f,  // Mode 1: Encodes at 5.15 kbit/s
            5.90f,  // Mode 2: Encodes at 5.9  kbit/s
            6.70f,  // Mode 3: Encodes at 6.7  kbit/s
            7.40f,  // Mode 4: Encodes at 7.4  kbit/s
            7.95f,  // Mode 5: Encodes at 7.95 kbit/s
            10.20f, // Mode 6: Encodes at 10.2 kbit/s
            12.20f, // Mode 7: Encodes at 12.2 kbit/s
    };

    /**
     * Frame size in bytes. The index of array corresponds to CMR (Code Mode Request).
     */
    public static int AMR_NB_FRAME_SIZE[] = new int[] {
            13, // CRM: 0; MODE: AMR 4.75 13
            14, // CRM: 1; MODE: AMR 5.15 14
            16, // CRM: 2; MODE: AMR 5.9 16
            18, // CRM: 3; MODE: AMR 6.7 18
            20, // CRM: 4; MODE: AMR 7.4 20
            21, // CRM: 5; MODE: AMR 7.95 21
            27, // CRM: 6; MODE: AMR 10.2 27
            32, // CRM: 7; MODE: AMR 12.2 32
    };
}
