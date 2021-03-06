package no.entra.bacnet.internal.objects;

import no.entra.bacnet.octet.Octet;

@Deprecated //Use MeasurementUnit
public enum Unit {
    DegreesCelcius("3e");
    private String unitHex;

    public static Unit fromUnitHex(String hexString) {
        for (Unit type : values()) {
            if (type.getUnitHex().equals(hexString)) {
                return type;
            }
        }
        return null;
    }


    public static Unit fromOctet(Octet UnitOctet) throws NumberFormatException {
        if (UnitOctet == null) {
            return null;
        }
        return fromUnitHex(UnitOctet.toString());
    }


    public String getUnitHex() {
        return unitHex;
    }

    private Unit(String hexString) {
        this.unitHex = hexString;
    }
}
