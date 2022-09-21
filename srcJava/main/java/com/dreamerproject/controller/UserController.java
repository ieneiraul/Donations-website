package com.dreamerproject.controller;

import com.dreamerproject.dto.UserDTO;
import com.dreamerproject.dto.UserProfileDTO;
import com.dreamerproject.model.User;
import com.dreamerproject.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping(path ="users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    //@GetMapping("/{username}")
    public User getUserByUserName(@PathVariable(name = "username") String userName) {
        return userService.getUserByUserName(userName);
    }

   @GetMapping
   @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public List<UserProfileDTO> getUsersProfile() {
        return userService.getUsersProfile();
    }

    @GetMapping("/{username}")
    public UserProfileDTO getUserProfileByUserName(@PathVariable(name = "username") String userName) {
        return userService.getUserProfileByUserName(userName);
    }

    @DeleteMapping("/{id}")
    public Map deleteUser(@PathVariable(name = "id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return Collections.singletonMap("response", userService.deleteUser(id, username));
    }

    @PutMapping(value ="/{username}")
    public Map updateUser(@PathVariable(name = "username") String username, @RequestBody UserDTO userDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernameAuth = auth.getName();

        return Collections.singletonMap("response",userService.updateUser(username, usernameAuth, userDTO));
    }
    
    @PostMapping("image/{username}")
    public Map uploadImageProfile(@PathVariable(name = "username") String username,
                                     @RequestParam(name="image") MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernameAuth = auth.getName();

        try {
            return Collections.singletonMap("response",userService.uploadImageProfile(username, usernameAuth, image));
        } catch (IOException e) {
             e.printStackTrace();
        }
        return null;
    }

}
