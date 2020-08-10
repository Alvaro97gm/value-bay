package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.dto.AuctionDTO;
import com.alvarogm.valuebay.domain.mapper.AuctionMapper;
import com.alvarogm.valuebay.domain.model.Auction;
import com.alvarogm.valuebay.domain.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService{

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    AuctionMapper auctionMapper;

    public List<AuctionDTO> findAll(){
        return auctionMapper.auctionsToAuctionDTOs(auctionRepository.findAll());
    }

    public AuctionDTO findByAuctionId(Integer auctionId){
        return auctionMapper.auctionToAuctionDTO(auctionRepository.findByAuctionId(auctionId));
    }

    public void deleteByAuctionId(Integer auctionId){
        auctionRepository.deleteByAuctionId(auctionId);
    }

    public void insertAuction(AuctionDTO auctionDTO){
        Auction auction = auctionMapper.auctionDTOToAuction(auctionDTO);

        /* TODO: Mover esto a billRepository y coinRepository
        Bill auxBill; Coin auxCoin;

        List<Integer> lotIds = auctionDTO.getLotIds();
        for (Integer lotId : lotIds){

            if ((auxBill = billRepository.findByLotId(lotId)) != null){
                if(auxBill.getFkAuction() == null){
                    auxBill.setFkAuction(auctionDTO.getAuctionId());
                    billRepository.save(auxBill);
                }
            }else if((auxCoin = coinRepository.findByLotId(lotId)) != null){
                if(auxCoin.getFkAuction() == null){
                    auxCoin.setFkAuction(auctionDTO.getAuctionId());
                    coinRepository.save(auxCoin);
                }
            }
        }
        */

        auctionRepository.save(auction);
    }
}
