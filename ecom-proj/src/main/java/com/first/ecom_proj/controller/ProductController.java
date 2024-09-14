package com.first.ecom_proj.controller;

import com.first.ecom_proj.model.Product;
import com.first.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api") //Class Url
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts()
    {
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
        Product product = service.getProductById(id);

        if(product!= null)
            return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        try {
            System.out.println(product);
            Product Product1 = service.addProduct(product, imageFile); //checking does this contain value or not
            return new ResponseEntity<>(Product1,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id)
    {
            Product prod = service.getProductById(id);

            byte[] imageFile = prod.getImagedate();

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(prod.getImageType()))
                    .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,
                                                @RequestPart(required = false) MultipartFile image) {

        Product prod;
        try {
            prod = service.updateProduct(id, product, image);
            System.out.println(prod);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to Update1.", HttpStatus.BAD_REQUEST);
        }

        if (prod != null) {
            System.out.println("111");
            return new ResponseEntity<>("Updated Successfully.", HttpStatus.OK);
        } else {
            System.out.println("2222");
            return new ResponseEntity<>("Failed to Update bcz null", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        Product prod = null;
        prod = service.getProductById(id);

        if(prod != null)
        {
            service.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword)
    {
        List<Product> prod = service.searchProduct(keyword);

        return new ResponseEntity<>(prod,HttpStatus.OK);
    }
}
