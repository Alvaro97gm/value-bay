package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.model.Bill;
import com.alvarogm.valuebay.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bills")
@Transactional
public class BillController {

    @Autowired
    BillService billService;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<Bill> getBill(@PathVariable(name = "id") Integer lotId){

        Bill result = billService.findByLotId(lotId);
        if (result == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<Bill>> getBills(){

        List<Bill> result = billService.findAll();
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> insertBill(@PathVariable(name = "data") Map<String, String> data){

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteBill(@PathVariable(name = "id") Integer lotId){

        try{
            billService.deleteByLotId(lotId);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
