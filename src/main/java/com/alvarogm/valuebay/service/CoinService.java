package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.model.Coin;
import com.alvarogm.valuebay.domain.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Gestionar excepciones si no se encuentran ocurrencias

@Service
public class CoinService{

    @Autowired
    private CoinRepository coinRepository;

    public List<Coin> findAll(){
        return coinRepository.findAll();
    }

    public Coin findById(Integer lotId){
        return coinRepository.findByLotId(lotId);
    }
}
