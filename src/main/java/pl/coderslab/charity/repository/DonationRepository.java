package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.Donation;

import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select sum(d.quantity) from Donation d")
    Optional<Long> allQuantityBag();

    @Query("select count(d) from Donation d")
    Optional<Long> allDonations();
}
