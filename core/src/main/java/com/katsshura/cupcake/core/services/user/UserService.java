package com.katsshura.cupcake.core.services.user;

import com.katsshura.cupcake.core.dto.user.UserDTO;
import com.katsshura.cupcake.core.exceptions.AlreadyExistsException;
import com.katsshura.cupcake.core.mapper.user.UserMapper;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO persistUser(final UserDTO userDTO) {
        if (this.userRepository.existsByEmailOrCpf(
                userDTO.getEmail(),
                userDTO.getCpf())
        ) {
            log.error("User already registered with provided information: {}", userDTO);
            throw new AlreadyExistsException(
                    String.format("User with cpf: %s and email: %s",
                            userDTO.getCpf(),
                            userDTO.getEmail())
            );
        }

        final var result = this.userRepository.save(this.userMapper.toEntity(userDTO));
        log.debug("User saved successfully in database: {}", result);

        return this.userMapper.toDTO(result);
    }
}
