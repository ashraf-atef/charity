package org.revo.charity.Service;

import org.junit.jupiter.api.Test;
import org.revo.charity.Domain.PackageInfo;
import org.revo.charity.Service.Impl.PackageInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


@DataJpaTest
@Import(PackageInfoServiceImpl.class)
class PackageInfoServiceTest {
    @Autowired
    private PackageInfoService packageInfoService;

    @Test
    void init() {
        List<PackageInfo> all = packageInfoService.findAll();
        System.out.println(all);
    }
}
