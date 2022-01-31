package no.entra.bacnet.apdu;

import no.entra.bacnet.internal.apdu.MessageType;
import no.entra.bacnet.services.ServiceChoice;
import no.entra.bacnet.utils.BitString;

import java.util.Objects;

import static no.entra.bacnet.utils.HexUtils.intToHexString;

public class Apdu {

    public static final int NEGATIVE_ACK_POSITION = 2;
    private static final int SENDER_IS_SERVER_POSITION = 1;
    private static final int SEGMENTED_REQUEST_POSITION = 4;
    private static final int SEGMENTED_REPLY_POSITION = 2;
    private static final int MORE_SEGMENTS_FOLLOW_POSITION = 3;
    private final MessageType messageType; //PDU type in spec. First char in ApduHexString
    private char applicationControlBits; //Second char in ApduHexString//2 = unsegmented request, no more segments, segmented response accepted

    private char maxSegmentsAccepted; //Used in BacnetRequest 7 = more than 64 accepted
    private char maxApduLengthAccepted; // Used in BacnetRequest. 5 = up to 1476 octets

    //Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response will
    // match this invokeId.
    private Integer invokeId = null;

    //Eg WhoIs, IAm, ReadPropertiesMultiple
    private ServiceChoice serviceChoice = null;

    private boolean isSegmented;
    private boolean isSegmentedReplyAllowed;
    private boolean hasMoreSegments;
    private int sequenceNumber; //TODO verify this is part of APDU, not Serviceimpl?
    private int proposedWindowSize;//TODO verify this is part of APDU, not Serviceimpl?
    private boolean senderIsServer;
    private String abortReason;
    private boolean isNegativeAck;
    private final BitString pduFlags;


    public Apdu(MessageType messageType) {
        this.messageType = messageType;
        pduFlags = new BitString(4);
    }

    public Apdu(MessageType messageType, char maxSegmentsAccepted, char maxApduLengthAccepted) {
        this.messageType = messageType;
        this.applicationControlBits = applicationControlBits;
        this.maxSegmentsAccepted = maxSegmentsAccepted;
        this.maxApduLengthAccepted = maxApduLengthAccepted;
        pduFlags = new BitString(4);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public char getMaxSegmentsAccepted() {
        return maxSegmentsAccepted;
    }

    public void setMaxSegmentsAccepted(char maxSegmentsAccepted) {
        this.maxSegmentsAccepted = maxSegmentsAccepted;
    }

    public char getMaxApduLengthAccepted() {
        return maxApduLengthAccepted;
    }

    public void setMaxApduLengthAccepted(char maxApduLengthAccepted) {
        this.maxApduLengthAccepted = maxApduLengthAccepted;
    }

    public Integer getInvokeId() {
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
        return pduFlags.isBitSet(SEGMENTED_REQUEST_POSITION);
    }

    public void setSegmented(boolean segmented) {
        if (segmented) {
            pduFlags.setBit(SEGMENTED_REQUEST_POSITION);
        } else {
            pduFlags.unsetBit(SEGMENTED_REQUEST_POSITION);
        }
    }

    public boolean isSegmentedReplyAllowed() {
        return pduFlags.isBitSet(SEGMENTED_REPLY_POSITION);
    }

    public void setSegmentedReplyAllowed(boolean segmentedReplyAllowed) {
        if (segmentedReplyAllowed) {
            pduFlags.setBit(SEGMENTED_REPLY_POSITION);
        } else {
            pduFlags.unsetBit(SEGMENTED_REPLY_POSITION);
        }
    }

    public boolean isHasMoreSegments() {
        return pduFlags.isBitSet(MORE_SEGMENTS_FOLLOW_POSITION);
    }

    public void setHasMoreSegments(boolean hasMoreSegments) {
        if (hasMoreSegments) {
            pduFlags.setBit(MORE_SEGMENTS_FOLLOW_POSITION);
        } else {
            pduFlags.unsetBit(MORE_SEGMENTS_FOLLOW_POSITION);
        }
    }

    public String toHexString() {
        String hexString = "" + messageType.getPduTypeChar() + pduFlags.toChar();
        if (maxSegmentsAccepted != '\u0000') {
            hexString = hexString + maxSegmentsAccepted;
        }
        if (maxApduLengthAccepted != '\u0000') {
            hexString = hexString + maxApduLengthAccepted;
        }
        if (messageType.equals(MessageType.ConfirmedRequest) || messageType.equals(MessageType.SimpleAck)) {
            if (invokeId != null && serviceChoice != null) {
                hexString += intToHexString(getInvokeId(), 2) + serviceChoice.getServiceChoiceHex();
            }
        }
        return hexString.replace(new String(new char[] {160}), "");
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setProposedWindowSize(int proposedWindowSize) {
        this.proposedWindowSize = proposedWindowSize;
    }

    public int getProposedWindowSize() {
        return proposedWindowSize;
    }

    public boolean isNegativeAck() {
        return pduFlags.isBitSet(NEGATIVE_ACK_POSITION);
    }

    public void setNegativeAck(boolean negativeAck) {
        if (negativeAck) {
            pduFlags.setBit(NEGATIVE_ACK_POSITION);
        } else {
            pduFlags.unsetBit(NEGATIVE_ACK_POSITION);
        }
    }

    public void setSenderIsServer(boolean senderIsServer) {
        if (senderIsServer) {
            pduFlags.setBit(SENDER_IS_SERVER_POSITION);
        } else {
            pduFlags.unsetBit(SENDER_IS_SERVER_POSITION);
        }
    }

    public boolean getSenderIsServer() {
        return pduFlags.isBitSet(SENDER_IS_SERVER_POSITION);
    }

    public void setAbortReason(String abortReason) {
        this.abortReason = abortReason;
    }

    public String getAbortReason() {
        return abortReason;
    }

    public static final class ApduBuilder {
        private MessageType apduType;//Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response will
        private char pduFlags; //2 = unsegmented request, no more segments, se//Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response willgmented response accepted
        private char maxSegmentsAccepted; //7 = more than 64 accepted
        private char maxApduLengthAccepted; // 5 = up to 1476 octets
        private boolean segmented;//Used for all B//Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response willacnetRequest that require a response. See ConfirmedServiceRequest. Response will
        private boolean hasMoreSegments;
        private boolean segmentedReplyAllowed;
        private boolean senderIsServer;
        private boolean isNegativeAck;
        private Integer invokeId = null;
        private ServiceChoice serviceChoice = null;

        private ApduBuilder() {
        }

        //Used for all BacnetRequest that require a response. See ConfirmedServiceRequest. Response will
        public static ApduBuilder builder() {
            return new ApduBuilder();
        }

        public Apdu build() {
//            mapPduFlags();
            //Documented in Bacnet Spec clause 20.1.xx
            Apdu apdu = new Apdu(apduType, maxSegmentsAccepted, maxApduLengthAccepted);
            switch (apduType) {
                case SegmentACK:
                    apdu.pduFlags.unsetBit(3);
                    apdu.pduFlags.unsetBit(4);
                    apdu.setNegativeAck(isNegativeAck);
                    apdu.setSenderIsServer(senderIsServer);
                    break;
                case ConfirmedRequest:
                    apdu.setSegmented(segmented);
                    apdu.setHasMoreSegments(hasMoreSegments);
                    apdu.setSegmentedReplyAllowed(segmentedReplyAllowed);
                    if (invokeId != null) {
                        apdu.setInvokeId(invokeId);
                    }
                    if (serviceChoice != null) {
                        apdu.setServiceChoice(serviceChoice);
                    }
                    break;
                case ComplexAck:
                    apdu.pduFlags.unsetBit(1);
                    apdu.pduFlags.unsetBit(2);
                    apdu.setSegmented(segmented);
                    apdu.setHasMoreSegments(hasMoreSegments);
                    break;
                case Abort:
                    apdu.pduFlags.unsetBit(1);
                    apdu.pduFlags.unsetBit(2);
                    apdu.pduFlags.unsetBit(3);
                    apdu.setSenderIsServer(senderIsServer);
                    break;
                case Error:
                case SimpleAck:
                    if (invokeId != null) {
                        apdu.setInvokeId(invokeId);
                    }
                    if (serviceChoice != null) {
                        apdu.setServiceChoice(serviceChoice);
                    }
                    apdu.pduFlags.unsetBit(1);
                    apdu.pduFlags.unsetBit(2);
                    apdu.pduFlags.unsetBit(3);
                    apdu.pduFlags.unsetBit(4);
                    break;
                case UnconfirmedRequest:
                default:
                    apdu.pduFlags.unsetBit(1);
                    apdu.pduFlags.unsetBit(2);
                    apdu.pduFlags.unsetBit(3);
                    apdu.pduFlags.unsetBit(4);
            }

            return apdu;
        }

        public ApduBuilder withApduType(MessageType apduType) {
            this.apduType = apduType;
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

        public ApduBuilder withSenderIsServer(boolean senderIsServer) {
            this.senderIsServer = senderIsServer;
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

        public ApduBuilder withMaxApduLength206() {
            this.maxApduLengthAccepted = '2';
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

        public ApduBuilder withIsNegativeAck(boolean isNegativeAck) {
            this.isNegativeAck = isNegativeAck;
            return this;
        }

        public ApduBuilder withInvokeId(int invokeId) {
            this.invokeId = invokeId;
            return this;
        }
        public ApduBuilder withServiceChoice(ServiceChoice serviceChoice) {
            this.serviceChoice = serviceChoice;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Apdu{" +
                "messageType=" + messageType +
                ", applicationControlBits=" + applicationControlBits +
                ", maxSegmentsAccepted=" + maxSegmentsAccepted +
                ", maxApduLengthAccepted=" + maxApduLengthAccepted +
                ", invokeId=" + invokeId +
                ", serviceChoice=" + serviceChoice +
                ", isSegmented=" + isSegmented +
                ", isSegmentedReplyAllowed=" + isSegmentedReplyAllowed +
                ", hasMoreSegments=" + hasMoreSegments +
                ", sequenceNumber=" + sequenceNumber +
                ", proposedWindowSize=" + proposedWindowSize +
                ", senderIsServer=" + senderIsServer +
                ", abortReason='" + abortReason + '\'' +
                ", isNegativeAck=" + isNegativeAck +
                ", pduFlags=" + pduFlags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apdu apdu = (Apdu) o;
        return applicationControlBits == apdu.applicationControlBits &&

                getMaxSegmentsAccepted() == apdu.getMaxSegmentsAccepted() &&
                getMaxApduLengthAccepted() == apdu.getMaxApduLengthAccepted() &&
                getInvokeId() == apdu.getInvokeId() &&
                isSegmented() == apdu.isSegmented() &&
                isSegmentedReplyAllowed() == apdu.isSegmentedReplyAllowed() &&
                isHasMoreSegments() == apdu.isHasMoreSegments() &&
                getSequenceNumber() == apdu.getSequenceNumber() &&

                getProposedWindowSize() == apdu.getProposedWindowSize() &&
                getSenderIsServer() == apdu.getSenderIsServer() &&
                isNegativeAck() == apdu.isNegativeAck() &&
                getMessageType() == apdu.getMessageType() &&
                Objects.equals(getServiceChoice(), apdu.getServiceChoice()) &&
                Objects.equals(getAbortReason(), apdu.getAbortReason()) &&
                Objects.equals(pduFlags, apdu.pduFlags);

    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessageType(), applicationControlBits, getMaxSegmentsAccepted(), getMaxApduLengthAccepted(), getInvokeId(), getServiceChoice(), isSegmented(), isSegmentedReplyAllowed(), isHasMoreSegments(), getSequenceNumber(), getProposedWindowSize(), getSenderIsServer(), getAbortReason(), isNegativeAck(), pduFlags);
    }
}
