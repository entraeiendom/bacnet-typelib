package no.entra.bacnet.internal.npdu;


import no.entra.bacnet.octet.Octet;

//TODO implement NPDU control using BitString
public enum  NpduControl {
    NormalMessage("00"),
    SourceAvailable("08"),
    DestinationSpecifier("20");
    private String npduControlHex;

    public static NpduControl fromBvlcFuntionHex(String hexString) {
        for (NpduControl type : values()) {
            if (type.getNpduControlHex().equals(hexString)) {
                return type;
            }
        }
        return null;
    }


    public static NpduControl fromOctet(Octet bvlcFuntionOctet) throws NumberFormatException {
        if (bvlcFuntionOctet == null) {
            return null;
        }
        return fromBvlcFuntionHex(bvlcFuntionOctet.toString());
    }


    public String getNpduControlHex() {
        return npduControlHex;
    }

    private NpduControl(String hexString) {
        this.npduControlHex = hexString;
    }
}
