package pl.coderslab.charity.domain.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.category.Category;
import pl.coderslab.charity.domain.category.CategoryRepository;
import pl.coderslab.charity.domain.institution.InstitutionRepository;
import pl.coderslab.charity.domain.user.User;
import pl.coderslab.charity.domain.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;

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

    public DonationAddRequest addDonation(DonationAddRequest donationAddRequest) {
        List<Category> categories = new ArrayList<>();
        donationAddRequest.categoryId().forEach(c -> categories.add(categoryRepository.findById(c).orElseThrow(IllegalArgumentException::new)));
        donationRepository.save(Donation.builder()
                .quantity(donationAddRequest.quantity())
                .category(categories)
                .institution(institutionRepository.findById(donationAddRequest.institutionId()).orElseThrow(IllegalArgumentException::new))
                .street(donationAddRequest.street())
                .city(donationAddRequest.city())
                .zipCode(donationAddRequest.zipCode())
                .phoneNumber(donationAddRequest.phoneNumber())
                .pickUpDate(donationAddRequest.pickUpDate())
                .pickUpTime(donationAddRequest.pickUpTime())
                .pickUpComment(donationAddRequest.pickUpComment())
                .receive(false)
                .createdDate(LocalDate.now())
                .createdTime(LocalTime.now().withSecond(0).withNano(0))
                .user(userRepository.findById(donationAddRequest.userId()).orElseThrow(IllegalArgumentException::new))
                .build());
        log.info("Added new donation:\n{}", donationAddRequest.toString());
        return donationAddRequest;
    }

    public Optional<Donation> updateDonation(Long id, DonationUpdateRequest donationUpdateRequest) {
        Optional<Donation> donation = donationRepository.findById(id);
        List<Category> categories = new ArrayList<>();
        donation.ifPresent(d -> {
            if (!donationUpdateRequest.categoryId().isEmpty()) {
                donationUpdateRequest.categoryId()
                        .forEach(c -> categories.add(categoryRepository.findById(c)
                                .orElseThrow(IllegalArgumentException::new)));
            }

            if (donationUpdateRequest.quantity().describeConstable().isPresent()) {
                d.setQuantity(d.getQuantity() == donationUpdateRequest.quantity() ? d.getQuantity()
                        : donationUpdateRequest.quantity());
            }

            if (!categories.isEmpty()) {
                d.setCategory(categories.equals(d.getCategory()) ? d.getCategory() : categories);
            }

            if (donationUpdateRequest.institutionId().describeConstable().isPresent()) {
                d.setInstitution(d.getInstitution().getId() == donationUpdateRequest.institutionId() ? d.getInstitution()
                        : institutionRepository.findById(donationUpdateRequest.institutionId()).orElseThrow(IllegalArgumentException::new));
            }

            if (!donationUpdateRequest.donationAddress().getStreet().isEmpty()) {
                d.setStreet(d.getStreet().equals(donationUpdateRequest.donationAddress().getStreet()) ? d.getStreet()
                        : donationUpdateRequest.donationAddress().getStreet());
            }

            if (!donationUpdateRequest.donationAddress().getCity().isEmpty()) {
                d.setCity(d.getCity().equals(donationUpdateRequest.donationAddress().getCity()) ? d.getCity()
                        : donationUpdateRequest.donationAddress().getCity());
            }

            if (!donationUpdateRequest.donationAddress().getZipCode().isEmpty()) {
                d.setZipCode(d.getZipCode().equals(donationUpdateRequest.donationAddress().getZipCode()) ? d.getStreet()
                        : donationUpdateRequest.donationAddress().getZipCode());
            }

            if (donationUpdateRequest.donationAddress().getPhoneNumber().describeConstable().isPresent()) {
                d.setPhoneNumber(d.getPhoneNumber() == donationUpdateRequest.donationAddress().getPhoneNumber() ? d.getPhoneNumber()
                        : donationUpdateRequest.donationAddress().getPhoneNumber());
            }

            if (donationUpdateRequest.donationAddress().getPickUpDate() != null) {
                d.setPickUpDate(d.getPickUpDate().equals(donationUpdateRequest.donationAddress().getPickUpDate()) ? d.getPickUpDate()
                        : donationUpdateRequest.donationAddress().getPickUpDate());
            }

            if (donationUpdateRequest.donationAddress().getPickUpTime() != null) {
                d.setPickUpTime(d.getPickUpTime().equals(donationUpdateRequest.donationAddress().getPickUpTime()) ? d.getPickUpTime()
                        : donationUpdateRequest.donationAddress().getPickUpTime());
            }

            if (donationUpdateRequest.receive() != null) {
                d.setReceive(d.isReceive() == donationUpdateRequest.receive() ? d.isReceive()
                        : donationUpdateRequest.receive());
            }

            if (!donationUpdateRequest.donationAddress().getPickUpComment().isEmpty()) {
                d.setPickUpComment(d.getPickUpComment().equals(donationUpdateRequest.donationAddress().getPickUpComment()) ?
                        d.getPickUpComment() : donationUpdateRequest.donationAddress().getPickUpComment());
            }

            if (donationUpdateRequest.userId().describeConstable().isPresent()) {
                d.setUser(d.getUser().getId() == donationUpdateRequest.userId() ? d.getUser()
                        :userRepository.findById(donationUpdateRequest.userId()).orElseThrow(IllegalArgumentException::new));
            }
            donationRepository.save(d);
            log.info("Updated donation: {}", d.toString());
            d.getUser().getRole().forEach(role -> role.getUser().clear());
        });

        return donation;
    }

    public Optional<Donation> deleteDonation(Long id) {
        Optional<Donation> donation = donationRepository.findById(id);
        donation.ifPresent(d -> {
            List<Category> categoriesDonationToDelete = new ArrayList<>(d.getCategory());
            User userDonationToDelete = d.getUser();
            userDonationToDelete.getRole().forEach(role -> role.getUser().clear());
            d.getCategory().clear();
            d.getUser().getRole().forEach(role -> role.getUser().clear());
            donationRepository.delete(d);
            log.info("Deleted donation:\n{}", d.toString());
            System.out.println(categoriesDonationToDelete.toString());
            donation.get().setUser(userDonationToDelete);
            donation.get().setCategory(categoriesDonationToDelete);
        });
        return donation;
    }
}
