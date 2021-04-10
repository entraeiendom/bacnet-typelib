package no.entra.bacnet.services;

import no.entra.bacnet.internal.parseandmap.ParserResult;

import java.util.UUID;

public class BacnetParserException extends Throwable {
    private final String uuid;
    private ParserResult parserResult;

    public BacnetParserException(String msg , ParserResult parserResult) {
        super(msg);
        this.uuid = UUID.randomUUID().toString();
        this.parserResult = parserResult;
    }

    public String getUuid() {
        return uuid;
    }

    public ParserResult getParserResult() {
        return parserResult;
    }

    public void setParserResult(ParserResult parserResult) {
        this.parserResult = parserResult;
    }

    @Override
    public String toString() {
        return "BacnetParserException{" +
                "uuid='" + uuid + '\'' +
                ", parserResult=" + parserResult +
                "} " + super.toString();
    }
}
