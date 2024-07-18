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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.dto.EditPassword;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.CurrentUser;
import pl.coderslab.charity.service.UserService;

@Controller
@RequestMapping("/charity/donation")
@AllArgsConstructor
@Slf4j
public class DonationController {

    private final InstitutionRepository institutionRepository;
    private final CategoryRepository categoryRepository;
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping("")
    public String giveDonation(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("donation", new Donation());
        model.addAttribute("categories", categoryRepository.findAll());
        return "application/main";
    }

    @PostMapping("")
    public String postGiveDonation(@Valid Donation donation, @AuthenticationPrincipal CurrentUser currentUser,
                                   BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("institutions", institutionRepository.findAll());
            model.addAttribute("donation", donation);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("messageError", "Napotkano błędy w formularzu");
            return "application/main";
        }
        donation.setUser(currentUser.getUser());
        donationRepository.save(donation);
        redirectAttributes.addFlashAttribute("donationSuccessFull", "completed");
       // log.info("Added donation {}", donation.toString());
        return "redirect:/charity/donation";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userRepository.findById(currentUser.getUser().getId()).get();
        model.addAttribute("name", user.getName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("donation", donationRepository.allDonationsUser(user.getId()).get());
        return "application/profile";
    }

    @GetMapping("/profile/edit")
    public String getEditProfile(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("user", currentUser.getUser());
        return "application/editProfile";
    }

    @PostMapping("/profile/edit")
    public String postEditProfile(@Valid User user, Model model,
                                  BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", result.getAllErrors());
            return "application/editProfile";
        }
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/donation/profile";
    }

    @GetMapping("/profile/password")
    public String getEditProfile(Model model) {
        model.addAttribute("editPassword", new EditPassword());
        return "application/editPassword";
    }

    @PostMapping("/profile/password")
    public String postEditProfile(@Valid EditPassword editPassword, @AuthenticationPrincipal CurrentUser currentUser,
                                  Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("editPassword", editPassword);
            model.addAttribute("errors", result.getAllErrors());
            return "application/editPassword";
        }

        if (!editPassword.getPassword().equals(editPassword.getRepeatPassword())) {
            model.addAttribute("editPassword", editPassword);
            model.addAttribute("messageError", "Hasła nie są takie same");
            return "application/editPassword";
        }
        userService.updateUser(currentUser.getUser(),editPassword.getPassword());
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/donation/profile";
    }
}
