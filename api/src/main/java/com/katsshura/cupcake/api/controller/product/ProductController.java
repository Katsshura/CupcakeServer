package com.katsshura.cupcake.api.controller.product;

import com.katsshura.cupcake.core.dto.highlight.HighlightDTO;
import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.services.highlight.HighlightService;
import com.katsshura.cupcake.core.services.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final HighlightService highlightService;

    public ProductController(final ProductService productService,
                             final HighlightService highlightService) {
        this.productService = productService;
        this.highlightService = highlightService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        log.debug("Received request to create product with info: {}", productDTO);

        final var result = this.productService.createProduct(productDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(@RequestParam(required = false) final String search,
                                                        @PageableDefault final Pageable pageable) {
        return ResponseEntity.ok(this.productService.getProducts(search, pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable final Long id) {
        return ResponseEntity.ok(this.productService.findProductById(id));
    }

    @PostMapping("/highlight/{productId}")
    public ResponseEntity<HighlightDTO> createHighlight(@PathVariable final Long productId,
                                                        @RequestBody @Valid final HighlightDTO highlightDTO) {

        final var result = this.highlightService.createHighlight(highlightDTO, productId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/highlight")
    public ResponseEntity<HighlightDTO> getEnabledHighlight() {
        final var result = this.highlightService.getHighlight();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/highlight/{id}")
    public ResponseEntity disableHighlight(@PathVariable final Long id) {
        this.highlightService.disableHighlight(id);
        return ResponseEntity.ok().build();
    }
}
