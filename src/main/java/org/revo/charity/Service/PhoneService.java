package org.revo.charity.Service;

import org.revo.charity.Domain.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {
    List<Phone> findAll();

    Optional<Phone> findOne(Long id);

    Phone save(Phone phone);
}
