package org.revo.charity.Controller;

import org.revo.charity.Domain.*;
import org.revo.charity.Service.DonationService;
import org.revo.charity.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/donations")
public class DonationController {
    private final DonationService donationService;
    private final UserService userService;

    public DonationController(
            DonationService donationService,
            UserService userService
    ) {
        this.donationService = donationService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Donation>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(donationService.findAll(user));
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Donation>> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(donationService.findById(id));
    }

    @GetMapping("my-beneficiaries")
    public ResponseEntity<List<User>> beneficiaries(@AuthenticationPrincipal User donor) {
        return ResponseEntity.ok(donationService.beneficiaries(donor));
    }

    @GetMapping("summary")
    public ResponseEntity<DonationSummaryResponse> summary(@AuthenticationPrincipal User donor) {
        return ResponseEntity.ok(
                new DonationSummaryResponse(
                        donationService.countDonations(donor),
                        donationService.countBeneficiaries(donor)
                )
        );
    }

    @PostMapping("link_meal")
    public ResponseEntity<Donation> linkMeal(
            @RequestBody MealLinkRequest mealLinkRequest
    ) {
        if (donationService.findByPackageId(mealLinkRequest.getPackageId()).isEmpty()) {
            Optional<User> optionalDonor = userService.findOne(mealLinkRequest.getDonatorId());

            if (!optionalDonor.isPresent())
                return ResponseEntity.status(406).build();

            Donation donation = new Donation();
            donation.setDonor(optionalDonor.get());
            Donation dbDonation = donationService.save(donation);

            PackageInfo packageInfo = new PackageInfo();
            packageInfo.setId(mealLinkRequest.getPackageId());
            packageInfo.setPackageStatus(PackageStatus.RECEIVED);
            dbDonation.setPackageInfo(packageInfo);

            return ResponseEntity.ok(donationService.save(dbDonation));
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("donate")
    public ResponseEntity<Donation> donate(
            @RequestBody DonationRequest donationRequest
    ) {
        List<Donation> donations = donationService.findByPackageId(donationRequest.getPackageId());
        if (donations.isEmpty()) {
            return ResponseEntity.status(400).build();
        } else {
            Optional<User> optionalBeneficiary = userService.findOne(donationRequest.getBeneficiaryId());
            if (!optionalBeneficiary.isPresent())
                return ResponseEntity.status(406).build();

            donations.get(0).setBeneficiary(optionalBeneficiary.get());
            return ResponseEntity.ok(donationService.save(donations.get(0)));
        }
    }
}
