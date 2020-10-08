package org.revo.charity.Service.Impl;

import org.revo.charity.Domain.PackageInfo;
import org.revo.charity.Repository.PackageInfoRepository;
import org.revo.charity.Service.PackageInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PackageInfoServiceImpl implements PackageInfoService {
    private final PackageInfoRepository packageInfoRepository;

    public PackageInfoServiceImpl(PackageInfoRepository packageInfoRepository) {
        this.packageInfoRepository = packageInfoRepository;
    }

    @Override
    public List<PackageInfo> findAll() {
        return StreamSupport.stream(packageInfoRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PackageInfo save(PackageInfo packageInfo) {
        return packageInfoRepository.save(packageInfo);
    }

    @Override
    public Optional<PackageInfo> findOne(Long id) {
        return packageInfoRepository.findById(id);
    }
}
