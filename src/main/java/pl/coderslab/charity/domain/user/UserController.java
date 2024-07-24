package pl.coderslab.charity.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/list/users")
    public List<User> getOnlyUsers() {
        return userService.getOnlyUsers();
    }

    @GetMapping("/list/admins")
    public List<User> getOnlyAdmins() {
        return userService.getOnlyAdmins();
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
