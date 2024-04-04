package co.isatd.mobilebankingapi.features.user;

import co.isatd.mobilebankingapi.base.BasedMessage;
import co.isatd.mobilebankingapi.features.user.dto.UserCreateRequest;
import co.isatd.mobilebankingapi.features.user.dto.UserResponse;
import co.isatd.mobilebankingapi.features.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserService {

    void createNew(UserCreateRequest userCreateRequest);
    UserResponse updateByUuid(String uuid , UserUpdateRequest userUpdateRequest);

    UserResponse findByUuid(String uuid);
    BasedMessage blockByUuid(String uuid);
    void deleteByUuid(String uuid);

    BasedMessage disableByUuid(String uuid);


    Page<UserResponse> findList(int page, int limit);
}
