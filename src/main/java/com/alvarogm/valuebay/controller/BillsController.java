package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.model.Bill;
import com.alvarogm.valuebay.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bills")
public class BillsController {

    @Autowired
    BillService billService;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<Bill> getBill(@PathVariable(name = "id") Integer lotId){

        return ResponseEntity.ok(billService.findById(lotId));
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<Bill>> getBills(){

        return ResponseEntity.ok(billService.findAll());
    }

    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> insertBill(@PathVariable(name = "data") Map<String, String> data){

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteBill(@PathVariable(name = "id") Integer lotId){

        return ResponseEntity.ok().build();
    }
}
