package org.revo.charity.Service.Impl;

import org.revo.charity.Domain.Donation;
import org.revo.charity.Domain.MealLinkRequest;
import org.revo.charity.Domain.User;
import org.revo.charity.Repository.DonationRepository;
import org.revo.charity.Service.DonationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;

    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public List<Donation> findAll() {
        return StreamSupport.stream(donationRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public List<Donation> findAll(User user) {
        return donationRepository.findByDonor(user);
    }

    @Override
    public Optional<Donation> findById(Long id) {
        return donationRepository.findById(id);
    }

    @Override
    public List<Donation> findByPackageId(Long packageId) {
       return donationRepository.findByPackageId(packageId);
    }

    @Override
    public List<User> beneficiaries(User donor) {
        return donationRepository.findDistinctBeneficiaryByDonor(donor);
    }

    @Override
    public int countDonations(User user) {
        return donationRepository.countDonations(user);
    }

    @Override
    public int countBeneficiaries(User user) {
        return donationRepository.countBeneficiaries(user);
    }

    @Override
    public Donation save(Donation donation) {
        return donationRepository.save(donation);
    }
}
