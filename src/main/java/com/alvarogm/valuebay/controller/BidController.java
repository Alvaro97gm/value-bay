package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.persistence.domain.dto.BidDTO;
import com.alvarogm.valuebay.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class BidController {

    @Autowired
    BidService bidService;

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/bids/get/{bidId}")
    public ResponseEntity<BidDTO> getBid(@PathVariable(name = "bidId") Integer bidId){

        try{

            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/bids/get/all")
    public ResponseEntity<List<BidDTO>> getBids(){

        try{
            List<BidDTO> bids = bidService.findAll();
            return ResponseEntity.ok(bids);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/bids/get/lotId/{lotId}")
    public ResponseEntity<List<BidDTO>> getBidsByLotId(@PathVariable(name = "lotId") Integer lotId){

        try{

            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/bids/get/insert")
    public ResponseEntity<ResponseStatus> insert(){

        try{

            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
