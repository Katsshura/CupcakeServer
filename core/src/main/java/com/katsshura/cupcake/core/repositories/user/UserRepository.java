package com.katsshura.cupcake.core.repositories.user;

import com.katsshura.cupcake.core.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByCpf(String cpf);

    boolean existsByEmailOrCpf(String email, String cpf);

    Optional<UserEntity> findByEmailOrCpf(String email, String Cpf);
}
