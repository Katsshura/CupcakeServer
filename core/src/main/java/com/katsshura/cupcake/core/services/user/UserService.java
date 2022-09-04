package com.katsshura.cupcake.core.services.user;

import com.katsshura.cupcake.core.entities.user.UserEntity;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
