package pl.coderslab.charity.domain.donation;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DonationUpdateRequest
        (
                @Min(value = 1)
                Integer quantity,
                List<Long> categoryId,
                Long institutionId,
                DonationAddress donationAddress,
                Boolean receive,
                Long userId
        )
{
}