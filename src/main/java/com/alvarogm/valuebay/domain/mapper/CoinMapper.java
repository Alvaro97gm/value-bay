package com.alvarogm.valuebay.domain.mapper;

import com.alvarogm.valuebay.domain.ConservationStatus;
import com.alvarogm.valuebay.domain.dto.CoinDTO;
import com.alvarogm.valuebay.domain.model.Coin;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CoinMapper {

    public abstract CoinDTO coinToCoinDTO(CoinDTO bill);
    public abstract List<CoinDTO> coinToCoinDTOs(List<Coin> Coin);

    @AfterMapping
    public void translateConservationStatus(@MappingTarget CoinDTO coinDTO, Coin bill){
        coinDTO.setConservationStatusDetailed(
                ConservationStatus.translateConservationStatus(bill.getConservationStatus())
        );
    }
}
