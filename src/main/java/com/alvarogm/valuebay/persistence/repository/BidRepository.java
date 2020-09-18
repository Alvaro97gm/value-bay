package com.alvarogm.valuebay.persistence.repository;

import com.alvarogm.valuebay.persistence.domain.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidRepository extends JpaRepository<Bid, String> {

}
