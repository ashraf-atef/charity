package org.revo.charity.Domain;

public class DonationRequest {
    private Long packageId;
    private Long beneficiaryId;


    public DonationRequest(Long packageId, Long beneficiaryId) {
        this.packageId = packageId;
        this.beneficiaryId = beneficiaryId;
    }


    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }
}
