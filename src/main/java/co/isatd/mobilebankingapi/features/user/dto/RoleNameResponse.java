package co.isatd.mobilebankingapi.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleNameResponse (
        @NotBlank
        String name
){
}
