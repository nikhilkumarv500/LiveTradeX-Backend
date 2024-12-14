package com.StockManagmentBackend.stock_managment_backend.controller.userStocksController;

import com.StockManagmentBackend.stock_managment_backend.controller.userAuthController.Pojo.UserAuthRegisterPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo.UserStockAfterSellPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo.UserStocksApiPayloadPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo.UserStocksGeneralResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.controller.webSocketController.WebSocketController;
import com.StockManagmentBackend.stock_managment_backend.entity.*;
import com.StockManagmentBackend.stock_managment_backend.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userStocks")
@CrossOrigin
public class UserStocksController {

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    StocksRepo stocksRepo;

    @Autowired
    UserStocksRepo userStocksRepo;

    @Autowired
    PurchaseHistoryRepo purchaseHistoryRepo;

    @Autowired
    UserAuthRepo userAuthRepo;

    @Autowired
    WebSocketController webSocketController;

    @PostMapping("/buyStock")
    public ResponseEntity<?> buyStock(@RequestBody UserStocksApiPayloadPojo item)
    {
        if(item.getEmail() == null || item.getStockId() == null || item.getNoOfStocks() == null || item.getPassword()==null)
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Missing Payload Data"), HttpStatus.OK);

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

        StocksItem generalStock = stocksRepo.findById(item.getStockId()).orElse(null);
        if(generalStock==null) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Invalid Stock Selection"),HttpStatus.OK);

        UserDetailsItem userDetailsItem = userDetailsRepo.findByEmail(item.getEmail()).orElse(null);
        if(userDetailsItem==null) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Unauthorized User :)"),HttpStatus.OK);



        if(item.getNoOfStocks() > generalStock.getRemQuantity()) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Invalid quantity to buy"),HttpStatus.OK);
        if(item.getNoOfStocks() == 0L) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Select more then zero stocks to buy"),HttpStatus.OK);

        Long cost = (item.getNoOfStocks()*generalStock.getCurPrice());
        if(cost > userDetailsItem.getAccountBalance()) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Insufficient Bank Balance"),HttpStatus.OK);

        //updated user accountbalance
        userDetailsItem.setAccountBalance( userDetailsItem.getAccountBalance()-cost );
        userDetailsItem.setInvestedAmount( userDetailsItem.getInvestedAmount()+cost );
        userDetailsRepo.save(userDetailsItem);

        //updated general stocks list remaning quantity
        generalStock.setRemQuantity(generalStock.getRemQuantity()- item.getNoOfStocks());
        stocksRepo.save(generalStock);

        //updated table having purchased stock
        UserStocksItem newPurchaseEntry = new UserStocksItem(null, item.getEmail(), item.getStockId(), generalStock.getName(),
                (long) item.getNoOfStocks(), generalStock.getCurPrice(), generalStock.getPercentage());
        userStocksRepo.save(newPurchaseEntry);

        webSocketController.broadcastStockAndUserUpdates();

        return new ResponseEntity<>(new UserStocksGeneralResponsePojo(true,"Stock purchased successfully"), HttpStatus.OK);
    }

    @PostMapping("/sellStock")
    public ResponseEntity<?> sellStock(@RequestBody UserStocksApiPayloadPojo item)
    {
        if(item.getPurchaseId()==null || item.getEmail()==null || item.getStockId()==null || item.getNoOfStocks()==null || item.getPassword()==null)
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Missing Payload Data"),HttpStatus.OK);

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

        StocksItem generalStock = stocksRepo.findById(item.getStockId()).orElse(null);
        if(generalStock==null) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Invalid Option"),HttpStatus.OK);

        UserDetailsItem userDetailsItem = userDetailsRepo.findByEmail(item.getEmail()).orElse(null);
        if(userDetailsItem==null) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Unauthorized User :)"),HttpStatus.OK);

        UserStocksItem userSellOption = userStocksRepo.findByEmailAndPurchaseId(item.getPurchaseId(),item.getEmail()).orElse(null);
        if(userSellOption==null) return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "No Stocks found"),HttpStatus.OK);

        if(item.getNoOfStocks() > userSellOption.getNoOfStocks())
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Invalid Stock Quantity to sell"),HttpStatus.OK);

        if(item.getNoOfStocks() == 0L)
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Select more then 0 stocks to sell"),HttpStatus.OK);


        //  updated no of stocks sold in stocks-purchased-table
        userSellOption.setNoOfStocks(userSellOption.getNoOfStocks()- item.getNoOfStocks());
        if(userSellOption.getNoOfStocks()>0) userStocksRepo.save(userSellOption);
        else userStocksRepo.deleteByEmailAndPurchaseId(item.getEmail(), item.getPurchaseId());

        //  update user earned money and profit and invested amount
        Long cost = generalStock.getCurPrice()* item.getNoOfStocks();
        cost = Math.max(0,cost);

        userDetailsItem.setAccountBalance(userDetailsItem.getAccountBalance()+cost);
        Long profitMoney = (generalStock.getCurPrice()* item.getNoOfStocks()) - (userSellOption.getPurchasedPrice()* item.getNoOfStocks()) ;
        profitMoney=Math.max(0,profitMoney);
        userDetailsItem.setProfitMade(userDetailsItem.getProfitMade() + profitMoney);

        cost= userSellOption.getPurchasedPrice() * item.getNoOfStocks() ;
        cost = userDetailsItem.getInvestedAmount() - cost;
        cost = Math.max(cost,0L);
        userDetailsItem.setInvestedAmount(cost);


        userDetailsRepo.save(userDetailsItem);

        //updated no of stock got back in general stock table
        generalStock.setRemQuantity(generalStock.getRemQuantity()+item.getNoOfStocks());
        stocksRepo.save(generalStock);

        //updated HistoryTable
        PurchaseHistoryItem newHistory = new PurchaseHistoryItem(null, userSellOption.getPurchaseId(), userSellOption.getEmail(),
                userSellOption.getStockId(), userSellOption.getStockName(), item.getNoOfStocks(), userSellOption.getPurchasedPrice(),
                userSellOption.getPurchasedPercentage(), generalStock.getCurPrice(), generalStock.getPercentage());
        purchaseHistoryRepo.save(newHistory);

        //updated api response
        UserStockAfterSellPojo apiResponse = new UserStockAfterSellPojo(
                userDetailsItem, userStocksRepo.findAllByEmail(item.getEmail()).orElse(new ArrayList<>() )  );

        webSocketController.broadcastStockAndUserUpdates();

        return new ResponseEntity<> (apiResponse,HttpStatus.OK);
    }

    @GetMapping("/listAllPurchasedStocks")
    public ResponseEntity<List<UserStocksItem>> listAllPurchasedStocks()
    {
        return new ResponseEntity<>(userStocksRepo.findAll(),HttpStatus.OK);
    }

    @PostMapping("/listAllPurchasedStocksByEmail")
    public ResponseEntity<?> listAllPurchasedStocksByEmail(@RequestBody UserStocksApiPayloadPojo item)
    {
        if(item.getEmail()==null)
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "Email not present in payload"),HttpStatus.OK);

        List<UserStocksItem> list = userStocksRepo.findAllByEmail(item.getEmail()).orElse(new ArrayList<>());
        return new ResponseEntity<>(list ,HttpStatus.OK);
    }



}
