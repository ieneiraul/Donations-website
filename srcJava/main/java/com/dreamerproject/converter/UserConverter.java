package com.dreamerproject.converter;

import com.dreamerproject.dto.UserDTO;
import com.dreamerproject.dto.UserProfileDTO;
import com.dreamerproject.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserProfileDTO entityProfileToDto(User user) {
        UserProfileDTO userDTO = new UserProfileDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUsername());
        userDTO.setMail(user.getMail());
        if(user.getIban()!=null)
            userDTO.setIban(user.getIban());
        if(user.getPhone()!=null)
            userDTO.setPhone(user.getPhone());
        if(user.getOcupation()!=null)
            userDTO.setOcupation(user.getOcupation());
        if(user.getImageUrl()!=null)
            userDTO.setImageUrl(user.getImageUrl());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public List<UserProfileDTO> entityProfileToDto(List<User> userEntities) {
        return userEntities.stream().map(this::entityProfileToDto).collect(Collectors.toList());
    }

    public UserDTO entityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setMail(user.getMail());
        userDTO.setPassword(null);
        userDTO.setUserName(user.getUsername());
        userDTO.setRole(user.getRole());
        if(user.getIban()!=null)
            userDTO.setIban(user.getIban());
        if(user.getPhone()!=null)
            userDTO.setPhone(user.getPhone());
        if(user.getOcupation()!=null)
            userDTO.setOcupation(user.getOcupation());
        if(user.getImageUrl()!=null)
            userDTO.setImageUrl(user.getImageUrl());

        return userDTO;
    }

    public List<UserDTO> entityToDto(List<User> userEntities) {
        return userEntities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public User dtoToEntity(UserDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMail(userDto.getMail());
        user.setPassword(userDto.getPassword());
        user.setUserName(userDto.getUserName());
        user.setRole(userDto.getRole());
        if(userDto.getIban()!=null)
            user.setIban(userDto.getIban());
        if(userDto.getPhone()!=null)
            user.setPhone(userDto.getPhone());
        if(userDto.getOcupation()!=null)
            user.setOcupation(userDto.getOcupation());
        if(userDto.getImageUrl()!=null)
            user.setImageUrl(userDto.getImageUrl());

        return user;
    }

    public User updateEntity(User user, UserDTO userDto) {
        if(userDto.getPassword()!=null && !userDto.getPassword().equals(""))
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        if(userDto.getIban()!=null && !userDto.getIban().equals(""))
            user.setIban(userDto.getIban());
        if(userDto.getPhone()!=null && !userDto.getPhone().equals(""))
            user.setPhone(userDto.getPhone());
        if(userDto.getOcupation()!=null && !userDto.getOcupation().equals(""))
            user.setOcupation(userDto.getOcupation());
        if(userDto.getImageUrl()!=null && !userDto.getImageUrl().equals(""))
            user.setImageUrl(userDto.getImageUrl());

        return user;
    }

    public List<User> dtoToEntity(List<UserDTO> userDto) {
        return userDto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
