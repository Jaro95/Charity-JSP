package pl.coderslab.charity.adapter.controllerToChangeOnRest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.infrastructure.security.CurrentUser;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/rest")
public class RestHomeController {

    @GetMapping("/validate")
    public String validateUser(@AuthenticationPrincipal CurrentUser currentUser, RedirectAttributes redirectAttributes) {
        if (!currentUser.getUser().isEnabled()) {
            redirectAttributes.addFlashAttribute("messageEnabled", "Konto nie zostało aktywowane");
            return "redirect:/charity/login";
        }
        return "redirect:/charity/donation";
    }
}


