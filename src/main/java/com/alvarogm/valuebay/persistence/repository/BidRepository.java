package com.alvarogm.valuebay.persistence.repository;

import com.alvarogm.valuebay.persistence.domain.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BidRepository extends JpaRepository<Bid, String> {

    List<Bid> findByFkCoin(Integer fkCoin);
    List<Bid> findByFkBill(Integer fkBill);
    List<Bid> findByFkUser(Integer fkUser);
}
