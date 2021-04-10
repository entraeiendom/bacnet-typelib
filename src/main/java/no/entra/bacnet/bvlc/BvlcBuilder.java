package no.entra.bacnet.bvlc;

import no.entra.bacnet.internal.octet.Octet;

import static no.entra.bacnet.bvlc.Bvlc.findExpectdNumberOfOctetsInBvll;


public class BvlcBuilder {

    private final BvlcFunction function;
    private int fullMessageOctetLength;

    public BvlcBuilder(BvlcFunction function) {
        this.function = function;
    }

    public BvlcBuilder withMessageLength(Octet[] messageLength) {
        if (messageLength != null && messageLength.length == 2) {
            this.fullMessageOctetLength = findExpectdNumberOfOctetsInBvll(messageLength);
        }
        return this;
    }
    public BvlcBuilder withTotalNumberOfOctets(int numberOfOctets) {
        if (numberOfOctets > 1024) {
            throw new IllegalArgumentException("Bacnet only support 1024 octets");
        }
        this.fullMessageOctetLength = numberOfOctets;
        return this;
    }

    public Bvlc build() {
        Bvlc bvlc = new Bvlc(function, fullMessageOctetLength);
        return bvlc;
    }


}
