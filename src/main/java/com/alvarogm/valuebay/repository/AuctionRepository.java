package com.alvarogm.valuebay.repository;

import com.alvarogm.valuebay.domain.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, String> {

    Auction findByAuctionId(Integer auctionId);
    boolean deleteByAuctionId(Integer auctionId);
}
