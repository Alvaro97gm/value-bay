package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.model.Coin;
import com.alvarogm.valuebay.service.CoinService;
import com.alvarogm.valuebay.domain.dto.CoinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coins")
public class CoinsController {

    @Autowired
    CoinService coinService;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<Coin> getCoin(@PathVariable(name = "id") Integer lotId){

        return ResponseEntity.ok(coinService.findById(lotId));
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<Coin>> getCoins(){

        return ResponseEntity.ok(coinService.findAll());
    }

    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> insertCoin(@PathVariable(name = "data") Map<String, String> data){

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteCoin(@PathVariable(name = "id") Integer lotId){

        return ResponseEntity.ok().build();
    }
}
