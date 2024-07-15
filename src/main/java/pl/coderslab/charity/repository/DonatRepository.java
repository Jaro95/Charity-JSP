package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.Donation;

public interface DonatRepository extends JpaRepository<Donation, Long> {
}
