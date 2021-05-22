package com.app.shopifyuser.model;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class DeliveryOrder implements Serializable {

    private String id;
    private String byUser;
    private String toUser;
    private long scheduledTime;
    private long orderedAt;
    private List<Map<String, Integer>> orders;
    private GeoPoint location;
    private double totalPrice;


    public DeliveryOrder() {
    }

    public DeliveryOrder(String id, String toUser, String byUser, long scheduledTime, long orderedAt, List<Map<String, Integer>> orders, GeoPoint location, double totalPrice) {
        this.id = id;
        this.toUser = toUser;
        this.byUser = byUser;
        this.scheduledTime = scheduledTime;
        this.orderedAt = orderedAt;
        this.orders = orders;
        this.location = location;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public List<Map<String, Integer>> getOrders() {
        return orders;
    }

    public void setOrders(List<Map<String, Integer>> orders) {
        this.orders = orders;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(long orderedAt) {
        this.orderedAt = orderedAt;
    }

    public String getByUser() {
        return byUser;
    }

    public void setByUser(String byUser) {
        this.byUser = byUser;
    }
}
