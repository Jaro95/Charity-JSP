package pl.coderslab.charity.domain.donation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/rest/donations")
public class DonationsController {

    private final DonationService donationService;

    @GetMapping("")
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public DonationResponse getDonation(@PathVariable Long id) {
        return new DonationResponse(donationService.getDonation(id));
    }

    @PostMapping("/add")
    public String addDonationUser(@RequestBody @Valid Optional<DonationRequest> donationRequest, BindingResult bindingResult) {
        return donationService.addDonation(donationRequest,bindingResult);
    }

}
