package com.dreamerproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String password;
    @Column(name = "image_url")
    private String imageUrl;
    private String phone;
    private String ocupation;
    private String iban;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "locked")
    @Enumerated(EnumType.STRING)
    private BOOLEAN locked;
    @Column(name = "enabled")
    @Enumerated(EnumType.STRING)
    private BOOLEAN enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    public User(String firstName, String lastName, String userName, String mail, String password, String imageUrl, String phone, String iban, String ocupation, Role role, BOOLEAN locked, BOOLEAN enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.iban = iban;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
        this.ocupation = ocupation;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return !locked.equals(BOOLEAN.TRUE);
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled.equals(BOOLEAN.TRUE);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", mail='" + mail + '\'' +
                ", role=" + role +
                ", locked=" + locked +
                ", enabled=" + enabled +
                '}';
    }
}
