package org.revo.charity.controller;

public interface View {

    interface Page {
    }

    interface BasicAuthority {
    }

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

    interface PageFullDetailedPackageInfo extends Page, BasicPackageInfo, DetailedPackageInfo, BasicDonation, DetailedDonation, BasicUser {
    }

}
