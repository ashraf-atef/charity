package org.revo.charity.Repository;

import org.revo.charity.Domain.Donation;
import org.revo.charity.Domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends CrudRepository<Donation, Long> {
    @Query("from Donation d  where d.donor = :donor and d.beneficiary is not NULL order by d.createdDate desc")
    List<Donation> findByDonor(@Param("donor") User user);

    @Query("from Donation d where d.packageInfo.id=:packageId")
    List<Donation> findByPackageId(@Param("packageId") Long packageId);

    @Query("select distinct d.beneficiary from Donation d where d.donor=:donor")
    List<User> findDistinctBeneficiaryByDonor(@Param("donor") User donor);

    @Query("select count(d.id) from Donation d where d.donor=:donor")
    int countDonations(@Param("donor") User donor);

    @Query("select count (distinct d.beneficiary.id) from Donation d where d.donor=:donor")
   int countBeneficiaries(@Param("donor") User donor);
}
