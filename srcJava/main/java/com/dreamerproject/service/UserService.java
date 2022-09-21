package com.dreamerproject.service;

import com.cloudinary.utils.ObjectUtils;
import com.dreamerproject.converter.UserConverter;
import com.dreamerproject.dto.UserDTO;
import com.dreamerproject.dto.UserProfileDTO;
import com.dreamerproject.model.BOOLEAN;
import com.dreamerproject.model.Role;
import com.dreamerproject.model.User;
import com.dreamerproject.model.mail.ConfirmationToken;
import com.dreamerproject.repository.UserRepository;
import com.dreamerproject.model.validators.EmailValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudinary.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private ConfirmationTokenService confirmationTokenService;
    private EmailService emailSender;

    private UserConverter userConverter = new UserConverter();
    private EmailValidator emailValidator= new EmailValidator();
    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dtb67cyxp",
            "api_key", "463459147168639",
            "api_secret", "mwmzpTQwtF2LRqM4x2I9mW4FhpI",
            "secure", true));
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final static String USER_NOT_FOUND_MSG="user with email %s not found";
    @Value("${server.port}")
    private String port;

    public UserService(UserRepository userRepository, ConfirmationTokenService confirmationTokenService, EmailService emailSender) {
        this.userRepository = userRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName).get();
    }

    public List<UserProfileDTO> getUsersProfile() {
        return userConverter.entityProfileToDto(userRepository.getUserByEnabled(BOOLEAN.TRUE));
    }

    public UserProfileDTO getUserProfileByUserName(String userName) {
        return userConverter.entityProfileToDto(userRepository.getUserByUserName(userName).get());
    }

    public String deleteImageProfile(String imageUrl) {
        log.debug("deleteImageProfile method accessed");
        try{
            Map result = cloudinary.uploader().destroy(imageUrl,
                    ObjectUtils.asMap("resource_type","image"));
            log.debug(result.toString());
        } catch (IOException e) {
            log.debug("eroare la stergerea imaginii!");
            return "eroare la stergerea imaginii!";
        }
        log.debug("ai sters imaginea cu succes!");
        return "ai sters imaginea cu succes!";
    }

    public String uploadImageProfile(String username, String usernameAuth, MultipartFile image) throws IOException {
        File file =new ClassPathResource(
                "data/profil.jpeg").getFile();
        if(!image.isEmpty()) {
            if(!image.getOriginalFilename().endsWith(".jpg") &&!image.getOriginalFilename().endsWith(".jpeg") &&
                    !image.getOriginalFilename().endsWith(".png")) {
                return "Trebuie sa incarcati o poza!";
            }
            User userRequester = userRepository.getUserByUserName(usernameAuth).get();
            if(userRequester.getUsername().equals(username)) {
                if(!userRequester.getImageUrl().isEmpty()) {
                    //deleteImageProfile(userRequester.getImageUrl());
                }
                image.transferTo(file);
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                        "folder", "dreamsForFuture"));
                String url = (String) uploadResult.get("url");
                userRepository.updateProfilePicture(userRequester.getId(), url);
                return "ai uploadat poza cu succes!";
            }
            else {
                return "nu ai dreptul sa uploadezi poza de profil!";
            }

        }
        return "eroare la incarcare";
    }

    public String updateUser(String username, String usernameAuth, UserDTO userDTO) {
        User userRequester = userRepository.getUserByUserName(usernameAuth).get();
        Optional<User> user1 = userRepository.getUserByUserName(username);
        if(user1.isPresent()) {
            if(user1.get().getRole().equals(Role.ADMIN)) {
                return "Nu poti modifica un cont de admin!";
            }
            else if(userRequester.getUsername().equals(username)) {
                User user = userConverter.updateEntity(user1.get(), userDTO);
                userRepository.updateUser(user.getId(), user.getPassword(), user.getIban(), user.getPhone(),user.getImageUrl());
            }
            else {
                return "nu ai dreptul sa stergi acest user!";
            }
        }
        else {
            return "Nu exista un user cu username-ul dat!";
        }
        return "Ai modificat userul cu succes!";
    }

    public String deleteUser(Long id, String username) {
        User userRequester = userRepository.getUserByUserName(username).get();
        User user = userRepository.getById(id);
        if(user!=null) {
            if(user.getRole().equals(Role.ADMIN)) {
                return "Nu poti sterge un cont de admin!";
            }
            if(userRequester.getRole().equals(Role.ADMIN)) {
                userRepository.delete(user);
            }
            else if(userRequester.getId()==id && !user.getRole().equals(Role.MANAGER)) {
                userRepository.delete(user);
            }
            else {
                return "nu ai dreptul sa stergi acest user!";
            }
        }
        else {
            return "Nu exista un user cu id-ul dat!";
        }
        return "Ai sters userul cu succes!";
    }

    public String register(UserDTO userDTO) {
        boolean isValidEmail = emailValidator.test(userDTO.getMail());
        if(!isValidEmail) {
            throw new IllegalStateException("Email is not valid!");
        }
        User user = userConverter.dtoToEntity(userDTO);
        user.setEnabled(BOOLEAN.FALSE);
        user.setLocked(BOOLEAN.FALSE);
        String token = signUpUser(user);
        String link = "http://localhost:"+port+"/registration/confirm?token=" + token;
        emailSender.send(
                userDTO.getMail(),
                emailSender.buildEmail(userDTO.getFirstName(), link));
        return "Confirmation has been sent to the mail";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableUser(confirmationToken.getUser().getMail());
        return "confirmed";
    }


    public String signUpUser(User user) {
        Optional<User> existsMail = userRepository.findByMail(user.getMail());
        Optional<User> existsUsername = userRepository.getUserByUserName(user.getUsername());
        if(existsMail.isPresent()) {
            if(existsMail.get().isEnabled())
                throw new IllegalStateException("Email is already taken!");
        }

        if(existsUsername.isPresent()) {
            if(existsUsername.get().isEnabled())
                throw new IllegalStateException("Username is already taken!");
        }
        if(user.getRole().equals(Role.ADMIN)) {
            throw new IllegalStateException("You can't be admin!");
        }
        if(user.getRole().equals(Role.MANAGER)) {
            throw new IllegalStateException("You can't be manager!");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if(!existsUsername.isPresent() && !existsMail.isPresent()) {
            userRepository.save(user);
        }
        else {
            user = existsMail.get();
        }

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUserName(username).orElseThrow(()->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

}
