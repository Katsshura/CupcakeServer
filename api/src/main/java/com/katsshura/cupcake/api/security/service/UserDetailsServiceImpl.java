package com.katsshura.cupcake.api.security.service;

import com.katsshura.cupcake.api.security.dto.UserDetailsDTO;
import com.katsshura.cupcake.core.services.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = this.userService.findByEmail(username);

        if (user == null) throw new UsernameNotFoundException("User Not Found with email: " + username);

        return UserDetailsDTO.buildFrom(user);
    }
}
