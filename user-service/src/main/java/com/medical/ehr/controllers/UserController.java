package com.medical.ehr.controllers;

import com.medical.ehr.dto.requests.AddUserRequest;
import com.medical.ehr.dto.requests.EditUserRequest;
import com.medical.ehr.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getByEmail")
    public ResponseEntity<Object> getUserByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/getByUsername")
    public ResponseEntity<Object> getUserByUsername(@RequestParam("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/showUsers")
    public ResponseEntity<Object> showUsers() {
        return new ResponseEntity<>(userService.showUsers(), HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody AddUserRequest addUserRequest) {
        userService.addUser(addUserRequest);
        return new ResponseEntity<>("User added successfully.", HttpStatus.CREATED);
    }

    @PostMapping("/editUser/{userId}")
    public ResponseEntity<Object> editUser(@RequestBody EditUserRequest editUserRequest, @PathVariable("userId") Long userId) {
        userService.editUser(editUserRequest, userId);
        return new ResponseEntity<>("User updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("/editProfile")
    public ResponseEntity<Object> editProfile(@RequestBody EditUserRequest editUserRequest) {
        userService.editProfile(editUserRequest);
        return new ResponseEntity<>("Profile updated successfully.", HttpStatus.OK);
    }

}
