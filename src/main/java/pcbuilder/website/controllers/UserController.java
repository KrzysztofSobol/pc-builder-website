package pcbuilder.website.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userEntity = userService.save(user);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        Optional<User> userEntity = userService.findById(id);

        return userEntity.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        if(!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setUserID(id);
        User userEntity = userService.update(user);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable UUID id, @RequestBody User user) {
        if(!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setUserID(id);
        User userEntity = userService.partialUpdate(id, user);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        Optional<User> user = userService.findById(id);

        if(user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(user.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
