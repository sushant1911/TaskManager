package com.example.TaskManager.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "user name cannot be null")
    @Column(nullable = false)
    private String username;

    @Pattern(regexp = "^(?=.*[!@#$%^&*()-+=])(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$", message = "Password must be at least 8 characters long and contain at least one special character, one digit, one uppercase letter, and one lowercase letter.")
    @Column(nullable = false)
    private String password;
    @Email
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Column(nullable = false)
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's role as a GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implement account expiration logic if needed
        return true; // Assuming accounts never expire
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implement account locking logic if needed
        return true; // Assuming accounts are never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implement credentials expiration logic if needed
        return true; // Assuming credentials never expire
    }

    @Override
    public boolean isEnabled() {
        // Implement user enable/disable logic if needed
        return true; // Assuming user is always enabled
    }
}
