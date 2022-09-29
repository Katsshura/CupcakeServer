package com.katsshura.cupcake.api.controller.product;

import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.services.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
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
}
