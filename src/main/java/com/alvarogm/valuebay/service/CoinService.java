package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.ConservationStatus;
import com.alvarogm.valuebay.domain.dto.CoinDTO;
import com.alvarogm.valuebay.domain.mapper.CoinMapper;
import com.alvarogm.valuebay.domain.model.Coin;
import com.alvarogm.valuebay.domain.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CoinService{

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinMapper coinMapper;


    public List<Coin> findAll(){
        return coinRepository.findAll();
    }


    public Coin findByLotId(Integer lotId){
        return coinRepository.findByLotId(lotId);
    }


    public void insertCoin(CoinDTO coinDTO, @Nullable Integer fkAuction){

        try{
            Coin coin = coinMapper.coinDTOToCoin(coinDTO);
            if(fkAuction != null)
                coin.setFkAuction(fkAuction);
            coinRepository.save(coin);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void modifyCoin(Integer lotId, @Nullable Integer itemValue, @Nullable Integer emissionYear,
                           @Nullable String conservationStatus, @Nullable Float price,
                           @Nullable Integer fkAuction){

        Coin coin = findByLotId(lotId);
        if(coin != null){
            if(itemValue != null) coin.setItemValue(itemValue);
            if(emissionYear != null) coin.setEmissionYear(emissionYear);
            if(conservationStatus != null){
                if(ConservationStatus.isValidConservationStatus(conservationStatus))
                    coin.setConservationStatus(conservationStatus);
                else System.out.println("Estado de conservación inválido: " + conservationStatus);
            }
            if(price != null) coin.setPrice(price);
            if(fkAuction != null) coin.setFkAuction(fkAuction);

            try{
                coinRepository.save(coin);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


    public void deleteByLotId(Integer lotId){
        coinRepository.deleteByLotId(lotId);
    }


    public void findLotAndSetFkAuction(Integer auctionId, List<Integer> lotIds){

        Coin auxCoin;
        for(Integer lotId : lotIds){
            auxCoin = coinRepository.findByLotId(lotId);
            if(auxCoin != null && auxCoin.getFkAuction() == null){
                auxCoin.setFkAuction(auctionId);
                coinRepository.save(auxCoin);
                System.out.println("[COINS] - Lote: " + lotId + " encontrado y enlazado a la subasta " + auctionId + ".");
            } else if (auxCoin != null && auxCoin.getFkAuction() != null){
                System.out.println("[COINS] - El lote " + lotId + " ya pertenece a una subasta.");
            }
        }
    }


    public void findLotAndRemoveFkAuction( Integer auctionId, List<Integer> lotIds){

        Coin auxCoin;
        for(Integer lotId : lotIds){
            auxCoin = coinRepository.findByLotId(lotId);
            if(auxCoin != null && auctionId.equals(auxCoin.getFkAuction())){
                auxCoin.setFkAuction(null);
                coinRepository.save(auxCoin);
                System.out.println("[COINS] - Lote: " + lotId + " encontrado y desenlazado de la subasta " + auctionId + ".");
            } else if (auxCoin != null && !auctionId.equals(auxCoin.getFkAuction())) {
                System.out.println("[COINS] - El lote " + lotId + " no se ha podido desenlazar de la subasta " + auctionId +
                        " ya que pertenece a la subasta " + auxCoin.getFkAuction());
            } else if (auxCoin != null && auxCoin.getFkAuction() == null){
                System.out.println("[COINS] - El lote " + lotId + " no se ha podido desenlazar de la subasta " + auctionId +
                        " porque no pertenece a ninguna subasta.");
            }
        }
    }


    public CoinDTO createCoinDTO(Integer lotId, Integer itemValue, Integer emissionYear,
                                 String conservationStatus, float price){
        return new CoinDTO(lotId, itemValue, emissionYear, conservationStatus, price);
    }
}
