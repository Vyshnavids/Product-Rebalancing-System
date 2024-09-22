package com.ProductreBalancing.ProductRebalancing.Service;

import com.ProductreBalancing.ProductRebalancing.DTO.ProductDTO;
import com.ProductreBalancing.ProductRebalancing.DTO.StoreDTO;
import com.ProductreBalancing.ProductRebalancing.Entity.Product;
import com.ProductreBalancing.ProductRebalancing.Entity.Store;
import com.ProductreBalancing.ProductRebalancing.Repository.ProductRepository;
import com.ProductreBalancing.ProductRebalancing.Repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductRebalancingService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductRebalancingService.class);

    // Rebalance products across all stores based on capacity
    // Rebalance products across all stores based on capacity
    public void rebalanceProducts() {
        List<Store> stores = storeRepository.findAll();
        List<Product> products = productRepository.findAll();

        int totalCapacity = stores.stream().mapToInt(Store::getCapacity).sum();

        for (Product product : products) {
            int productQuantity = product.getQuantity();

            // Distribute product quantities based on store capacities
            for (Store store : stores) {
                int storeCapacity = store.getCapacity();
                int currentStock = store.getCurrentStock();

                // Calculate the quantity to assign to this store
                int assignedQuantity = (storeCapacity * productQuantity) / totalCapacity;
                int newStock = currentStock + assignedQuantity;

                // Ensure the new stock does not exceed store capacity
                if (newStock > storeCapacity) {
                    assignedQuantity = storeCapacity - currentStock; // Adjust the quantity to fit within capacity
                    newStock = storeCapacity; // Update newStock to the store's capacity
                }

                store.setCurrentStock(newStock);
                storeRepository.save(store);
            }
        }
    }


    // Add a new store
//    public Store addStore(StoreDTO storeDTO) {
//        Store store = new Store();
//        store.setLocation(storeDTO.getLocation());
//        store.setCapacity(storeDTO.getCapacity());
//        store.setCurrentStock(0); // Initialize with zero stock
//        return storeRepository.save(store);
//    }

    // Remove a store by ID
    public void removeStore(Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new RuntimeException("Store with ID " + storeId + " not found.");
        }
        storeRepository.deleteById(storeId);
    }

    // Get all stores
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // Add a new product
//    public Product addProduct(ProductDTO productDTO) {
//        Product product = new Product();
//        product.setName(productDTO.getName());
//        product.setQuantity(productDTO.getQuantity());
//        return productRepository.save(product);
//    }

    // Update a product
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found."));
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        return productRepository.save(product);
    }

    // Remove a product by ID
    public void removeProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }
        productRepository.deleteById(productId);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Add stock to a store
//    public Store addStockToStore(Long storeId, int quantity) {
//        logger.debug("Adding stock to store ID: {}, Quantity: {}", storeId, quantity);
//
//        Store store = storeRepository.findById(storeId)
//                .orElseThrow(() -> new RuntimeException("Store with ID " + storeId + " not found."));
//
//        if (store.getCurrentStock() + quantity > store.getCapacity()) {
//            logger.error("Exceeds capacity. Current stock: {}, Quantity: {}, Capacity: {}",
//                    store.getCurrentStock(), quantity, store.getCapacity());
//            throw new RuntimeException("Not enough capacity to add stock.");
//        }
//
//        store.setCurrentStock(store.getCurrentStock() + quantity);
//        return storeRepository.save(store);
//    }

    // Remove stock from a store
//    public Store removeStockFromStore(Long storeId, int quantity) {
//        Store store = storeRepository.findById(storeId)
//                .orElseThrow(() -> new RuntimeException("Store with ID " + storeId + " not found."));
//
//        if (store.getCurrentStock() < quantity) {
//            throw new RuntimeException("Not enough stock to remove.");
//        }
//
//        store.setCurrentStock(store.getCurrentStock() - quantity);
//        return storeRepository.save(store);
//    }
    public Store addStore(StoreDTO storeDTO) {
        if (storeDTO.getCapacity() < 0) {
            throw new IllegalArgumentException("Store capacity cannot be less than 0");
        }
        Store store = new Store();
        store.setLocation(storeDTO.getLocation());
        store.setCapacity(storeDTO.getCapacity());
        store.setCurrentStock(0); // New store starts with zero stock
        return storeRepository.save(store);
    }

    public Product addProduct(ProductDTO productDTO) {
        if (productDTO.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be less than 0");
        }
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        return productRepository.save(product);
    }

    public Store addStockToStore(Long storeId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store with ID " + storeId + " not found"));

        if (store.getCurrentStock() + quantity > store.getCapacity()) {
            throw new RuntimeException("Not enough capacity to add this stock");
        }

        store.setCurrentStock(store.getCurrentStock() + quantity);
        return storeRepository.save(store);
    }

    public Store removeStockFromStore(Long storeId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store with ID " + storeId + " not found"));

        if (store.getCurrentStock() < quantity) {
            throw new RuntimeException("Not enough stock to remove");
        }

        store.setCurrentStock(store.getCurrentStock() - quantity);
        return storeRepository.save(store);
    }
    public  Product getProductById(Long productId) {
        // Use findById() with Optional and throw an exception if not found
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));
    }

}
