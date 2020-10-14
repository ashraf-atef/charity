package org.revo.charity.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.charity.domain.PackageInfo;
import org.revo.charity.service.PackageInfoService;
import org.revo.charity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/package-info")
public class PackageInfoController {
    private final PackageInfoService packageInfoService;
    private final UserService userService;

    public PackageInfoController(PackageInfoService packageInfoService, UserService userService) {
        this.packageInfoService = packageInfoService;
        this.userService = userService;
    }

    @GetMapping
    @JsonView(View.PageFullDetailedPackageInfo.class)
    public ResponseEntity<List<PackageInfo>> findAll() {
        return ResponseEntity.ok(packageInfoService.findAll());
    }

    @GetMapping("{id}")
    @JsonView(View.PageFullDetailedPackageInfo.class)
    public ResponseEntity<Optional<PackageInfo>> findAll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(packageInfoService.findOne(id));
    }

    @PostMapping
    @JsonView(View.PageFullDetailedPackageInfo.class)
    public ResponseEntity<PackageInfo> save(@RequestBody PackageInfo packageInfo) {
        if (packageInfo.getDonation() != null) {
            if (packageInfo.getDonation().getDonor() != null && packageInfo.getDonation().getDonor().getId() != null) {
                packageInfo.getDonation().setDonor(userService.findOne(packageInfo.getDonation().getDonor().getId()).orElse(null));
            }
            if (packageInfo.getDonation().getBeneficiary() != null && packageInfo.getDonation().getBeneficiary().getId() != null) {
                packageInfo.getDonation().setDonor(userService.findOne(packageInfo.getDonation().getBeneficiary().getId()).orElse(null));
            }
            PackageInfo info = new PackageInfo();
            info.setId(packageInfo.getId());
            packageInfo.getDonation().setPackageInfo(info);
        }
        return ResponseEntity.ok(packageInfoService.save(packageInfo));
    }
}
