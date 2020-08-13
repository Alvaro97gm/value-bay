package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.dto.BillDTO;
import com.alvarogm.valuebay.domain.mapper.BillMapper;
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

    @Autowired
    BillMapper billMapper;


    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<BillDTO> getBill(@PathVariable(name = "id") Integer lotId){

        BillDTO result = billMapper.billToBillDTO(billService.findByLotId(lotId));
        if (result == null) {
            System.out.println("[COINS] - No se ha encontrado el lote " + lotId + ".");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<BillDTO>> getBills(){

        List<BillDTO> result = billMapper.billsToBillDTOs(billService.findAll());
        if (result.isEmpty()){
            System.out.println("[BILLS] - No se han encontrado ningún lote.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
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
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
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
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
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
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
