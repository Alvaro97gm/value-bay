package com.alvarogm.valuebay.domain.repository;

import com.alvarogm.valuebay.domain.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
    Coin findByLotId(Integer lotId);
}
