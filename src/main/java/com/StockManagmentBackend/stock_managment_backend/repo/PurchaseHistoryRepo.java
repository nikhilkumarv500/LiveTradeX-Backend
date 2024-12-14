package com.StockManagmentBackend.stock_managment_backend.repo;

import com.StockManagmentBackend.stock_managment_backend.entity.PurchaseHistoryItem;
import com.StockManagmentBackend.stock_managment_backend.entity.StocksItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserAuthItem;
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
public interface PurchaseHistoryRepo extends JpaRepository<PurchaseHistoryItem, Long> {

    @Query("SELECT item FROM PurchaseHistoryItem item  WHERE item.email = ?1")
    Optional<List<PurchaseHistoryItem>> findAllByEmail(String email);


    @Modifying
    @Query("DELETE FROM PurchaseHistoryItem WHERE historyId = ?1")
    int deleteByHistoryId(Long historyId);

    @Modifying
    @Query("DELETE FROM PurchaseHistoryItem WHERE email = ?1")
    int deleteByEmail(String email);

}
