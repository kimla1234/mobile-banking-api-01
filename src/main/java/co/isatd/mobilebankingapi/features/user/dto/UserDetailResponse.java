package co.isatd.mobilebankingapi.features.user.dto;

import co.isatd.mobilebankingapi.domain.Role;

import java.time.LocalDate;
import java.util.List;

public record UserDetailResponse(
        String uuid,
        String name,
        String profileImage,
        String gender,
        LocalDate dob,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        List<RoleNameResponse> role

) {

}
