package com.alvarogm.valuebay.domain.mapper;

import com.alvarogm.valuebay.domain.dto.AuctionDTO;
import com.alvarogm.valuebay.domain.model.Auction;
import com.alvarogm.valuebay.domain.model.Bill;
import com.alvarogm.valuebay.domain.model.Coin;
import com.alvarogm.valuebay.domain.repository.BillRepository;
import com.alvarogm.valuebay.domain.repository.CoinRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AuctionMapper {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CoinRepository coinRepository;

    public abstract AuctionDTO auctionToAuctionDTO(Auction auction);
    public abstract List<AuctionDTO> auctionsToAuctionDTOs(List<Auction> auctions);
    public abstract Auction auctionDTOToAuction(AuctionDTO auctionDTO);
    public abstract List<Auction> auctionDTOsToAuctions(List<AuctionDTO> auctionDTOS);

    @AfterMapping
    public void fillLots(@MappingTarget AuctionDTO auctionDto, Auction auction) {

        List<Integer> lotIds = new ArrayList<>();

        List<Bill> billAuxList = billRepository.findByFkAuction(auction.getAuctionId());
        billAuxList.forEach(bill -> lotIds.add(bill.getLotId()));

        List<Coin> coinAuxList = coinRepository.findByFkAuction(auction.getAuctionId());
        coinAuxList.forEach(coin -> lotIds.add(coin.getLotId()));

        auctionDto.setLotIds(lotIds);
    }
}
