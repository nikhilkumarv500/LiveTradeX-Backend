package com.StockManagmentBackend.stock_managment_backend.controller.webSocketController;

import com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo.ListAllStocksItemPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo.StocksGeneralResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.controller.webSocketController.pojo.BroadStockUserResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.entity.StocksItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserStocksItem;
import com.StockManagmentBackend.stock_managment_backend.repo.StocksRepo;
import com.StockManagmentBackend.stock_managment_backend.repo.UserDetailsRepo;
import com.StockManagmentBackend.stock_managment_backend.repo.UserStocksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
@EnableScheduling
public class WebSocketController {

    Random random = new Random();

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    StocksRepo stocksRepo;

    @Autowired
    UserStocksRepo userStocksRepo;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void broadcastStockAndUserUpdates()
    {
        long userCount = userDetailsRepo.count();


        List<UserStocksItem> allPurchasedUserList= userStocksRepo.findAll();
        List<StocksItem> allStocksList = stocksRepo.findAll();

        Map<Long, Long> quantityOfStocksPurchasedMap = new HashMap<>();
        for(UserStocksItem item: allPurchasedUserList)
        {
            Long prevCnt = 0L;
            if(quantityOfStocksPurchasedMap.containsKey(item.getStockId())) prevCnt = quantityOfStocksPurchasedMap.get(item.getStockId());
            quantityOfStocksPurchasedMap.put(item.getStockId(), (prevCnt+item.getNoOfStocks()));
        }

        Map<Long, Set<String>> cntOfUserPurchasedPerStockMap = new HashMap<>();
        for(UserStocksItem item: allPurchasedUserList)
        {
            if(!cntOfUserPurchasedPerStockMap.containsKey(item.getStockId())) //not present
            {
                cntOfUserPurchasedPerStockMap.put(item.getStockId(), new HashSet<>(Arrays.asList(item.getEmail())));
            }
            else {
                cntOfUserPurchasedPerStockMap.get(item.getStockId()).add(item.getEmail());
            }
        }

        ArrayList<ListAllStocksItemPojo> finalList = new ArrayList<>();
        for(StocksItem item:allStocksList)
        {
            ListAllStocksItemPojo obj = new ListAllStocksItemPojo();
            obj.setId(item.getId());
            obj.setName(item.getName());
            obj.setOriginalPrice(item.getOriginalPrice());
            obj.setLowestPrice(item.getLowestPrice());
            obj.setHighestPrice(item.getHighestPrice());
            obj.setCurPrice(item.getCurPrice());
            obj.setPercentage(item.getPercentage());
            obj.setRemQuantity(item.getRemQuantity());
            obj.setPrevPercentages(item.getPrevPercentages());
            obj.setInitialQuantity(item.getInitialQuantity());

            Long purchasedQuantity = 0L;
            if(quantityOfStocksPurchasedMap.containsKey(item.getId())) purchasedQuantity=quantityOfStocksPurchasedMap.get(item.getId());
            obj.setPurchasedQuantity(purchasedQuantity);

            Long totalInvestedUserCnt = 0L;
            Set<String> valueSet = cntOfUserPurchasedPerStockMap.get(item.getId());
            if (valueSet != null)   totalInvestedUserCnt= (long) valueSet.size();
            obj.setTotalInvestedUserCnt(totalInvestedUserCnt);

            finalList.add(obj);
        }

        BroadStockUserResponsePojo finalObj = new BroadStockUserResponsePojo(userCount,finalList);

        simpMessagingTemplate.convertAndSend("/stockUpdates",finalObj);

    }


    @Scheduled(fixedRate = 20000) //20,000 milli sec = 20 sec
    public void refreshGeneralStockWithSchedule()
    {
        List<StocksItem> allStocksList = stocksRepo.findAll();

        for(StocksItem x:allStocksList)
        {
            Long newPrice = x.getOriginalPrice();

            double randomNumber = -10.0 + (20.0 * random.nextDouble());
            BigDecimal roundedNumber = BigDecimal.valueOf(randomNumber).setScale(2, RoundingMode.HALF_UP);
            Long buf = (long) ((x.getOriginalPrice()*(roundedNumber.doubleValue()))/100);
            newPrice+=buf;

            if(newPrice>x.getHighestPrice()) x.setHighestPrice(newPrice);
            if(newPrice<x.getLowestPrice()) x.setLowestPrice(newPrice);

            x.setCurPrice(newPrice);
            x.setPercentage(roundedNumber);

            List<BigDecimal> updatedPerList = x.getPrevPercentages();
            updatedPerList.remove(0);
            updatedPerList.add(roundedNumber);
            x.setPrevPercentages(updatedPerList);
        }

        try{
            stocksRepo.saveAll(allStocksList);
        }catch (Exception e)
        {
           System.out.println("Error in Websocket reload stock details" + e.getMessage());
        }

        broadcastStockAndUserUpdates();
    }


}
