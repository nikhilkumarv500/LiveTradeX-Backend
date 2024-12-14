package com.StockManagmentBackend.stock_managment_backend.repo;
import com.StockManagmentBackend.stock_managment_backend.entity.UserDetailsItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserStocksItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserStocksRepo extends JpaRepository<UserStocksItem, String>{

    @Modifying
    @Query("DELETE FROM UserStocksItem WHERE email = ?1")
    int deleteByEmail(String email);

    @Query("SELECT item FROM UserStocksItem item  WHERE item.email = ?1")
    Optional<List<UserStocksItem>> findAllByEmail(String email);

    @Query("SELECT item FROM UserStocksItem item  WHERE item.purchaseId = ?1 and item.email = ?2")
    Optional<UserStocksItem> findByEmailAndPurchaseId(Long purchaseId, String email);

    @Modifying
    @Query("DELETE FROM UserStocksItem WHERE email = ?1 and purchaseId = ?2")
    int deleteByEmailAndPurchaseId(String email, Long purchaseId);



}
