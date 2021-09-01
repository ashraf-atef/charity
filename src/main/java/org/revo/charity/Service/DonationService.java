package org.revo.charity.Service;

import org.revo.charity.Domain.Donation;
import org.revo.charity.Domain.MealLinkRequest;
import org.revo.charity.Domain.User;

import java.util.List;
import java.util.Optional;

public interface DonationService {
    List<Donation> findAll();

    List<Donation> findAll(User user);

    Optional<Donation> findById(Long id);

    List<Donation> findByPackageId(Long packageId);

    List<User> beneficiaries(User donor);

    int countDonations(User user);

    int countBeneficiaries(User user);

    Donation save(Donation donation);
}
