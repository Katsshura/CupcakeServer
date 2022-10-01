package com.katsshura.cupcake.api.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.katsshura.cupcake.core.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDTO implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    @JsonIgnore
    private final String username;

    private final String email;

    private final String name;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsDTO buildFrom(final UserDTO userDTO) {
        return UserDetailsDTO.builder()
                .id(userDTO.getId())
                .username(userDTO.getEmail())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
