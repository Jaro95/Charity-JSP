package pl.coderslab.charity.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

@Controller
@RequestMapping("/charity/donation")
@AllArgsConstructor
@Slf4j
public class DonationController {

    private final InstitutionRepository institutionRepository;
    private final CategoryRepository categoryRepository;
    private final DonationRepository donationRepository;

    @GetMapping("")
    public String giveDonation(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("donation", new Donation());
        model.addAttribute("categories", categoryRepository.findAll());
        return "application/main";
    }

    @PostMapping("")
    public String postGiveDonation(@Valid Donation donation, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("institutions", institutionRepository.findAll());
            model.addAttribute("donation", donation);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("messageError", "Napotkano błędy w formularzu");
            return "application/main";
        }
        donationRepository.save(donation);
        redirectAttributes.addFlashAttribute("donationSuccessFull", "completed");
        log.info("Added donation {}", donation.toString());
        return "redirect:/charity/donation";
    }
}
