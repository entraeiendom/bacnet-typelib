package no.entra.bacnet.npdu;


import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.utils.HexUtils;

import java.util.Arrays;
import java.util.Objects;

import static no.entra.bacnet.utils.HexUtils.octetsToString;

public class Npdu {
    public static final Octet version = Octet.fromHexString("01");
    private Octet control;
    private Octet[] sourceNetworkAddress;
    private Octet[] sourceMacLayerAddress;
    private Octet[] destinationNetworkAddress;
    private Octet destinationMacLayerAddress;
    private Octet hopCount;
    private boolean expectingResponse = false;

    public Octet getVersion() {
        return version;
    }

    public Octet getControl() {
        return control;
    }

    public void setControl(Octet controlOctet) {
        this.control = controlOctet;
    }

    public Octet[] getSourceNetworkAddress() {
        return sourceNetworkAddress;
    }

    public void setSourceNetworkAddress(Octet[] sourceNetworkAddress) {
        this.sourceNetworkAddress = sourceNetworkAddress;
    }

    public Octet[] getSourceMacLayerAddress() {
        return sourceMacLayerAddress;
    }

    public void setSourceMacLayerAddress(Octet[] sourceMacLayerAddress) {
        this.sourceMacLayerAddress = sourceMacLayerAddress;
    }

    public Octet[] getDestinationNetworkAddress() {
        return destinationNetworkAddress;
    }

    public void setDestinationNetworkAddress(Octet[] destinationNetworkAddress) {
        this.destinationNetworkAddress = destinationNetworkAddress;
    }

    public Octet getDestinationMacLayerAddress() {
        return destinationMacLayerAddress;
    }

    public void setDestinationMacLayerAddress(Octet destinationMacLayerAddress) {
        this.destinationMacLayerAddress = destinationMacLayerAddress;
    }

    public Octet getHopCount() {
        return hopCount;
    }

    public void setHopCount(Octet hopCount) {
        this.hopCount = hopCount;
    }

    public boolean isSourceAvailable() {
        boolean sourceIsAvailable = false;
        char lowerControl = control.getSecondNibble();
        sourceIsAvailable = HexUtils.isBitSet(lowerControl, 3);
        return sourceIsAvailable;
    }

    public boolean isDestinationAvailable() {
        boolean destinationIsAvailable = false;
        char higerControl = control.getFirstNibble();
        destinationIsAvailable = HexUtils.isBitSet(higerControl, 1);
        return destinationIsAvailable;
    }

    public boolean setExpectingResponse() {
        return expectingResponse;
    }
    public void expectingResponse(boolean expectingResponse) {
        this.expectingResponse = expectingResponse;
    }

    public boolean isExpectingResponse() {
        return expectingResponse;
    }

    public String toHexString() {
        String hexString = version.toString() + control.toString();
        if (isDestinationAvailable()) {
            hexString += octetsToString(destinationNetworkAddress) + destinationMacLayerAddress + hopCount;
        }
        return hexString;
    }

    @Override
    public String toString() {
        return "Npdu{" +
                "control=" + control +
                ", sourceNetworkAddress=" + octetsToString(sourceNetworkAddress) +
                ", sourceMacLayerAddress=" + octetsToString(sourceMacLayerAddress) +
                ", destinationNetworkAddress=" + octetsToString(destinationNetworkAddress) +
                ", destinationMacLayerAddress=" + destinationMacLayerAddress +
                ", hopCount=" + hopCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Npdu npdu = (Npdu) o;
        return isExpectingResponse() == npdu.isExpectingResponse() &&
                Objects.equals(getControl(), npdu.getControl()) &&
                Arrays.equals(getSourceNetworkAddress(), npdu.getSourceNetworkAddress()) &&
                Arrays.equals(getSourceMacLayerAddress(), npdu.getSourceMacLayerAddress()) &&
                Arrays.equals(getDestinationNetworkAddress(), npdu.getDestinationNetworkAddress()) &&
                Objects.equals(getDestinationMacLayerAddress(), npdu.getDestinationMacLayerAddress()) &&
                Objects.equals(getHopCount(), npdu.getHopCount());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getControl(), getDestinationMacLayerAddress(), getHopCount(), isExpectingResponse());
        result = 31 * result + Arrays.hashCode(getSourceNetworkAddress());
        result = 31 * result + Arrays.hashCode(getSourceMacLayerAddress());
        result = 31 * result + Arrays.hashCode(getDestinationNetworkAddress());
        return result;
    }


}
