package no.entra.bacnet.services;

import java.util.UUID;

public class BacnetParserException extends Throwable {
    private final String uuid;
    private BacnetParserResult bacnetParserResult;

    public BacnetParserException(String msg , BacnetParserResult bacnetParserResult) {
        super(msg);
        this.uuid = UUID.randomUUID().toString();
        this.bacnetParserResult = bacnetParserResult;
    }

    public String getUuid() {
        return uuid;
    }

    public BacnetParserResult getBacnetParserResult() {
        return bacnetParserResult;
    }

    public void setBacnetParserResult(BacnetParserResult bacnetParserResult) {
        this.bacnetParserResult = bacnetParserResult;
    }

    @Override
    public String toString() {
        return "BacnetParserException{" +
                "uuid='" + uuid + '\'' +
                ", bacnetParserResult=" + bacnetParserResult +
                "} " + super.toString();
    }
}
