package org.revo.charity.service;

import org.revo.charity.domain.Donation;
import org.revo.charity.domain.User;

import java.util.List;
import java.util.Optional;

public interface DonationService {
    List<Donation> findAll();

    List<Donation> findAll(User user);

    Optional<Donation> findById(Long id);

    List<User> beneficiaries(User donor);
}
