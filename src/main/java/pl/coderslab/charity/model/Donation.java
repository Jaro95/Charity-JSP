package pl.coderslab.charity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private int quantity;
    @NotEmpty
    @ManyToMany
    private List<Category> categories;
    @ManyToOne
    private Institution institution;
    @NotBlank
    @Size(max=100)
    private String street;
    @NotBlank
    @Size(max=100)
    private String city;
    @NotBlank
    @Size(max=50)
    private String zipCode;
    @Min(value = 1)
    private long phoneNumber;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;
    @NotNull
    private LocalTime pickUpTime;
    @Size(max=500)
    private String pickUpComment;
    @ManyToOne
    private User user;
}
