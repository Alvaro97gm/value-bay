package com.alvarogm.valuebay.domain.repository;

import com.alvarogm.valuebay.domain.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, String> {
    Bill findByLotId(Integer lotId);
}
