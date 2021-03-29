package no.entra.bacnet.objects;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ObjectId {
    private static final Logger log = getLogger(ObjectId.class);

    private no.entra.bacnet.objects.ObjectType objectType;
    private String instanceNumber;

    public ObjectId() {
    }

    public ObjectId(no.entra.bacnet.objects.ObjectType objectType, String instanceNumber) {
        this.objectType = objectType;
        this.instanceNumber = instanceNumber;
    }

    public no.entra.bacnet.objects.ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(no.entra.bacnet.objects.ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(String instanceNumber) {
        this.instanceNumber = instanceNumber;
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
