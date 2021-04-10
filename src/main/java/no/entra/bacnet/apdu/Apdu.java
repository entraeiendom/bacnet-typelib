package no.entra.bacnet.apdu;

import no.entra.bacnet.internal.apdu.ApduType;

public class Apdu {

    private final ApduType apduType;
    private final char pduFlags; //2 = unsegmented request, no more segments, segmented response accepted

    private final char maxSegmentsAccepted; //7 = more than 64 accepted
    private final char maxApduLengthAccepted; // 5 = up to 1476 octets

    public Apdu(ApduType apduType, char pduFlags, char maxSegmentsAccepted, char maxApduLengthAccepted) {
        this.apduType = apduType;
        this.pduFlags = pduFlags;
        this.maxSegmentsAccepted = maxSegmentsAccepted;
        this.maxApduLengthAccepted = maxApduLengthAccepted;
    }

    public String toHexString() {
        return "" + apduType.getPduTypeChar() + pduFlags + maxSegmentsAccepted + maxApduLengthAccepted;
    }

    public static final class ApduBuilder {
        private ApduType apduType;
        private char pduFlags; //2 = unsegmented request, no more segments, segmented response accepted
        private char maxSegmentsAccepted; //7 = more than 64 accepted
        private char maxApduLengthAccepted; // 5 = up to 1476 octets
        private boolean segmented;
        private boolean hasMoreSegments;
        private boolean segmentedReplyAllowed;

        private ApduBuilder() {
        }

        public static ApduBuilder builder() {
            return new ApduBuilder();
        }

        public Apdu build() {
                mapPduFlags();
            return new Apdu(apduType, pduFlags, maxSegmentsAccepted, maxApduLengthAccepted);
        }
        protected void mapPduFlags() {
            if (!segmented && !hasMoreSegments && segmentedReplyAllowed) {
                pduFlags = '2';
            }
        }

        public ApduBuilder withApduType(ApduType apduType) {
            this.apduType = apduType;
            return this;
        }

        public ApduBuilder withPduFlags(char pduFlags) {
            this.pduFlags = pduFlags;
            return this;
        }

        public ApduBuilder withMaxSegmentsAccepted(char maxSegmentsAccepted) {
            this.maxSegmentsAccepted = maxSegmentsAccepted;
            return this;
        }

        public ApduBuilder withMaxApduLengthAccepted(char maxApduLengthAccepted) {
            this.maxApduLengthAccepted = maxApduLengthAccepted;
            return this;
        }


        public ApduBuilder isSegmented(boolean segmented) {
            this.segmented = segmented;
            return this;
        }

        public ApduBuilder hasMoreSegments(boolean hasMoreSegments) {
            this.hasMoreSegments = hasMoreSegments;
            return this;
        }

        public ApduBuilder isSegmentedReplyAllowed(boolean segmentedReplyAllowed) {
            this.segmentedReplyAllowed = segmentedReplyAllowed;
            return this;
        }

        public ApduBuilder withMaxApduLength1476() {
            this.maxApduLengthAccepted = '5';
            return this;
        }

        public ApduBuilder withMaxSegmentsAcceptedAbove64() {
            this.maxSegmentsAccepted = '7';
            return this;
        }
    }
}
