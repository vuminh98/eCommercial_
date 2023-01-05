package com.example.comercial.controller;

import com.example.comercial.model.product.Product;
import com.example.comercial.service.extend.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Value("${upload.path}")
    private String link;

    @Value("${display.path}")
    private String displayLink;

    @GetMapping("/product")
    public ResponseEntity<Iterable<Product>> findAllProduct() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> findAllPageProduct(@PageableDefault(size = 3) Pageable pageable) {
        return new ResponseEntity<>(productService.findAllPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<Iterable<Product>> searchProduct(@RequestParam("search") String name) {
        return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> findByIdProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }


    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product,
                                                 @PathVariable Long id) {
        Optional<Product> productUpdated = productService.findById(id);
        if (productUpdated.isPresent()) {
            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.remove(id);
        return new ResponseEntity<>("Delete done!", HttpStatus.OK);
    }
//
    @PostMapping(value = "/uploadProduct")
    public ResponseEntity<Product> createUploadProduct(@RequestPart(value = "file", required = false) MultipartFile file,
                                                       @RequestPart("product") Product product ) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File(link + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setImage(displayLink + fileName);
        } else {
            product.setImage(displayLink + "7up.jpg");
        }
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PostMapping(value = "/uploadProduct1")
    public ResponseEntity<?> createUploadProduct1(@RequestPart("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
