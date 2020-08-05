package com.alvarogm.valuebay.architechture.controllers;

import com.alvarogm.valuebay.architechture.services.AuctionService;
import com.alvarogm.valuebay.domain.dto.AuctionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/auctions")
public class AuctionsController {

    @Autowired
    AuctionService auctionService;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<AuctionDto> getAuction(@PathVariable(name = "id") Integer auctionId){

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<ArrayList<AuctionDto>> getAuctions(){

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> createAuction(@PathVariable(name = "data") Map<String, String> data){

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteAuction(@PathVariable(name = "id") Integer auctionId){

        return ResponseEntity.ok().build();
    }
}
