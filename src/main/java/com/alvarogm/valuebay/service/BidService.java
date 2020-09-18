package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.persistence.domain.dto.BidDTO;
import com.alvarogm.valuebay.persistence.domain.mapper.BidMapper;
import com.alvarogm.valuebay.persistence.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    BidMapper bidMapper;

    public List<BidDTO> findAll(){
        return bidMapper.bidsToBidDTOs(bidRepository.findAll());
    }
}
