package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.dto.CoinDTO;
import com.alvarogm.valuebay.domain.mapper.CoinMapper;
import com.alvarogm.valuebay.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
@Transactional
public class CoinController {

    @Autowired
    CoinService coinService;

    @Autowired
    CoinMapper coinMapper;


    @GetMapping(value = "/get/{lotId}", produces = "application/json")
    public ResponseEntity<CoinDTO> getCoin(@PathVariable(name = "lotId") Integer lotId){

        CoinDTO result = coinMapper.coinToCoinDTO(coinService.findByLotId(lotId));
        if (result == null){
            System.out.println("[COINS] - No se ha encontrado el lote " + lotId + ".");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<CoinDTO>> getCoins(){

        List<CoinDTO> result = coinMapper.coinsToCoinDTOs(coinService.findAll());
        if (result.isEmpty()){
            System.out.println("[COINS] - No se ha encontrado ningún lote.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> insertCoin(
        @RequestParam(value = "lotId") Integer lotId,
        @RequestParam(value = "itemValue") Integer itemValue,
        @RequestParam(value = "emissionYear") Integer emissionYear,
        @RequestParam(value = "conservationStatus") String conservationStatus,
        @RequestParam(value = "price") Float price,
        @RequestParam(value = "fkAuction", required = false) Integer fkAuction
    ){

        try{
            coinService.insertCoin(coinService.createCoinDTO(lotId, itemValue, emissionYear, conservationStatus, price), fkAuction);
            System.out.println("[COINS] - Lote: " + lotId + " insertado en el sistema." );
            return ResponseEntity.ok().build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }


    @PostMapping(value = "/mod/{lotId}", produces = "application/json")
    public ResponseEntity<ResponseStatus> modifyCoin(
            @PathVariable(name = "lotId") Integer lotId,
            @RequestParam(value = "itemValue", required = false) Integer itemValue,
            @RequestParam(value = "emissionYear", required = false) Integer emissionYear,
            @RequestParam(value = "conservationStatus", required = false) String conservationStatus,
            @RequestParam(value = "price", required = false) Float price,
            @RequestParam(value = "fkAuction", required = false) Integer fkAuction
    ){


        try{
            coinService.modifyCoin(lotId, itemValue, emissionYear, conservationStatus, price, fkAuction);
            System.out.println("[COINS] - Lote: " + lotId + " modificado.");
            return ResponseEntity.ok().build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }


    @DeleteMapping(value = "/delete/{lotId}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteCoin(@PathVariable(name = "lotId") Integer lotId){

        try{
            coinService.deleteByLotId(lotId);
            System.out.println("[COINS] - Lote: " + lotId + " eliminado del sistema." );
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
