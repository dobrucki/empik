package org.dobrucki.empik.user;

import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> fetchUser(@PathVariable("login") String login) {
        return ResponseEntity.of(
                service.fetch(login).map(Model::from)
        );
    }

    @Value
    static class Model {
        String id;
        String login;
        String name;
        String type;
        String avatarUrl;
        String createdAt;
        String calculations;

        static Model from(User user) {
            return new Model(
                    user.getId(),
                    user.getLogin(),
                    user.getName(),
                    user.getType(),
                    user.getAvatarUrl(),
                    user.getCreatedAt(),
                    user.getCalculations()
            );
        }
    }
}
