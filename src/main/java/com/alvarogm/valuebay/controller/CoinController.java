package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.model.Coin;
import com.alvarogm.valuebay.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coins")
@Transactional
public class CoinController {

    @Autowired
    CoinService coinService;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<Coin> getCoin(@PathVariable(name = "id") Integer lotId){

        Coin result = coinService.findByLotId(lotId);
        if (result == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<Coin>> getCoins(){

        List<Coin> result = coinService.findAll();
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> insertCoin(@PathVariable(name = "data") Map<String, String> data){

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteCoin(@PathVariable(name = "id") Integer lotId){

        try{
            coinService.deleteByLotId(lotId);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
