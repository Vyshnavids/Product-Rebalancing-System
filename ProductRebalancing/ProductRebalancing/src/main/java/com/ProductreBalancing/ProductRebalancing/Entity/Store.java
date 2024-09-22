package com.ProductreBalancing.ProductRebalancing.Entity;

import jakarta.persistence.*;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)  // Ensures store location is unique
    private String location;

    private int capacity;
    private int currentStock;

    // Getters, Setters, Constructors


    public Store() {
    }

    public Store(Long id, String location, int capacity, int currentStock) {
        this.id = id;
        this.location = location;
        this.capacity = capacity;
        this.currentStock = currentStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }
}


