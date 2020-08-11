package com.alvarogm.valuebay.domain.repository;

import com.alvarogm.valuebay.domain.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, String> {

    Bill findByLotId(Integer lotId);
    List<Bill> findByFkAuction(Integer auctionId);
    void deleteByLotId(Integer lotId);
}
