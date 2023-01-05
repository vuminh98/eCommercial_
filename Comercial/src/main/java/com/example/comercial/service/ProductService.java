package com.example.comercial.service;

import com.example.comercial.model.product.Product;
import com.example.comercial.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Iterable<Product> findByName(String name) {
        return null;
    }

    @Override
    public Page<Product> findAllPage(Pageable pageable) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean remove(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
