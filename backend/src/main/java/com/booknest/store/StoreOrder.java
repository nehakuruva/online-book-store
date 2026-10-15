package com.booknest.store;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="store_orders")
public class StoreOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public Long userId; public String itemsJson; public double total;
 public LocalDateTime createdAt=LocalDateTime.now();
 public String status="Placed";
 public StoreOrder() {}
}
