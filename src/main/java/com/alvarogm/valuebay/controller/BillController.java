package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.model.Bill;
import com.alvarogm.valuebay.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ResponseStatus> insertBill(
            @RequestParam(value = "lotId") Integer lotId,
            @RequestParam(value = "itemValue") Integer itemValue,
            @RequestParam(value = "emissionYear") Integer emissionYear,
            @RequestParam(value = "emissionMonth") Integer emissionMonth,
            @RequestParam(value = "emissionDay") Integer emissionDay,
            @RequestParam(value = "conservationStatus") String conservationStatus,
            @RequestParam(value = "price") float price,
            @RequestParam(value = "fkAuction", required = false) Integer fkAuction
    ){

        try{
            billService.insert(
                billService.createBillDTO(lotId, itemValue, emissionYear, emissionMonth, emissionDay, conservationStatus, price),
                fkAuction
            );
            System.out.println("[BILLS] - Lote: " + lotId + " insertado en el sistema." );
            return ResponseEntity.ok().build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PostMapping(value = "/mod/{lotId}", produces = "application/json")
    public ResponseEntity<ResponseStatus> modifyCoin(
            @PathVariable(name = "lotId") Integer lotId,
            @RequestParam(value = "itemValue", required = false) Integer itemValue,
            @RequestParam(value = "emissionYear", required = false) Integer emissionYear,
            @RequestParam(value = "emissionYear", required = false) Integer emissionMonth,
            @RequestParam(value = "emissionYear", required = false) Integer emissionDay,
            @RequestParam(value = "conservationStatus", required = false) String conservationStatus,
            @RequestParam(value = "price", required = false) Float price,
            @RequestParam(value = "fkAuction", required = false) Integer fkAuction
    ){
        try{
            billService.modifyBill(lotId, itemValue, emissionYear, emissionMonth, emissionDay, conservationStatus, price, fkAuction);
            System.out.println("[BILLS] - Lote: " + lotId + " modificado.");
            return ResponseEntity.ok().build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteBill(@PathVariable(name = "id") Integer lotId){

        try{
            billService.deleteByLotId(lotId);
            System.out.println("[BILLS] - Lote: " + lotId + " eliminado del sistema." );
            return ResponseEntity.ok().build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
