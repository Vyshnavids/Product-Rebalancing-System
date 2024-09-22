package com.ProductreBalancing.ProductRebalancing.Controller;

import com.ProductreBalancing.ProductRebalancing.DTO.ProductDTO;
import com.ProductreBalancing.ProductRebalancing.DTO.StoreDTO;
import com.ProductreBalancing.ProductRebalancing.Entity.Product;
import com.ProductreBalancing.ProductRebalancing.Entity.Store;
import com.ProductreBalancing.ProductRebalancing.Service.ProductRebalancingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")  // Allow your frontend origin
public class Controller {

    @Autowired
    private ProductRebalancingService rebalancingService;

    @PostMapping("/rebalance")
    public ResponseEntity<String> rebalance() {
        rebalancingService.rebalanceProducts();
        return ResponseEntity.ok("Products rebalanced across stores successfully.");
    }

    @PostMapping("/stores")
    public ResponseEntity<?> addStore(@RequestBody StoreDTO storeDTO) {
        try {
            Store store = rebalancingService.addStore(storeDTO);
            return ResponseEntity.ok(store);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<String> removeStore(@PathVariable Long storeId) {
        rebalancingService.removeStore(storeId);
        return ResponseEntity.ok("Store with ID " + storeId + " removed successfully.");
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = rebalancingService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = rebalancingService.addProduct(productDTO);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        Product product = rebalancingService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long productId) {
        rebalancingService.removeProduct(productId);
        return ResponseEntity.ok("Product with ID " + productId+" removed successfully.");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = rebalancingService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        try {
            // Use Optional to avoid NullPointerException
            Product product = rebalancingService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            // If the product is not found, return a 404 error with a meaningful message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/stores/{storeId}/addStock")
    public ResponseEntity<?> addStock(@PathVariable Long storeId, @RequestParam int quantity) {
        try {
            Store store = rebalancingService.addStockToStore(storeId, quantity);
            return ResponseEntity.ok(store);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/stores/{storeId}/removeStock")
    public ResponseEntity<?> removeStock(@PathVariable Long storeId, @RequestParam int quantity) {
        try {
            Store store = rebalancingService.removeStockFromStore(storeId, quantity);
            return ResponseEntity.ok(store);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
