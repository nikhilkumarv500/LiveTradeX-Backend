package com.StockManagmentBackend.stock_managment_backend.repo;
import com.StockManagmentBackend.stock_managment_backend.entity.StocksItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserAuthItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserDetailsItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Transactional
@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetailsItem, String>{

    @Modifying
    @Query("DELETE FROM UserDetailsItem WHERE email = ?1")
    int deleteByEmail(String email);

    @Query("SELECT item FROM UserDetailsItem item  WHERE item.email = ?1")
    Optional<UserDetailsItem> findByEmail(String email);
}
