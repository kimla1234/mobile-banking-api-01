package co.isatd.mobilebankingapi.features.user.dto;

import java.time.LocalDate;

public record UserResponse(
        String uuid,
        String name,
        String profileImage,
        String gender,
        LocalDate dob
) {
}
