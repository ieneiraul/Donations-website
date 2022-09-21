package com.dreamerproject.dto;

import com.dreamerproject.model.Role;
import lombok.Data;

@Data
public class UserProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String imageUrl;
    private String phone;
    private String iban;
    private String ocupation;
    private Role role;
}
