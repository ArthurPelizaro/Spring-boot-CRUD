package com.example.productapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        // Validação: Verifica se o produto já possui um ID (não deve ter um ID ao ser criado)
        if (product.getId() != null) {
            throw new IllegalArgumentException("ID do produto deve ser nulo ao criar um novo produto.");
        }
        
        // Validação: Verifica se o nome e o preço são fornecidos
        if (product.getName() == null || product.getName().isEmpty() || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Nome e preço do produto são obrigatórios e devem ser válidos.");
        }
        
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            // Validação: Verifica se o nome e o preço são fornecidos e válidos
            if (updatedProduct.getName() != null && !updatedProduct.getName().isEmpty() && updatedProduct.getPrice() > 0) {
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                return productRepository.save(product);
            } else {
                throw new IllegalArgumentException("Nome e preço do produto são obrigatórios e devem ser válidos.");
            }
        } else {
            // Tratar o caso em que o produto não existe (por exemplo, lançar uma exceção)
            throw new IllegalArgumentException("Produto não encontrado com o ID fornecido.");
        }
    }

    public void deleteProduct(Long id) {
        // Validação: Verifica se o produto com o ID fornecido existe antes de excluí-lo
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado com o ID fornecido.");
        }
        productRepository.deleteById(id);
    }
}
