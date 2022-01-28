package no.entra.bacnet.internal.npdu;

import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.octet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NpduTest {

    @Test
    void isSourceAvailable() {
        Npdu npdu = new Npdu();
        npdu.setControl(Octet.fromHexString(NpduControl.SourceAvailable.getNpduControlHex()));
        assertTrue(npdu.isSourceAvailable());
        npdu.setControl(Octet.fromHexString("00"));
        assertFalse(npdu.isSourceAvailable());
    }

    @Test
    void isDestinationAvailable() {
        Npdu npdu = new Npdu();
        Octet[] destinationNetworkAddress = {new Octet("ff"), new Octet("ff")};
        Octet destinationMacLayerAddress = new Octet("00");
        Octet hopCount = new Octet("ff");
        npdu.setControl(Octet.fromHexString(NpduControl.DestinationSpecifier.getNpduControlHex()));
        assertTrue(npdu.isDestinationAvailable());
        npdu.setControl(Octet.fromHexString("00"));
        assertFalse(npdu.isDestinationAvailable());
    }

    @Test
    void equalsTest() {
        Npdu npdu = new Npdu();
        npdu.setControl(Octet.fromHexString(NpduControl.DestinationSpecifier.getNpduControlHex()));
        Npdu npdu2 = new Npdu();
        npdu2.setControl(Octet.fromHexString(NpduControl.DestinationSpecifier.getNpduControlHex()));
        assertEquals(npdu, npdu2);
    }
}