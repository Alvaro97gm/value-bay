package com.alvarogm.valuebay.architechture.controllers;

import com.alvarogm.valuebay.architechture.services.CoinService;
import com.alvarogm.valuebay.domain.dto.CoinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/coins")
public class CoinsController {

    @Autowired
    CoinService coinService;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<CoinDto> getCoin(@PathVariable(name = "id") Integer lotId){

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<ArrayList<CoinDto>> getCoins(){

        return ResponseEntity.ok().build();
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
