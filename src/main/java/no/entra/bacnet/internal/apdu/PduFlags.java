package no.entra.bacnet.internal.apdu;

import static no.entra.bacnet.utils.HexUtils.isBitSet;

/*
 PduFlag is an binary represented by 4 bits
 abcd
 a = Segmented Request
 b = More Segments Follow
 c = Segmented Response Accepted.
 d =
 The position is abcd = 3210
 */
public class PduFlags {

    public static boolean isSegmented(char pduFlag) {
        return isBitSet(pduFlag, 3);
    }

    public static boolean hasMoreSegments(char pduFlag) {
        return isBitSet(pduFlag, 2);
    }

    public static boolean willAcceptSegmentedResponse(char pduFlag) {
        return isBitSet(pduFlag,1);
    }

    public static boolean isServer(char pduFlag) {
        return isBitSet(pduFlag, 0);
    }
}
