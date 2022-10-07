package com.katsshura.cupcake.core.repositories.highlight;

import com.katsshura.cupcake.core.entities.highlight.HighlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HighlightRepository extends JpaRepository<HighlightEntity, Long> {
    Optional<HighlightEntity> findFirstByEnabledIsTrue();
}
