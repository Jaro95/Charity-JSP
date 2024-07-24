package pl.coderslab.charity.adapter.controllerForJsp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.domain.user.EditPassword;
import pl.coderslab.charity.domain.user.RecoveryPasswordRepository;
import pl.coderslab.charity.domain.user.UserService;
import pl.coderslab.charity.infrastructure.security.CurrentUser;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/rest")
public class RestHomeController {

    private final UserService userService;
    private final RecoveryPasswordRepository recoveryPasswordRepository;



    @PostMapping("/recovery/password")
    public String postRecoveryPassword(@Valid EditPassword editPassword, BindingResult result, @RequestParam String email ,
                                       Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("editPassword", new EditPassword());
            model.addAttribute("email", email);
            model.addAttribute("errors", result.getAllErrors());
            return "home/recoveryPassword";
        }
        if (!editPassword.getPassword().equals(editPassword.getRepeatPassword())) {
            model.addAttribute("email", email);
            model.addAttribute("wrongRepeatPassword", "Hasła nie są takie same");
            return "home/recoveryPassword";
        }
        userService.resetPassword(email, editPassword.getPassword());
        redirectAttributes.addFlashAttribute("message", "Hasło zostało zmienione");
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


