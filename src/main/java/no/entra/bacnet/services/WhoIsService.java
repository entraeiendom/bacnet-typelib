package no.entra.bacnet.services;

public class WhoIsService implements Service {

    private Integer lowRangeLimit = null;
    private Integer highRangeLimit = null;

    public WhoIsService() {
    }

    public Integer getLowRangeLimit() {
        return lowRangeLimit;
    }

    public void setLowRangeLimit(Integer lowRangeLimit) {
        this.lowRangeLimit = lowRangeLimit;
    }

    public Integer getHighRangeLimit() {
        return highRangeLimit;
    }

    public void setHighRangeLimit(Integer highRangeLimit) {
        this.highRangeLimit = highRangeLimit;
    }

    @Override
    public String toString() {
        return "WhoIsService{" +
                "lowRangeLimit=" + lowRangeLimit +
                ", highRangeLimit=" + highRangeLimit +
                '}';
    }
}
