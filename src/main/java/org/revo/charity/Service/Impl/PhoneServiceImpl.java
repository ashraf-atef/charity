package org.revo.charity.Service.Impl;

import org.revo.charity.Domain.Phone;
import org.revo.charity.Repository.PhoneRepository;
import org.revo.charity.Service.PhoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public List<Phone> findAll() {
        return StreamSupport.stream(phoneRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Phone> findOne(Long id) {
        return phoneRepository.findById(id);
    }

    @Override
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }
}
