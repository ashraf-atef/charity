package org.revo.charity.service.impl;

import org.revo.charity.domain.Donation;
import org.revo.charity.domain.User;
import org.revo.charity.repository.DonationRepository;
import org.revo.charity.service.DonationService;
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
    public List<User> beneficiaries(User donor) {
        return donationRepository.findDistinctBeneficiaryByDonor(donor);
    }
}
