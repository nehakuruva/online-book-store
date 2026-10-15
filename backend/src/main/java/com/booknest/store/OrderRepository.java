package com.booknest.store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderRepository extends JpaRepository<StoreOrder,Long> { List<StoreOrder> findByUserIdOrderByCreatedAtDesc(Long userId); }
