package co.isatd.mobilebankingapi.features.user;

import co.isatd.mobilebankingapi.base.BasedMessage;
import co.isatd.mobilebankingapi.domain.Role;
import co.isatd.mobilebankingapi.domain.User;
import co.isatd.mobilebankingapi.features.user.dto.UserCreateRequest;
import co.isatd.mobilebankingapi.features.user.dto.UserResponse;
import co.isatd.mobilebankingapi.features.user.dto.UserUpdateRequest;
import co.isatd.mobilebankingapi.maper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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

        // Create dynamic roles
        userCreateRequest.role().forEach(role -> {
            Role newRole = roleRepository.findByName(role.name())
                    .orElseThrow(()->
                            new ResponseStatusException(HttpStatus.NOT_FOUND ,
                                    "Role USER has not been found!") );
            roles.add(newRole);
        });


        roles.add(userRole);
        user.setRoles(roles);


        userRepository.save(user);

    }

    @Override
    public UserResponse updateByUuid(String uuid, UserUpdateRequest userUpdateRequest) {
        // check Uuid if  exist
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND ,
                                "Role USER has not been found!") );
        log.info("found User : { }" + user);

        userMapper.fromUserUpdateRequest(userUpdateRequest , user);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user) ;
    }

    @Override
    public UserResponse findByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND ,
                                "Role USER has not been found!") );

        return userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public BasedMessage blockByUuid(String uuid) {

        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User has not been found!");
        }

        userRepository.blockByUuid(uuid);

        return new BasedMessage("User has been blocked");
    }

    @Override
    public void deleteByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User not found with UUID: " + uuid));

        userRepository.deleteByUuid(user);
    }

    @Override
    public BasedMessage disableByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User not found with UUID: " + uuid));
        user.setIsDeleted(user.getIsDeleted());
        userRepository.save(user);
        return new BasedMessage("User disabled successfully");
    }

    @Override
    public Page<UserResponse> findList(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page,limit);
        Page<User> users = userRepository.findAll(pageRequest);
        return users.map(userMapper::toUserResponse);
    }


}
