package com.StockManagmentBackend.stock_managment_backend.controller.historyController;

import com.StockManagmentBackend.stock_managment_backend.controller.historyController.pojo.PruchaseHistoryGeneralResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.controller.historyController.pojo.PurchaseHistoryApiPayloadPojo;
import com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo.UserStocksGeneralResponsePojo;
import com.StockManagmentBackend.stock_managment_backend.entity.PurchaseHistoryItem;
import com.StockManagmentBackend.stock_managment_backend.entity.UserAuthItem;
import com.StockManagmentBackend.stock_managment_backend.repo.PurchaseHistoryRepo;
import com.StockManagmentBackend.stock_managment_backend.repo.UserAuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    PurchaseHistoryRepo purchaseHistoryRepo;

    @Autowired
    UserAuthRepo userAuthRepo;

    @GetMapping("/listAllHistory")
    public ResponseEntity<List<PurchaseHistoryItem>> listAllHistory()
    {
        return new ResponseEntity<>(purchaseHistoryRepo.findAll(), HttpStatus.OK);
    }

    @PostMapping("/listAllHistoryByEmail")
    public ResponseEntity<?> listAllHistoryByEmail(@RequestBody PurchaseHistoryApiPayloadPojo item)
    {
        if(item.getEmail()==null)
            return new ResponseEntity<>(new PruchaseHistoryGeneralResponsePojo(false, "Email was not sent in palyload"), HttpStatus.OK);

        List<PurchaseHistoryItem> list = purchaseHistoryRepo.findAllByEmail(item.getEmail()).orElse(new ArrayList<>());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/deleteByHistoryId")
    public ResponseEntity<?> deleteByHistoryId(@RequestBody PurchaseHistoryApiPayloadPojo item)
    {

        if(item.getHistoryId()==null || item.getEmail()==null || item.getPassword()==null)
            return new ResponseEntity<>(new PruchaseHistoryGeneralResponsePojo(false, "Incorrect Payload"), HttpStatus.OK);

        //user Auth password check
        UserAuthItem cur = userAuthRepo.findByEmail(item.getEmail()).orElse(null);
        if(cur==null){
            return new ResponseEntity<>(new PruchaseHistoryGeneralResponsePojo(false,"User not found"), HttpStatus.OK);
        }
        if(!cur.getPassword().equals(item.getPassword()))
        {
            return new ResponseEntity<>(new PruchaseHistoryGeneralResponsePojo(false,"Wrong Password, Try Again"), HttpStatus.OK);
        }
        //----------------------

        List<PurchaseHistoryItem> list = purchaseHistoryRepo.findAllByEmail(item.getEmail()).orElse(new ArrayList<>());

        boolean f=true;
        for(PurchaseHistoryItem x:list)
        {
            if(x.getHistoryId().equals(item.getHistoryId()))
            {
                f=false;
            }
        }

        if(f) return new ResponseEntity<>(new PruchaseHistoryGeneralResponsePojo(false, "Could not find the history entry"), HttpStatus.OK);

        int cnt = purchaseHistoryRepo.deleteByHistoryId(item.getHistoryId());

        if(cnt>0)
            return new ResponseEntity<>(new PruchaseHistoryGeneralResponsePojo(true, "Delete successful"),HttpStatus.OK);

        return new ResponseEntity<>(new PruchaseHistoryGeneralResponsePojo(false, "Could not find the history entry"), HttpStatus.OK);

    }

}
