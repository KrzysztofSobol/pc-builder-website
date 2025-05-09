package pcbuilder.website.controllers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pcbuilder.website.controllers.UserController;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.services.UserService;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> addUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.CREATED);
    }
}
