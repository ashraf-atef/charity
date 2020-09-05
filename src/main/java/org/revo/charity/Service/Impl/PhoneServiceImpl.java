package org.revo.charity.Service.Impl;

import org.revo.charity.Repository.PhoneRepository;
import org.revo.charity.Service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;
}
