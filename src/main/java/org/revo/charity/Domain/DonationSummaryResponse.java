package org.revo.charity.Domain;

public class DonationSummaryResponse {
    private int donationsNumber;
    private int beneficiariesNumber;

    public DonationSummaryResponse(int donationsNumber, int familyNumber) {
        this.donationsNumber = donationsNumber;
        this.beneficiariesNumber = familyNumber;
    }

    public int getDonationsNumber() {
        return donationsNumber;
    }

    public void setDonationsNumber(int donationsNumber) {
        this.donationsNumber = donationsNumber;
    }

    public int getBeneficiariesNumber() {
        return beneficiariesNumber;
    }

    public void setBeneficiariesNumber(int beneficiariesNumber) {
        this.beneficiariesNumber = beneficiariesNumber;
    }
}