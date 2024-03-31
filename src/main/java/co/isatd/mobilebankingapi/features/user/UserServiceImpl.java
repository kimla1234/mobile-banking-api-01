package co.isatd.mobilebankingapi.features.user;

import co.isatd.mobilebankingapi.domain.Role;
import co.isatd.mobilebankingapi.domain.User;
import co.isatd.mobilebankingapi.features.user.dto.UserCreateRequest;
import co.isatd.mobilebankingapi.maper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public void createNew(UserCreateRequest userCreateRequest) {

        if (userRepository.existsByPhoneNumber(userCreateRequest.phoneNumber())){
            throw  new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Num have not been !"
            );
        }

        if (userRepository.existsByNationalCardId(userCreateRequest.nationalCardId())){
            throw  new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "nati have not been !"
            );
        }

        if (userRepository.existsByStudentIdCard(userCreateRequest.studentIdCard())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student card ID has already been existed!"
            );
        }

        if (!userCreateRequest.password()
                .equals(userCreateRequest.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password doesn't match!"
            );
        }



        // DTO pattern (mapstruct ft. lombok)
        User user = userMapper.fromUserCreateRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setProfileImage("avatar");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        // Assign default user role
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(()->
                                new ResponseStatusException(HttpStatus.NOT_FOUND ,
                                        "Role USER has not been found!") );
        roles.add(userRole);

        user.setRoles(roles);


        userRepository.save(user);

    }

}
