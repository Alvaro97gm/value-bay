package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.persistence.domain.dto.BidDTO;
import com.alvarogm.valuebay.persistence.domain.mapper.BidMapper;
import com.alvarogm.valuebay.persistence.domain.model.Auction;
import com.alvarogm.valuebay.persistence.domain.model.Bid;
import com.alvarogm.valuebay.persistence.domain.model.Bill;
import com.alvarogm.valuebay.persistence.domain.model.Coin;
import com.alvarogm.valuebay.persistence.repository.AuctionRepository;
import com.alvarogm.valuebay.persistence.repository.BidRepository;
import com.alvarogm.valuebay.persistence.repository.BillRepository;
import com.alvarogm.valuebay.persistence.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    BidMapper bidMapper;

    public List<BidDTO> findAll(){
        return bidMapper.bidsToBidDTOs(bidRepository.findAll());
    }

    public List<Bid> findByFkUserId(Integer userId){
        return bidRepository.findByFkUser(userId);
    }

    public List<Bid> findByFkLotId(Integer lotId){
        List<Bid> bids;
        Coin coinResult = coinRepository.findByLotId(lotId);

        if(coinResult != null){
            bids = bidRepository.findByFkCoin(lotId);
        }else{
            bids = bidRepository.findByFkBill(lotId);
        }
        return bids;
    }

    public void insert(BidDTO bid, Integer auctionId){

        Auction auction = auctionRepository.findByAuctionId(auctionId);

        if(System.currentTimeMillis() > auction.getActivationTime().getTime() &&
            System.currentTimeMillis() < auction.getActivationTime().getTime() + auction.getDuration() * 3600000)
        {
            bidRepository.save(bidMapper.bidDTOToBid(bid));
        }
    }
}
