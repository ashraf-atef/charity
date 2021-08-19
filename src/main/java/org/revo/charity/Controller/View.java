package org.revo.charity.Controller;

public interface View {
    interface BasicPackageInfo {
    }

    interface DetailedPackageInfo {
    }

    interface BasicDonation {
    }

    interface DetailedDonation {
    }

    interface BasicUser {
    }

    interface PageFullDetailedPackageInfo extends BasicPackageInfo, DetailedPackageInfo, BasicDonation, DetailedDonation,BasicUser {
    }

    interface BasicUserLocation {

    }
}
