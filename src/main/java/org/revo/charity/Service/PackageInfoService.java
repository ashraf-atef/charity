package org.revo.charity.Service;

import org.revo.charity.Domain.PackageInfo;

import java.util.List;
import java.util.Optional;

public interface PackageInfoService {
    List<PackageInfo> findAll();

    PackageInfo save(PackageInfo packageInfo);

    Optional<PackageInfo> findOne(Long id);
}
