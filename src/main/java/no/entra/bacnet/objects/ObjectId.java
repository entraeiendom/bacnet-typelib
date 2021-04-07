package no.entra.bacnet.objects;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ObjectId {
    private static final Logger log = getLogger(ObjectId.class);

    private ObjectType objectType;
    private Integer instanceNumber;

    public ObjectId() {
    }

    public ObjectId(ObjectType objectType, Integer instanceNumber) {
        this.objectType = objectType;
        this.instanceNumber = instanceNumber;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(Integer instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public String toHexString() {
        return ObjectIdMapper.toHexString(this);
    }

    public static ObjectId fromHexString(String hexString) {
        return ObjectIdMapper.fromHexString(hexString);
    }

    @Override
    public String toString() {
        if (objectType == null) {
            return "type-missing " + " " + instanceNumber;
        }
        return objectType.name() + "_" + instanceNumber;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj != null && obj instanceof ObjectId) {
            if (((ObjectId) obj).getObjectType().equals(this.objectType) && ((ObjectId)obj).getInstanceNumber().equals(this.getInstanceNumber())) {
                isEqual = true;
            }
            return isEqual;
        }
        return super.equals(obj);
    }
}
