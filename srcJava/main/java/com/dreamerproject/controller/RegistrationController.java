package com.dreamerproject.controller;

import com.dreamerproject.dto.UserDTO;
import com.dreamerproject.service.UserService;
import lombok.AllArgsConstructor;
import org.owasp.encoder.Encode;
import org.owasp.encoder.Encoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {
    private UserService userService;


    @PostMapping
    public Map register(@RequestBody UserDTO userDTO) {
        try{

            return Collections.singletonMap("response", userService.register(userDTO));
        }
        catch (IllegalStateException e) {
            return Collections.singletonMap("response", e.getMessage());
        }

    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        try{
            return userService.confirmToken(Encode.forHtml(token));
        }
        catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
