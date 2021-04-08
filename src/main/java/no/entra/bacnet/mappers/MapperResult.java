package no.entra.bacnet.mappers;

public class MapperResult<T> {

    private final T parsedObject;
    private final int numberOfOctetsRead;

    public MapperResult(T parsedObject, int numberOfOctetsRead) {
        this.parsedObject = parsedObject;
        this.numberOfOctetsRead = numberOfOctetsRead;
    }

    public T getParsedObject() {
        return parsedObject;
    }

    public int getNumberOfOctetsRead() {
        return numberOfOctetsRead;
    }
}
