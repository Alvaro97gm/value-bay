package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.mapper.AuctionMapper;
import com.alvarogm.valuebay.service.AuctionService;
import com.alvarogm.valuebay.domain.dto.AuctionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auctions")
@Transactional
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @Autowired
    AuctionMapper auctionMapper;

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<AuctionDTO> getAuction(@PathVariable(name = "id") Integer auctionId){

        AuctionDTO result = auctionService.findByAuctionId(auctionId);

        if(result == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<AuctionDTO>> getAuctions(){

        List<AuctionDTO> result = auctionService.findAll();

        if(result.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> createAuction(
            @RequestParam("auctionId") Integer auctionId,
            @RequestParam(value = "lotIds", required = false) List<Integer> lotIds,
            @RequestParam(value = "endTime", required = false) Date endTime
    ){

        AuctionDTO auctionDTO = new AuctionDTO();

        auctionDTO.setAuctionId(auctionId);
        if(!lotIds.isEmpty()) auctionDTO.setLotIds(lotIds);
        if(endTime != null) auctionDTO.setEndTime(endTime);

        try{
            auctionService.insertAuction(auctionDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteAuction(@PathVariable(name = "id") Integer auctionId){

        try{
            auctionService.deleteByAuctionId(auctionId);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
