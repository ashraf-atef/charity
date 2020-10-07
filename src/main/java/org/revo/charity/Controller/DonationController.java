package org.revo.charity.Controller;

import org.revo.charity.Domain.Donation;
import org.revo.charity.Domain.User;
import org.revo.charity.Service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/donations")
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
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
}
