package no.entra.bacnet.internal.parseandmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserResultTest {

    @Test
    void getNumberOfOctetsRead() {
        ParserResult parserResult = new ParserResult();
        parserResult.setNumberOfOctetsRead(14);
        assertEquals(14, parserResult.getNumberOfOctetsRead());
        String initialHexString = "0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f";
        String unparsedHexString = "291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f";
        parserResult = new ParserResult(null, initialHexString, unparsedHexString, true);
        assertEquals(32, parserResult.getNumberOfOctetsRead());
    }
}