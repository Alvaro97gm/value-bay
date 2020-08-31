package com.alvarogm.valuebay.persistence.domain.mapper;

import com.alvarogm.valuebay.persistence.domain.ConservationStatus;
import com.alvarogm.valuebay.persistence.domain.dto.CoinDTO;
import com.alvarogm.valuebay.persistence.domain.model.Coin;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CoinMapper {

    public abstract CoinDTO coinToCoinDTO(Coin coin);
    public abstract Coin coinDTOToCoin(CoinDTO coin);
    public abstract List<CoinDTO> coinsToCoinDTOs(List<Coin> Coin);

    @AfterMapping
    public void translateConservationStatus(@MappingTarget CoinDTO coinDTO, Coin bill){
        coinDTO.setConservationStatusDetailed(
                ConservationStatus.translateConservationStatus(bill.getConservationStatus())
        );
    }
}
