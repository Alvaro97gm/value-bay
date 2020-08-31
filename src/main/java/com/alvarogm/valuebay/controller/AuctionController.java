package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.service.AuctionService;
import com.alvarogm.valuebay.persistence.domain.dto.AuctionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auctions")
@Transactional
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<AuctionDTO> getAuction(@PathVariable(name = "id") Integer auctionId){

        AuctionDTO result = auctionService.findByAuctionId(auctionId);

        if(result == null){
            System.out.println("[AUCTIONS] - No se ha encontrado ninguna ocurrencia.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/get/all", produces = "application/json")
    public ResponseEntity<List<AuctionDTO>> getAuctions(){

        List<AuctionDTO> result = auctionService.findAll();

        if(result.isEmpty()){
            System.out.println("[AUCTIONS] - No se ha encontrado ninguna ocurrencia.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/insert", produces = "application/json")
    public ResponseEntity<ResponseStatus> createAuction(
            @RequestParam(value = "auctionId") Integer auctionId,
            @RequestParam(value = "lotIds", required = false) List<Integer> lotIds,
            @RequestParam(value = "hoursActive", required = false) Integer hoursActive
    ){

        try{
            auctionService.insertAuction(auctionService.createAuctionDTO(auctionId, lotIds, hoursActive));
            System.out.println("[AUCTIONS] - Subasta: " + auctionId + " insertada en el sistema." );
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/mod/{auctionId}", produces = "application/json")
    public ResponseEntity<ResponseStatus> modifyAuction(
        @PathVariable(name = "auctionId") Integer auctionId,
        @RequestParam(value = "endTime", required = false) Date endTime,
        @RequestParam(value = "addLotIds", required = false) List<Integer> addLotsIds,
        @RequestParam(value = "delLotIds", required = false) List<Integer> delLotsIds,
        @RequestParam(value = "active", required = false) Boolean active
    ){
        try{
            auctionService.modifyAuction(auctionId, endTime, addLotsIds, delLotsIds, active);
            System.out.println("[AUCTIONS] - Subasta: " + auctionId + " modificada.");
            return ResponseEntity.ok().build();
        } catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<ResponseStatus> deleteAuction(@PathVariable(name = "id") Integer auctionId){

        try{
            if(!auctionService.deleteByAuctionId(auctionId))
                throw new Exception("[AUCTIONS] - La subasta " + auctionId + " no se ha podido eliminar  del sistema.");
            System.out.println("[AUCTIONS] - Subasta: " + auctionId + " eliminada del sistema." );
            return ResponseEntity.ok().build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
