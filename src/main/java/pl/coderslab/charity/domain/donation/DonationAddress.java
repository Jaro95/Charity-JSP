package pl.coderslab.charity.domain.donation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DonationAddress {

    @Size(max=100)
    private String street;
    @Size(max=100)
    private String city;
    @Size(max=50)
    private String zipCode;
    @Min(value = 1)
    private Long phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;
    private LocalTime pickUpTime;
    @Size(max=500)
    private String pickUpComment;

}
