package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.model.Bill;
import com.alvarogm.valuebay.domain.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Gestionar excepciones si no se encuentran ocurrencias

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public List<Bill> findAll(){
        return billRepository.findAll();
    }

    public Bill findById(Integer lotId){
        return billRepository.findByLotId(lotId);
    }
}
