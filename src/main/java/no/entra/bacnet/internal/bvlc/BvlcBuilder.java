package no.entra.bacnet.internal.bvlc;

import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.octet.Octet;

import static no.entra.bacnet.BacnetConstants.MAX_BVLC_OCTETS_LENGTH;
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
        if (numberOfOctets > MAX_BVLC_OCTETS_LENGTH) {
            throw new IllegalArgumentException("Bacnet only support " + MAX_BVLC_OCTETS_LENGTH +" octets");
        }
        this.fullMessageOctetLength = numberOfOctets;
        return this;
    }

    public Bvlc build() {
        Bvlc bvlc = new Bvlc(function, fullMessageOctetLength);
        return bvlc;
    }


}
