package co.isatd.mobilebankingapi.features.user;

import co.isatd.mobilebankingapi.base.BasedMessage;
import co.isatd.mobilebankingapi.features.user.dto.UserCreateRequest;
import co.isatd.mobilebankingapi.features.user.dto.UserResponse;
import co.isatd.mobilebankingapi.features.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    void createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createNew(userCreateRequest);

    }

    @PatchMapping("/{uuid}")
    UserResponse updateByUuid (@PathVariable String uuid , UserUpdateRequest userUpdateRequest){

        return userService.updateByUuid(uuid,userUpdateRequest);
    }

    @GetMapping("/{uuid}")
    UserResponse findByUuid(@PathVariable String uuid ){
        return  userService.findByUuid(uuid);
    }

    @PutMapping("/{uuid}/block")
    BasedMessage blockByUuid(@PathVariable String uuid) {
        return userService.blockByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteByUuid(@PathVariable String uuid) {
        userService.deleteByUuid(uuid);
    }

    @PutMapping("/{uuid}/disable")
    BasedMessage disableUserByUuid(@PathVariable String uuid) {
        return userService.disableByUuid(uuid);
    }

    @GetMapping
    Page<UserResponse> findList(@RequestParam(required = false , defaultValue = "0") int page,
                                @RequestParam(required = false , defaultValue = "2") int limit){
        return userService.findList(page,limit);
    }

}




// delete user by UUID
// Disable user by UUID
// Enable user by UUID