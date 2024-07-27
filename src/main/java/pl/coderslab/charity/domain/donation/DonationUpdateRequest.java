package pl.coderslab.charity.domain.donation;

import jakarta.validation.constraints.*;

import java.util.List;

public record DonationUpdateRequest
        (
                @Min(value = 1)
                Integer quantity,
                List<Long> categoryIdList,
                Long institutionId,
                DonationAddress donationAddress,
                Boolean receive,
                Long userId
        )
{
}