package co.isatd.mobilebankingapi.maper;

import co.isatd.mobilebankingapi.domain.User;
import co.isatd.mobilebankingapi.features.user.dto.UserCreateRequest;
import co.isatd.mobilebankingapi.features.user.dto.UserDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // SourceType = UserCreateRequest (Parameter)
    // TargetType = User (ReturnType)
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    void fromUserCreateRequest2(@MappingTarget User user, UserCreateRequest userCreateRequest);

    UserDetailResponse toUserDetailsResponse(User user);

}
