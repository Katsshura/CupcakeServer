package com.katsshura.cupcake.core.services.highlight;

import com.katsshura.cupcake.core.dto.highlight.HighlightDTO;
import com.katsshura.cupcake.core.exceptions.NotFoundException;
import com.katsshura.cupcake.core.mapper.highlight.HighlightMapper;
import com.katsshura.cupcake.core.repositories.highlight.HighlightRepository;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HighlightService {

    private final HighlightRepository repository;
    private final ProductRepository productRepository;
    private final HighlightMapper highlightMapper;

    public HighlightService(final HighlightRepository repository,
                            final HighlightMapper highlightMapper,
                            final ProductRepository productRepository) {
        this.repository = repository;
        this.highlightMapper = highlightMapper;
        this.productRepository = productRepository;
    }

    public HighlightDTO createHighlight(final HighlightDTO highlightDTO, final Long productId) {
        final var product = productRepository.findById(productId);

        if (product.isEmpty()) throw new NotFoundException(String.format("Product with id: {%s}", productId));

        final var lastEnabledHighlight = this.repository.findFirstByEnabledIsTrue();
        lastEnabledHighlight.ifPresent(entity -> disableHighlight(entity.getId()));

        final var highlightEntity = this.highlightMapper.toEntity(highlightDTO);
        highlightEntity.setProduct_id(productId);
        highlightEntity.setEnabled(true);

        final var result = this.repository.save(highlightEntity);

        return this.highlightMapper.toDTO(result, product.get());
    }

    public HighlightDTO getHighlight() {
        final var result = this.repository.findFirstByEnabledIsTrue();

        if (result.isEmpty()) throw new NotFoundException("Couldn't find any enabled highlight");

        final var product = this.productRepository.findById(result.get().getProduct_id());

        return this.highlightMapper.toDTO(result.get(), product.get());
    }

    public void disableHighlight(final Long id) {
        final var highlightOpt = this.repository.findById(id);

        if (highlightOpt.isEmpty()) throw new NotFoundException(String.format("Highlight with id: {%s}", id));

        final var highlight = highlightOpt.get();
        highlight.setEnabled(false);

        this.repository.save(highlight);

        log.debug("Highlight with id: [{}] disabled with sucess!", id);
    }
}
