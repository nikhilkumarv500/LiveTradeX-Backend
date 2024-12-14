package com.StockManagmentBackend.stock_managment_backend.controller.webSocketController;

import com.StockManagmentBackend.stock_managment_backend.controller.userStocksController.pojo.UserStocksGeneralResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wrapper/ws")
@CrossOrigin
public class WrapperApiForWebSocketController {

    @Autowired
    WebSocketController webSocketController;

    @GetMapping("/broadcastStockUserData")
    public ResponseEntity<?> broadcastStockUserData()
    {
        try
        {
            webSocketController.broadcastStockAndUserUpdates();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new UserStocksGeneralResponsePojo(false, "failed attempt to broadcast"),HttpStatus.OK);
        }


        return new ResponseEntity<>(new UserStocksGeneralResponsePojo(true, "attempt to broadcast"),HttpStatus.OK);

    }

}
