package org.revo.charity.Service;

import org.revo.charity.Domain.PackageInfo;

import java.util.List;

public interface PackageInfoService {
    List<PackageInfo> findAll();

    PackageInfo save(PackageInfo packageInfo);
}
