package no.entra.bacnet.internal.npdu;


import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.utils.BitString;

//TODO #1 Set Control based on bits
public class NpduBuilder {

    private final Octet version;
    private Octet control;
    private Octet[] destinationNetworkAddress;
    private Octet destinationMacLayerAddress;
    private Octet hopCount;
    private boolean hasDestinationSpecifier;

    public NpduBuilder(Octet control) {
        this.version = Octet.fromHexString("01");
        this.control = control;
    }
    public NpduBuilder() {
        this.version = Octet.fromHexString("01");
        this.control = Octet.fromHexString("00");
    }

    public NpduBuilder withNormalMessage() {
        this.control = Octet.fromHexString("00");
        return this;
    }

    public NpduBuilder withExpectingReply() {
        this.control = Octet.fromHexString("04");
        return this;
    }

    public NpduBuilder setDestinationSpecifierControlBit(boolean destinationIsSpecified) {
        this.hasDestinationSpecifier = destinationIsSpecified;
        return this;
    }

    public NpduBuilder withDestinationNetworkAddress(Octet[] destinationNetworkAddress) {
        this.destinationNetworkAddress = destinationNetworkAddress;
        return this;
    }
    public NpduBuilder withDestinationMacLayerAddress(Octet destinationMacLayerAddress) {
        this.destinationMacLayerAddress = destinationMacLayerAddress;
        return this;
    }
    public NpduBuilder withHopCount(Octet hopCount) {
        this.hopCount = hopCount;
        return this;
    }


    public Npdu build() {
        BitString destinationControl = new BitString(4);
        BitString sourceControl = new BitString(4);
        Npdu npdu = new Npdu();
        if (hasDestinationSpecifier) {
            destinationControl.setBit(2);
            npdu.setDestinationNetworkAddress(destinationNetworkAddress);
            npdu.setDestinationMacLayerAddress(destinationMacLayerAddress);
            npdu.setHopCount(hopCount);
        }
        if (control == null) {
            control = new Octet(destinationControl.toHexString() + sourceControl.toHexString());
        }
        npdu.setControl(control);
        if (control.getSecondNibble() == '4') {
            npdu.expectingResponse(true);
        }

        return npdu;
    }


}
