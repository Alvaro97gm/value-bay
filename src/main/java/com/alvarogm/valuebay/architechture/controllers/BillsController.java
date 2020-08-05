package com.alvarogm.valuebay.architechture.controllers;

import com.alvarogm.valuebay.architechture.services.BillService;
import com.alvarogm.valuebay.domain.dto.BillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/bills")
public class BillsController {

    @Autowired
    BillService billService;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<BillDto> getBill(@PathVariable(name = "id") Integer lotId){

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<ArrayList<BillDto>> getBills(){

        return ResponseEntity.ok().build();
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
