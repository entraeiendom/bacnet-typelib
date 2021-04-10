package no.entra.bacnet.apdu;

import no.entra.bacnet.internal.apdu.MessageType;
import no.entra.bacnet.services.ServiceChoice;

public class Apdu {

    private final MessageType messageType; //PDU type in spec. First char in ApduHexString
    private final char applicationControlBits; //Second char in ApduHexString//2 = unsegmented request, no more segments, segmented response accepted

    private final char maxSegmentsAccepted; //Used in BacnetRequest7 = more than 64 accepted
    private final char maxApduLengthAccepted; // Used in BacnetRequest. 5 = up to 1476 octets

    //Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response will
    // match this invokeId.
    private int invokeId;

    //Eg WhoIs, IAm, ReadPropertiesMultiple
    private ServiceChoice serviceChoice;

    private boolean isSegmented;
    private boolean isSegmentedReplyAllowed;
    private boolean hasMoreSegments;


    public Apdu(MessageType messageType, char applicationControlBits, char maxSegmentsAccepted, char maxApduLengthAccepted) {
        this.messageType = messageType;
        this.applicationControlBits = applicationControlBits;
        this.maxSegmentsAccepted = maxSegmentsAccepted;
        this.maxApduLengthAccepted = maxApduLengthAccepted;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public char getApplicationControlBits() {
        return applicationControlBits;
    }

    public char getMaxSegmentsAccepted() {
        return maxSegmentsAccepted;
    }

    public char getMaxApduLengthAccepted() {
        return maxApduLengthAccepted;
    }

    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    public ServiceChoice getServiceChoice() {
        return serviceChoice;
    }

    public void setServiceChoice(ServiceChoice serviceChoice) {
        this.serviceChoice = serviceChoice;
    }

    public boolean isSegmented() {
        return isSegmented;
    }

    public void setSegmented(boolean segmented) {
        isSegmented = segmented;
    }

    public boolean isSegmentedReplyAllowed() {
        return isSegmentedReplyAllowed;
    }

    public void setSegmentedReplyAllowed(boolean segmentedReplyAllowed) {
        isSegmentedReplyAllowed = segmentedReplyAllowed;
    }

    public boolean isHasMoreSegments() {
        return hasMoreSegments;
    }

    public void setHasMoreSegments(boolean hasMoreSegments) {
        this.hasMoreSegments = hasMoreSegments;
    }

    public String toHexString() {
        return "" + messageType.getPduTypeChar() + applicationControlBits + maxSegmentsAccepted + maxApduLengthAccepted;
    }

    public static final class ApduBuilder {
        private MessageType apduType;//Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response will
        private char pduFlags; //2 = unsegmented request, no more segments, se//Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response willgmented response accepted
        private char maxSegmentsAccepted; //7 = more than 64 accepted
        private char maxApduLengthAccepted; // 5 = up to 1476 octets
        private boolean segmented;//Used for all B//Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response willacnetRequest that require a response. See ConfirmedServiceRequest. Response will
        private boolean hasMoreSegments;
        private boolean segmentedReplyAllowed;

        private ApduBuilder() {
        }
//Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response will
        public static ApduBuilder builder() {
            return new ApduBuilder();
        }

        public Apdu build() {
                mapPduFlags();
            Apdu apdu = new Apdu(apduType, pduFlags, maxSegmentsAccepted, maxApduLengthAccepted);
            apdu.isSegmented = segmented;
            apdu.hasMoreSegments = hasMoreSegments;
            apdu.isSegmentedReplyAllowed = segmentedReplyAllowed;
            return apdu;
        }
        protected void mapPduFlags() {
            if (!segmented && !hasMoreSegments && segmentedReplyAllowed) {
                pduFlags = '2';
            }
        }

        public ApduBuilder withApduType(MessageType apduType) {
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
