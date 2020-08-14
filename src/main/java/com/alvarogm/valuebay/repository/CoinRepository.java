package com.alvarogm.valuebay.repository;

import com.alvarogm.valuebay.domain.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, String> {

    Coin findByLotId(Integer lotId);
    List<Coin> findByFkAuction(Integer auctionId);
    void deleteByLotId(Integer lotId);
}
