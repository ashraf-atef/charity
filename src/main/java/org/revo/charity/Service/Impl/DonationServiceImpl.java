package org.revo.charity.Service.Impl;

import org.revo.charity.Domain.Donation;
import org.revo.charity.Domain.User;
import org.revo.charity.Repository.DonationRepository;
import org.revo.charity.Service.DonationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;

    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
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
    public List<User> beneficiaries(User donor) {
        return donationRepository.findDistinctBeneficiaryByDonor(donor);
    }
}
