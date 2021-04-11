package no.entra.bacnet.services;

public class AbortService implements Service {
    private int invokeId = -1;
    private String abortReason = null;

    public AbortService() {
    }

    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    public String getAbortReason() {
        return abortReason;
    }

    public void setAbortReason(String abortReason) {
        this.abortReason = abortReason;
    }
}
