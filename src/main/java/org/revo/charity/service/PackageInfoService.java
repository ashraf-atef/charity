package org.revo.charity.service;

import org.revo.charity.domain.PackageInfo;

import java.util.List;
import java.util.Optional;

public interface PackageInfoService {
    List<PackageInfo> findAll();

    PackageInfo save(PackageInfo packageInfo);

    Optional<PackageInfo> findOne(Long id);
}
