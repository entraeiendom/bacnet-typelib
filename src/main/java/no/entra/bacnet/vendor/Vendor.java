package no.entra.bacnet.vendor;

import java.util.Objects;

public class Vendor {

    private String vendorId;
    private String name;

    public Vendor() {
    }

    public Vendor(String vendorId, String name) {
        this.vendorId = vendorId;
        this.name = name;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendor vendor = (Vendor) o;
        return Objects.equals(getVendorId(), vendor.getVendorId()) && Objects.equals(getName(), vendor.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVendorId(), getName());
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
