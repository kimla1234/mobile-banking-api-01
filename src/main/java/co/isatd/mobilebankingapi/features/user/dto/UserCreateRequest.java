package co.isatd.mobilebankingapi.features.user.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;

import java.util.List;

public record UserCreateRequest(
        @NotNull(message = "Pin is required")
        @Positive
        @Max(9999)
        Integer pin,
        @NotBlank
        @Size(max = 20)
        String phoneNumber,
        @NotBlank
        String password,
        @NotBlank
        @Size(max = 40)
        String name,
        @NotBlank
        @Size(max = 6)
        String gender,

        @NotBlank
        String confirmedPassword,
        @NotNull
        @Size(max = 20)
        String nationalCardId,
        @Size(max = 20)
        String studentIdCard,

        @NotEmpty
        List<RoleRequest> role
) {

}
