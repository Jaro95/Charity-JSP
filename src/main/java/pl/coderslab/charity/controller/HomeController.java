package pl.coderslab.charity.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.dto.RegistrationDTO;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.service.UserService;

@Controller
@AllArgsConstructor
@Slf4j
public class HomeController {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final UserService userService;

    @GetMapping("/")
    public String homeAction(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("quantityBag", donationRepository.allQuantityBag());
        model.addAttribute("allDonations", donationRepository.allDonations());
        return "home/main";
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("wrongPassword", error);
        return "home/login";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "home/registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@Valid RegistrationDTO registrationDTO, RedirectAttributes redirectAttributes,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registrationDTO", registrationDTO);
            model.addAttribute("errors", result.getAllErrors());
            return "home/registration";
        }
        userService.saveUser(registrationDTO);
        redirectAttributes.addFlashAttribute("message", "Rejestracja przebiegła pomyślnie");
        return "redirect:/login";
    }
}


