package no.entra.bacnet.internal.properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.entra.bacnet.internal.objects.ObjectIdMapper;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.objects.ObjectId;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static no.entra.bacnet.internal.properties.PropertyIdentifier.*;
import static org.junit.jupiter.api.Assertions.*;

class ReadAccessResultTest {

    private ReadAccessResult accessResult;
    private String objectIdString = "002dc6ef";
    private ObjectId objectId;
    private Gson gson;

    @BeforeEach
    void setUp() {
        ParserResult<ObjectId> result = ObjectIdMapper.parse(objectIdString);
        objectId = result.getParsedObject();
        accessResult = new ReadAccessResult(objectId);
        accessResult.setResultByKey(PresentValue, Double.valueOf(22.567));
        accessResult.setResultByKey(Description, "some short one");
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    @Test
    void validOutput() {
        assertEquals(objectId, accessResult.getObjectId());
        assertEquals(Double.valueOf(22.567), accessResult.getResultByKey("PresentValue"));
        assertEquals("some short one", accessResult.getResultByKey(Description));
    }

    @Test
    void jsonValidation() throws JSONException {

        String jsonBuilt = gson.toJson(accessResult);
        String expected = "{\n" +
                "  \"objectId\": {\n" +
                "    \"objectType\": \"AnalogInput\",\n" +
                "    \"instanceNumber\": 3000047\n" +
                "  },\n" +
                "  \"results\": {\n" +
                "    \"PresentValue\": 22.567,\n" +
                "    \"Description\": \"some short one\"\n" +
                "  }\n" +
                "}";
        JSONAssert.assertEquals(expected, jsonBuilt, true);

    }

    @Test
    void buildFromResultListTest() {
        String readPropertyMultiple = "0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        ReadAccessResult result = ReadAccessResult.buildFromResultList(readPropertyMultiple);
        assertNotNull(result);
        assertNotNull(result.getObjectId());
        assertFalse(result.getResults().isEmpty());
        assertEquals(Float.parseFloat("22.170061"), result.getResultByKey(PresentValue));
        assertEquals("DegreesCelcius", result.getResultByKey(Units));
        assertEquals("SOKP16-NAE4/FCB.434_101-1OU001.RT001", result.getResultByKey(ObjectName));
        assertEquals("Rom 1013, del1, plan U1, Blokk1 ", result.getResultByKey(Description));
    }

    @Test
    void buildUnitsTest() {
        String unitsHexString = "29754e913e4f";
        PropertyResult result = ReadAccessResult.parseProperty(unitsHexString);
        assertNotNull(result);
        assertEquals("DegreesCelcius", result.getProperty().getValue());

    }

    @Test
    void buildObjectNameTest() {
        String objectNameHexString = "294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f";
        PropertyResult result = ReadAccessResult.parseProperty(objectNameHexString);
        assertNotNull(result);
        assertEquals("SOKP16-NAE4/FCB.434_101-1OU001.RT001", result.getProperty().getValue());
    }
}