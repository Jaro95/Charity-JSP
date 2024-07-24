package pl.coderslab.charity.domain.donation;

import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.domain.category.Category;
import pl.coderslab.charity.domain.institution.Institution;
import pl.coderslab.charity.domain.institution.InstitutionRequest;
import pl.coderslab.charity.domain.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DonationRequest
        (
        @NotNull
        int quantity,
        @NotEmpty
        List<Category> category,
        Institution institution,
        @NotBlank
        @Size(max=100)
        String street,
        @NotBlank
        @Size(max=100)
        String city,
        @NotBlank
        @Size(max=50)
        String zipCode,
        @Min(value = 1)
        long phoneNumber,
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate pickUpDate,
        @NotNull
        LocalTime pickUpTime,
        boolean receive,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate createdDate,
        LocalTime createdTime,
        @Size(max=500)
        String pickUpComment,
        @NotNull
        Long userId
        ) {
}
