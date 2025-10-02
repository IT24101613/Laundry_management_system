package org.example.pickupanddelivery.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contact;

    @OneToMany(mappedBy = "pickupDriver")
    private List<Order> pickupOrders;

    @OneToMany(mappedBy = "deliveryDriver")
    private List<Order> deliveryOrders;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public List<Order> getPickupOrders() { return pickupOrders; }
    public void setPickupOrders(List<Order> pickupOrders) { this.pickupOrders = pickupOrders; }
    public List<Order> getDeliveryOrders() { return deliveryOrders; }
    public void setDeliveryOrders(List<Order> deliveryOrders) { this.deliveryOrders = deliveryOrders; }
}