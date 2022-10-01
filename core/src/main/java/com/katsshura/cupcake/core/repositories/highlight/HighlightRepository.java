package com.katsshura.cupcake.core.repositories.highlight;

import com.katsshura.cupcake.core.entities.highlight.HighlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HighlightRepository extends JpaRepository<HighlightEntity, Long> {
}
