package pl.coderslab.charity.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class RegistrationDTO {

    @NotBlank
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 30)
    private String firstName;
    @NotBlank
    @Size(max = 30)
    private String lastName;
    @NotBlank
    @Size(min = 8)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Hasło musi zawierać wielkie litery, małe litery, cyfry i znaki specjalne"
    )
    private String password;
    @NotBlank
    private String repeatPassword;
}
