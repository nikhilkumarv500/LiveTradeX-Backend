package com.StockManagmentBackend.stock_managment_backend.repo;

import com.StockManagmentBackend.stock_managment_backend.entity.UserAuthItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface UserAuthRepo extends JpaRepository<UserAuthItem, String> {

    @Modifying
    @Query("DELETE FROM UserAuthItem WHERE email = ?1")
    int deleteByEmail(String email);


    @Query("SELECT item FROM UserAuthItem item  WHERE item.email = ?1")
    Optional<UserAuthItem> findByEmail(String email);

}
