package com.StockManagmentBackend.stock_managment_backend.repo;
import com.StockManagmentBackend.stock_managment_backend.entity.StocksItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserAuthItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Transactional
@Repository
public interface StocksRepo extends JpaRepository<StocksItem, Long>{

    @Modifying
    @Query("DELETE FROM StocksItem WHERE id = ?1")
    int deleteByStockId(Long id);

}
