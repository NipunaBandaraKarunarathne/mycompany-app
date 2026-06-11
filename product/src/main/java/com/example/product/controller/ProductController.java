package com.example.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.dto.ProductDTO;
import com.example.product.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getproducts")
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("addproduct")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {

        return productService.saveProduct(productDTO);
    }

      @GetMapping("/product/{productId}")
    public ProductDTO getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

       @PutMapping("/updateproduct")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/deleteproduct/{productId}")
    public String deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProduct(productId);
    }

}
