package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.octet.Octet;

import static java.lang.Integer.parseInt;

public enum AbortReason {
    othEr(0),
    BufferOverflow(1),
    InvalidApduInThisState(2),
    PreemptedByHigherPriorityTask(3),
    SegmentationNotSupported(4),
    SecurityError(5),
    InsufficientSecurity(6),
    WindowSizeOutOfRange(7),
    ApplicationExceededReplyTime(8),
    OutOfResources(9),
    TsmTimeout(10),
    ApduTooLong(11);
    private int AbortReasonInt;

    public static AbortReason fromAbortReasonInt(int AbortReasonInt) {
        for (AbortReason type : values()) {
            if (type.getAbortReasonInt() == AbortReasonInt) {
                return type;
            }
        }
        return null;
    }

    public static AbortReason fromChar(char nibble) throws NumberFormatException {
        int abortReasonInt = parseInt(String.valueOf(nibble), 16);
        AbortReason abortReason = fromAbortReasonInt(abortReasonInt);
        return abortReason;
    }

    public static AbortReason fromOctet(Octet AbortReasonOctet) throws NumberFormatException {
        if (AbortReasonOctet == null) {
            return null;
        }
        Integer abortReasonInt = parseInt(AbortReasonOctet.toString(), 16);
        AbortReason abortReason = fromAbortReasonInt(abortReasonInt.intValue());
        return abortReason;
    }


    public int getAbortReasonInt() {
        return AbortReasonInt;
    }

    // enum constructor - cannot be public or protected
    private AbortReason(int AbortReasonInt) {
        this.AbortReasonInt = AbortReasonInt;
    }
}
