package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.ConservationStatus;
import com.alvarogm.valuebay.domain.dto.BillDTO;
import com.alvarogm.valuebay.domain.mapper.BillMapper;
import com.alvarogm.valuebay.domain.model.Bill;
import com.alvarogm.valuebay.domain.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;


    public List<Bill> findAll(){
        return billRepository.findAll();
    }


    public Bill findByLotId(Integer lotId){
        return billRepository.findByLotId(lotId);
    }


    public void insert(BillDTO billDTO, @Nullable Integer fkAuction){

        try{
            Bill bill = billMapper.billDTOToBill(billDTO);
            if(fkAuction != null){
                bill.setFkAuction(fkAuction);
            }
            billRepository.save(bill);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void modifyBill(Integer lotId, @Nullable Integer itemValue, @Nullable Integer emissionYear,
                           @Nullable Integer emissionMonth, @Nullable Integer emissionDay,
                           @Nullable String conservationStatus, @Nullable Float price,
                           @Nullable Integer fkAuction){

        Bill bill = findByLotId(lotId);
        if(bill != null){
            if(itemValue != null) bill.setItemValue(itemValue);
            if(emissionYear != null) bill.setEmissionYear(emissionYear);
            if(emissionMonth != null) bill.setEmissionYear(emissionMonth);
            if(emissionDay != null) bill.setEmissionYear(emissionDay);
            if(conservationStatus != null){
                if(ConservationStatus.isValidConservationStatus(conservationStatus))
                    bill.setConservationStatus(conservationStatus);
                else System.out.println("Estado de conservación inválido: " + conservationStatus);
            }
            if(price != null) bill.setPrice(price);
            if(fkAuction != null) bill.setFkAuction(fkAuction);

            try{
                billRepository.save(bill);
                System.out.println("[BILLS] - Lote: " + lotId + " insertado en el sistema." );
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


    public void deleteByLotId(Integer lotId){
        billRepository.deleteByLotId(lotId);
    }


    public void findLotAndSetFkAuction(Integer auctionId, List<Integer> lotIds){

        Bill auxBill;
        for(Integer lotId : lotIds){
            auxBill = billRepository.findByLotId(lotId);
            if(auxBill != null && auxBill.getFkAuction() == null){
                auxBill.setFkAuction(auctionId);
                billRepository.save(auxBill);
                System.out.println("[BILLS] - Lote: " + lotId + " encontrado y enlazado a la subasta " + auctionId +  ".");
            }else if (auxBill != null && auxBill.getFkAuction() != null) {
                System.out.println("[BILLS] - El lote " + lotId + " ya pertenece a la subasta" + auxBill.getFkAuction() + ".");
            }
        }
    }


    public void findLotAndRemoveFkAuction(Integer auctionId, List<Integer> lotIds){

        Bill auxBill;
        for(Integer lotId : lotIds){
            auxBill = billRepository.findByLotId(lotId);
            if(auxBill != null && auctionId.equals(auxBill.getFkAuction())){
                auxBill.setFkAuction(null);
                billRepository.save(auxBill);
                System.out.println("[BILLS] - Lote: " + lotId + " encontrado y desenlazado de la subasta " + auctionId +  ".");
            } else if (auxBill != null && !auctionId.equals(auxBill.getFkAuction())) {
                System.out.println("[BILLS] - El lote " + lotId + " no se ha podido desenlazar de la subasta " + auctionId +
                        " porque ya pertenece a otra subasta.");
            } else if (auxBill != null && auxBill.getFkAuction() == null){
                System.out.println("[BILLS] - El lote " + lotId + " no se ha podido desenlazar de la subasta " + auctionId +
                        " porque no pertenece a ninguna subasta.");
            }
        }
    }


    public BillDTO createBillDTO(Integer itemValue, Integer emissionYear, Integer emissionMonth,
                                 Integer emissionDay, String conservationStatus, float price){
        return new BillDTO(CommonService.generate5DigitsId(), itemValue, emissionYear, emissionMonth, emissionDay, conservationStatus, price);
    }
}
