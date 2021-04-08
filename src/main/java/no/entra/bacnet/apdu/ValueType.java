package no.entra.bacnet.apdu;

import no.entra.bacnet.octet.Octet;

import static java.lang.Integer.parseInt;

public enum ValueType {
    Null(0),
    Boolean(1),
    Long(2),
    Integer(3),
    Float(4),
    Double(5),
    OctetString(6),
    CharString(7),
    BitString(8),
    Enumerated(9),
    Date(10),
    Time(11),
    ObjectIdentifier(12);
        /*
    0 = null
    1 = Boolean
2 = Unsigned Integer (long)
3 = Signed Integer (2's complement notation)
4 = Real (ANSI/IEEE-754 floating point)
5 = Double (ANSI/IEEE-754 double precision floating point)
6 = Octet String
7 = Character String
8 = Bit String
9 = Enumerated
10 = Date
11 = Time
12 = BACnetObjectIdentifier
13, 14, 15 = Reserved for ASHRAE
     */


    private int ValueTypeInt;

    public static ValueType fromValueTypeInt(int ValueTypeInt) {
        for (ValueType type : values()) {
            if (type.getValueTypeInt() == ValueTypeInt) {
                return type;
            }
        }
        return null;
    }

    public static ValueType fromChar(char nibble) throws NumberFormatException {
        int valueTypeInt = parseInt(String.valueOf(nibble), 16);
        ValueType valueType = fromValueTypeInt(valueTypeInt);
        return valueType;
    }
    public static ValueType fromOctet(Octet ValueTypeOctet) throws NumberFormatException {
        if (ValueTypeOctet == null) {
            return null;
        }
        Integer valueTypeInt = parseInt(ValueTypeOctet.toString(), 16);
        ValueType valueType = fromValueTypeInt(valueTypeInt.intValue());
        return valueType;
    }


    public int getValueTypeInt() {
        return ValueTypeInt;
    }

    // enum constructor - cannot be public or protected
    private ValueType(int ValueTypeInt) {
        this.ValueTypeInt = ValueTypeInt;
    }

}
