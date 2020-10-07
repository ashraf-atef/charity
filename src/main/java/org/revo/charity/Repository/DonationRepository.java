package org.revo.charity.Repository;

import org.revo.charity.Domain.Donation;
import org.revo.charity.Domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends CrudRepository<Donation, Long> {
    List<Donation> findByDonor(User user);

    @Query("select d.beneficiary from Donation d where d.donor=:donor")
    List<User> findDistinctBeneficiaryByDonor(@Param("donor") User donor);

}
