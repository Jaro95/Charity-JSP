package pl.coderslab.charity.domain.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.domain.user.UserRepository;
import pl.coderslab.charity.infrastructure.security.Role;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public List<Donation> getAllDonations() {
        List<Donation> donations = donationRepository.findAll();
        for (Donation donation : donations) {
            donation.getUser().getRole().forEach(role -> role.getUser().clear());
        }
        return donations;
    }

    public Optional<Donation> getDonation(Long id) {
        Optional<Donation> donation = donationRepository.findById(id);
        donation.ifPresent(d -> d.getUser().getRole().forEach(role -> role.getUser().clear()));
        return donation;
    }

    public String addDonation(Optional<DonationRequest> donationRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors().toString();
        }
        donationRequest.ifPresent(d -> {
            donationRepository.save(Donation.builder()
                    .quantity(d.quantity())
                    .category(d.category())
                    .institution(d.institution())
                    .street(d.street())
                    .city(d.city())
                    .zipCode(d.zipCode())
                    .phoneNumber(d.phoneNumber())
                    .pickUpDate(d.pickUpDate())
                    .pickUpTime(d.pickUpTime())
                    .pickUpComment(d.pickUpComment())
                    .receive(false)
                    .createdDate(LocalDate.now())
                    .createdTime(LocalTime.now().withSecond(0).withNano(0))
                    .user(userRepository.findById(d.userId()).orElse(null))
                    .build());
            log.info("Added new donation:\n{}", d.toString());
        });
        return donationRequest.isPresent() ? "Added new donation:\n" + donationRequest.get()
                : "Donation not added";
    }

    public String updateDonation(Long id, Optional<DonationRequest> donationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors().toString();
        }
        Optional<Donation> donation = donationRepository.findById(id);
        donation.ifPresent(d -> {
            donationRequest.ifPresent(dr -> {
                d.setQuantity(dr.quantity());
                d.setCategory(dr.category());
                d.setInstitution(dr.institution());
                d.setStreet(dr.street());
                d.setCity(dr.city());
                d.setZipCode(dr.zipCode());
                d.setPhoneNumber(dr.phoneNumber());
                d.setPickUpDate(dr.pickUpDate());
                d.setPickUpTime(dr.pickUpTime());
                d.setReceive(dr.receive());
                d.setCreatedDate(dr.createdDate());
                d.setCreatedTime(dr.createdTime());
                d.setPickUpComment(dr.pickUpComment());
                d.setUser(userRepository.findById(dr.userId()).orElse(null));
            });
            donationRepository.save(d);
            log.info("Updated donation: {}", d.toString());
        });
        return donation.isPresent() && donationRequest.isPresent() ? "Updated donation"
                : "The donation to update was not found";
    }

    public String deleteDonation(Long id) {
        Optional<Donation> donation = donationRepository.findById(id);
        donation.ifPresent(d -> {
            donationRepository.delete(d);
            log.info("Deleted donation:\n{}",d.toString());
        });
        return donation.isPresent() ? "Donation deleted" : "The donation to delete was not found";
    }
}
