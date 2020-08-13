package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.dto.AuctionDTO;
import com.alvarogm.valuebay.domain.mapper.AuctionMapper;
import com.alvarogm.valuebay.domain.model.Auction;
import com.alvarogm.valuebay.domain.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuctionService{

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    BillService billService;

    @Autowired
    CoinService coinService;

    @Autowired
    AuctionMapper auctionMapper;


    public List<AuctionDTO> findAll(){
        return auctionMapper.auctionsToAuctionDTOs(auctionRepository.findAll());
    }


    public AuctionDTO findByAuctionId(Integer auctionId){
        return auctionMapper.auctionToAuctionDTO(auctionRepository.findByAuctionId(auctionId));
    }


    public boolean deleteByAuctionId(Integer auctionId){
        return auctionRepository.deleteByAuctionId(auctionId);
    }


    public void insertAuction(AuctionDTO auctionDTO){

        Auction auction = auctionMapper.auctionDTOToAuction(auctionDTO);
        auctionRepository.save(auction);

        billService.findLotAndSetFkAuction(auctionDTO.getAuctionId(), auctionDTO.getLotIds());
        coinService.findLotAndSetFkAuction(auctionDTO.getAuctionId(), auctionDTO.getLotIds());
    }


    public void modifyAuction(Integer auctionId, @Nullable Date endTime, @Nullable List<Integer> addLotIds,
                              @Nullable List<Integer> delLotIds, @Nullable Boolean active){

        Auction auction = auctionMapper.auctionDTOToAuction(findByAuctionId(auctionId));
        if(auction != null){
            if(endTime != null) auction.setEndTime(endTime);
            if(addLotIds != null && !addLotIds.isEmpty()) {
                billService.findLotAndSetFkAuction(auctionId, addLotIds);
                coinService.findLotAndSetFkAuction(auctionId, addLotIds);
            }
            if(delLotIds != null && !delLotIds.isEmpty()){
                billService.findLotAndRemoveFkAuction(auctionId, delLotIds);
                coinService.findLotAndRemoveFkAuction(auctionId, delLotIds);
            }
            if(active != null) auction.setActive(active);

            auctionRepository.save(auction);
        }
    }


    public AuctionDTO createAuctionDTO(Integer auctionId, List<Integer> lotIds, Integer hoursActive){

        int HOUR_IN_MILLISECONDS = 3600 * 1000;

        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(auctionId);

        if(!lotIds.isEmpty())
            auctionDTO.setLotIds(lotIds);

        if(hoursActive != null)
            auctionDTO.setEndTime(new Date(System.currentTimeMillis() + (hoursActive * HOUR_IN_MILLISECONDS)));
        else
            auctionDTO.setEndTime(new Date(System.currentTimeMillis() + (24 * HOUR_IN_MILLISECONDS)));

        return auctionDTO;
    }
}
