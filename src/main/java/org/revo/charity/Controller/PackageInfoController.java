package org.revo.charity.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.charity.Domain.PackageInfo;
import org.revo.charity.Service.PackageInfoService;
import org.revo.charity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/package-info")
public class PackageInfoController {
    private final PackageInfoService packageInfoService;
    @Autowired
    private UserService userService;

    public PackageInfoController(PackageInfoService packageInfoService) {
        this.packageInfoService = packageInfoService;
    }

    @PostMapping
    @JsonView(View.PageFullDetailedPackageInfo.class)
    public PackageInfo save(@RequestBody PackageInfo packageInfo) {
        if (packageInfo.getDonation() != null) {
            if (packageInfo.getDonation().getDonor() != null && packageInfo.getDonation().getDonor().getId() != null) {
                packageInfo.getDonation().setDonor(userService.findOne(packageInfo.getDonation().getDonor().getId()).orElse(null));
            }
            if (packageInfo.getDonation().getBeneficiary() != null && packageInfo.getDonation().getBeneficiary().getId() != null) {
                packageInfo.getDonation().setDonor(userService.findOne(packageInfo.getDonation().getBeneficiary().getId()).orElse(null));
            }
        }
        return packageInfoService.save(packageInfo);
    }
}
