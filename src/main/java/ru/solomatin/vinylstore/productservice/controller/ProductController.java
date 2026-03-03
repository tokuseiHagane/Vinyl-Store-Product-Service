package ru.solomatin.vinylstore.productservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.solomatin.vinylstore.productservice.dto.VinylDTO;
import ru.solomatin.vinylstore.productservice.model.Vinyl;
import ru.solomatin.vinylstore.productservice.service.ProductService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/vinyl/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody VinylDTO product) {
        productService.saveNewVinyl(product);
    }

    @GetMapping("/all")
    public List<Vinyl> getAllProducts() {
        return productService.getAllVinyl();
    }

    @GetMapping("/vinyl/{id}")
    public Vinyl getProductById(@PathVariable String id) {
        try {
            return productService.getVinylById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/vinyl/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        try {
            productService.deleteVinylById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
