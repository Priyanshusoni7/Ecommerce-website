package com.first.ecom_proj.service;

import com.first.ecom_proj.model.Product;
import com.first.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getProducts()
    {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImagedata(imageFile.getBytes());

        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile image) throws IOException {

        Product existingProduct = repo.findById(id).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        product.setImagedata(image.getBytes());
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());

        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }


    public List<Product> searchProduct(String keyword) {

        return repo.searchProduct(keyword);
    }
}
