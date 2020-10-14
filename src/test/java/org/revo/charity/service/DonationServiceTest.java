package org.revo.charity.service;

import org.junit.jupiter.api.Test;
import org.revo.charity.domain.Donation;
import org.revo.charity.service.impl.DonationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import(DonationServiceImpl.class)
class DonationServiceTest {
    @Autowired
    private DonationService donationService;

    @Test
    void init() {
        List<Donation> all = donationService.findAll(null);
        System.out.println(all);
    }
}
