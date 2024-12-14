package com.StockManagmentBackend.stock_managment_backend.controller.stocksController;

import com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo.ListAllStocksItemPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.stocksController.pojo.StocksGeneralResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.entity.StocksItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserStocksItem;
import com.StockManagmentBackend.stock_managment_backend.repo.StocksRepo;
import com.StockManagmentBackend.stock_managment_backend.repo.UserStocksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RestController
@RequestMapping("/stocks")
@CrossOrigin
public class StocksController {

    Random random = new Random();

    @Autowired
    StocksRepo stocksRepo;

    @Autowired
    UserStocksRepo userStocksRepo;

    @PostMapping("/addNewStock")
    public ResponseEntity<StocksGeneralResponsePojo> addNewStock(@RequestBody StocksItem item)
    {
        try
        {
            stocksRepo.save(item);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new StocksGeneralResponsePojo(false,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity<>(new StocksGeneralResponsePojo(true,"New Stock added"), HttpStatus.OK);
    }

    @GetMapping("/listAllStocks")
    public ResponseEntity<ArrayList<ListAllStocksItemPojo>> listAllStocks()
    {


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
        return new ResponseEntity<>(finalList,HttpStatus.OK);
    }

    @DeleteMapping("/deleteStock")
    public ResponseEntity<?> deleteStock(@RequestBody StocksItem item)
    {
        if(item.getId()==null)
        {
            return new ResponseEntity<>(new StocksGeneralResponsePojo(false,"Id is empty"),HttpStatus.BAD_REQUEST);
        }

        int cnt = stocksRepo.deleteByStockId(item.getId());

        if(cnt>0)
        {
            return new ResponseEntity<>(new StocksGeneralResponsePojo(true, "Stock deleted"),HttpStatus.OK);
        }

        return new ResponseEntity<>(new StocksGeneralResponsePojo(true, "No Stock found"),HttpStatus.OK);

    }


    @GetMapping("/refreshStocks")
    public ResponseEntity<?> refreshStocks()
    {
        // update percentages
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


        // extract setPurchasedQuantity & setTotalInvestedUserCnt
        List<UserStocksItem> allPurchasedUserList= userStocksRepo.findAll();

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

        try{
            stocksRepo.saveAll(allStocksList);
        }catch (Exception e)
        {
            return new ResponseEntity<>(new StocksGeneralResponsePojo(false,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity<>( finalList,HttpStatus.OK);
    }

}
