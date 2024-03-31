package co.isatd.mobilebankingapi.features.user;

import co.isatd.mobilebankingapi.features.user.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UserService {

    void createNew(UserCreateRequest userCreateRequest);


}
