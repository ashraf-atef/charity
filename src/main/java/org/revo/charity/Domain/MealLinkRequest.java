package org.revo.charity.Domain;

public class MealLinkRequest {
    private long donatorId;
    private long packageId;

    public MealLinkRequest(long donatorId, long packageId) {
        this.donatorId = donatorId;
        this.packageId = packageId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public long getDonatorId() {
        return donatorId;
    }

    public void setDonatorId(long donatorId) {
        this.donatorId = donatorId;
    }
}