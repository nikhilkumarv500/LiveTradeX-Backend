package com.StockManagmentBackend.stock_managment_backend.controller.userAuthController;

import com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo.StocksGeneralResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo.UserAuthAddMoneyPayloadPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo.UserAuthListAllPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo.UserAuthLoginPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo.UserAuthRegisterPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo.UserStocksGeneralResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.entity.*;
import com.StockManagmentBackend.stock_managment_backend.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    UserAuthRepo userAuthRepo;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    UserStocksRepo userStocksRepo;

    @Autowired
    PurchaseHistoryRepo purchaseHistoryRepo;

    @Autowired
    StocksRepo stocksRepo;



    @PostMapping("/registerUser")
    public ResponseEntity<UserAuthRegisterPojo>  registerUser(@RequestBody UserAuthItem item)
    {
        if(item.getEmail()==null || item.getPassword()==null || item.getName()==null)
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Fields cannot be empty. "), HttpStatus.OK);
        }
        if (!item.getEmail().contains("@")) {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Email must contain '@'. "), HttpStatus.OK);
        }
        if (item.getPassword().length() < 4) {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Password must be at least 4 characters long."), HttpStatus.OK);
        }

        UserAuthItem chkForExistingUser= userAuthRepo.findByEmail(item.getEmail()).orElse(null);
        if(chkForExistingUser!=null)
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"User with same email, already exists"), HttpStatus.OK);
        }

        userAuthRepo.save(item);
        userDetailsRepo.save(new UserDetailsItem(item.getEmail(),item.getName(),0L,0L,0L));

        return new ResponseEntity<>(new UserAuthRegisterPojo(true,"User Created Successfully"),HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<UserAuthRegisterPojo> deleteuser(@RequestBody UserAuthItem item)
    {
        if(item.getEmail()==null || item.getPassword()==null)
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"wrong payload"),HttpStatus.OK);
        }

        //user Auth password check
        UserAuthItem cur = userAuthRepo.findByEmail(item.getEmail()).orElse(null);
        if(cur==null){
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"User not found"), HttpStatus.OK);
        }
        if(!cur.getPassword().equals(item.getPassword()))
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Wrong Password, Try Again"), HttpStatus.OK);
        }
        //----------------------

        //retrieve quantity the user has purchased into general stocks / for every stocks
        List<StocksItem> allStocksList = stocksRepo.findAll();
        List<UserStocksItem> curUserStocksItemList = userStocksRepo.findAllByEmail(item.getEmail()).orElse(null);

        Map<Long, Long> quantityOfStocksPurchasedMap = new HashMap<>();

        if(curUserStocksItemList!=null) {
            for (UserStocksItem x : curUserStocksItemList) {
                Long prevCnt = 0L;
                if (quantityOfStocksPurchasedMap.containsKey(x.getStockId()))
                    prevCnt = quantityOfStocksPurchasedMap.get(x.getStockId());
                quantityOfStocksPurchasedMap.put(x.getStockId(), (prevCnt + x.getNoOfStocks()));
            }
        }

        for(StocksItem x:allStocksList)
        {
            if(quantityOfStocksPurchasedMap.containsKey(x.getId()))
            {
                x.setRemQuantity(x.getRemQuantity() + (quantityOfStocksPurchasedMap.get(x.getId())) );
            }
        }

        try{
            stocksRepo.saveAll(allStocksList);
        }catch (Exception e)
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,e.getMessage()),HttpStatus.OK);
        }

        // apply delete operation
        int cnt=0;
        try{
            cnt = userAuthRepo.deleteByEmail(item.getEmail());
            cnt+= userDetailsRepo.deleteByEmail(item.getEmail());
            cnt+= userStocksRepo.deleteByEmail(item.getEmail());
            cnt+= purchaseHistoryRepo.deleteByEmail(item.getEmail());
        }catch (Exception e)
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,e.getMessage()),HttpStatus.OK);
        }

        if(cnt==0){
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"No User found"),HttpStatus.OK);
        }

        return new ResponseEntity<>(new UserAuthRegisterPojo(true,"User deleted successfully"),HttpStatus.OK);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@RequestBody UserAuthItem item)
    {
        if(item.getEmail()==null || item.getPassword()==null)
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Email and password cannot be empty. "), HttpStatus.OK);
        }
        if (!item.getEmail().contains("@")) {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Email must contain '@'. "), HttpStatus.OK);
        }
        if (item.getPassword().length() < 4) {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Password must be at least 4 characters long."), HttpStatus.OK);
        }

        UserAuthItem cur = userAuthRepo.findByEmail(item.getEmail()).orElse(null);

        if(cur==null){
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"User not found"), HttpStatus.OK);
        }

        if(!cur.getPassword().equals(item.getPassword()))
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Wrong Password, Try Again"), HttpStatus.OK);
        }

        UserAuthRegisterPojo curUserAuthRegisterPojo = new UserAuthRegisterPojo(true,"Login Successful");
        UserDetailsItem curUserDetailsItem = userDetailsRepo.findByEmail(item.getEmail()).orElse(null);
        List<UserStocksItem> curUserStocksItemList = userStocksRepo.findAllByEmail(item.getEmail()).orElse(null);
        List<PurchaseHistoryItem> curUserStocksHistoryList = purchaseHistoryRepo.findAllByEmail(item.getEmail()).orElse(new ArrayList<>());
        long userCount = userDetailsRepo.count();

        UserAuthLoginPojo curResult = new UserAuthLoginPojo(curUserAuthRegisterPojo,curUserDetailsItem,curUserStocksItemList,curUserStocksHistoryList,userCount);

        return new ResponseEntity<>(curResult, HttpStatus.OK);

    }

    @GetMapping("/listAllUserDetails")
    public ResponseEntity<?> listUserDetails()
    {
        UserAuthRegisterPojo curUserAuthRegisterPojo = new UserAuthRegisterPojo(true,"All Users List ");
        List<UserAuthItem> userAuthItemList = userAuthRepo.findAll();
        List<UserDetailsItem> userDetailsItemList = userDetailsRepo.findAll();

        return new ResponseEntity<>(new UserAuthListAllPojo(curUserAuthRegisterPojo,userAuthItemList,userDetailsItemList) , HttpStatus.OK);
    }

    @PostMapping("/addMoney")
    public ResponseEntity<?> addMoney(@RequestBody UserAuthAddMoneyPayloadPojo item)
    {
        if(item.getPassword()==null || item.getEmail()==null || item.getCouponCode()==null || item.getAmount()==null)
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Missing fields"), HttpStatus.OK);

        //user Auth password check
        UserAuthItem cur = userAuthRepo.findByEmail(item.getEmail()).orElse(null);
        if(cur==null){
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"User not found"), HttpStatus.OK);
        }
        if(!cur.getPassword().equals(item.getPassword()))
        {
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Wrong Password, Try Again"), HttpStatus.OK);
        }
        //----------------------

        if(item.getAmount()!=500 && item.getAmount()!=1000 && item.getAmount()!=5000)
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Invalid Amount Entered"),HttpStatus.OK);

        if(!item.getCouponCode().equals("freemoney"))
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Invalid Coupon Code"),HttpStatus.OK);

        UserDetailsItem userDetailsItem= userDetailsRepo.findByEmail(item.getEmail()).orElse(null);
        if(userDetailsItem==null)
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"User doesnt exists"),HttpStatus.OK);

        userDetailsItem.setAccountBalance(userDetailsItem.getAccountBalance()+ Math.max(0,item.getAmount()));
        userDetailsRepo.save(userDetailsItem);

        return new ResponseEntity<>(new UserAuthRegisterPojo(true,"Transaction Successful"),HttpStatus.OK);
    }

    @PostMapping("/specificUserDetails")
    public ResponseEntity<?> specificUserDetails (@RequestBody UserAuthAddMoneyPayloadPojo item)
    {
        if(item.getEmail()==null || item.getPassword()==null)
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"Incorrect Api Payload"),HttpStatus.OK);

        //user Auth password check
        UserAuthItem cur = userAuthRepo.findByEmail(item.getEmail()).orElse(null);
        if(cur==null){
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false,"User not found"), HttpStatus.OK);
        }
        if(!cur.getPassword().equals(item.getPassword()))
        {
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false,"Wrong Password, Try Again"), HttpStatus.OK);
        }
        //----------------------

        UserDetailsItem userDetailsItem= userDetailsRepo.findByEmail(item.getEmail()).orElse(null);
        if(userDetailsItem==null)
            return new ResponseEntity<>(new UserAuthRegisterPojo(false,"User doesnt exists"),HttpStatus.OK);

        return new ResponseEntity<>(userDetailsItem,HttpStatus.OK);
    }

}
