package pl.coderslab.charity.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{role}")
    public List<User> getUsersWithRole(@PathVariable String role) {
        return userService.getUsersWithRole(role);
    }


    @PostMapping("/registration")
    public String registration(@RequestBody @Valid RegistrationRequest registrationRequest, BindingResult result) {
        return userService.registrationUser(registrationRequest,result);
    }

    @GetMapping("/verification")
    public String activateUser(@RequestParam(required = false) String token) {
        return userService.activateUser(token);
    }

    @PostMapping("/recovery/{email}")
    public String postRecoveryPasswordEmail(@PathVariable String email) {
        return userService.resetPasswordCheckEmail(email);
    }

    @GetMapping("/recovery/password")
    public String getRecoveryPassword(@RequestParam(required = false) String token) {
        return userService.resetPasswordCheckToken(token);
    }

    @PostMapping("/recovery/password")
    public String postRecoveryPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult) {
        return userService.resetPassword(resetPasswordRequest,bindingResult);
    }
}
