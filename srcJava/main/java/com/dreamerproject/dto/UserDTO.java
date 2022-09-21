package com.dreamerproject.dto;

import com.dreamerproject.model.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserDTO {
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String password;
    private String imageUrl;
    private String phone;
    private String iban;
    private String ocupation;
    private Role role;
}
