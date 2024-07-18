package pl.coderslab.charity.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.dto.RegistrationDTO;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.service.CurrentUser;
import pl.coderslab.charity.service.UserService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/charity")
public class HomeController {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final UserService userService;

    @GetMapping("")
    public String homeAction(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("quantityBag", donationRepository.allQuantityBag().orElse(0L));
        model.addAttribute("allDonations", donationRepository.allDonations().orElse(0L));
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
        return "redirect:/charity/login";
    }

    @GetMapping("/verification")
    public String validateUser(@RequestParam(required = false) String token, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.findByToken(token);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("messageEnabled", "Token nieprawidłowy lub jego ważność wygasła");
            return "redirect:/gowithme/login";
        }
        user.get().setEnabled(true);
        user.get().setToken("verified");
        userService.updateUser(user.get());
        redirectAttributes.addFlashAttribute("message", "Konto zostało aktywowane");
        return "redirect:/charity/login";
    }

    @GetMapping("/validate")
    public String validateUser(@AuthenticationPrincipal CurrentUser currentUser, RedirectAttributes redirectAttributes) {
        if (!currentUser.getUser().isEnabled()) {
            redirectAttributes.addFlashAttribute("messageEnabled", "Konto nie zostało aktywowane");
            return "redirect:/charity/login";
        }
        return "redirect:/charity/donation";
    }
}


