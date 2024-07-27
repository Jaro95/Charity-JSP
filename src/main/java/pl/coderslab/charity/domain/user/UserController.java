package pl.coderslab.charity.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.domain.institution.Institution;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public UserListResponse getAllUsers() {
        return new UserListResponse(userService.getAllUsers());
    }

    @GetMapping("/{role}")
    public UserListResponse getUsersWithRole(@PathVariable String role) {
        return new UserListResponse(userService.getUsersWithRole(role));
    }


    @PostMapping("/registration")
    public RegistrationResponse registration(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return userService.registrationUser(registrationRequest);
    }

    @GetMapping("/verification")
    public ActivateUserResponse activateUser(@RequestParam(required = false) ActivateUserRequest activateUserRequest) {
        return userService.activateUser(activateUserRequest);
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
    public String postRecoveryPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        return userService.resetPassword(resetPasswordRequest);
    }
}
