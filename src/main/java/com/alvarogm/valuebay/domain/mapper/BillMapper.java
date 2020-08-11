package com.alvarogm.valuebay.domain.mapper;

import com.alvarogm.valuebay.domain.ConservationStatus;
import com.alvarogm.valuebay.domain.dto.BillDTO;
import com.alvarogm.valuebay.domain.model.Bill;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BillMapper {

    public abstract BillDTO billToBillDTO(Bill bill);
    public abstract Bill billDTOToBill(BillDTO billDTO);
    public abstract List<BillDTO> billsToBillDTOs(List<Bill> Bill);

    @AfterMapping
    public void translateConservationStatus(@MappingTarget BillDTO billDTO, Bill bill){
        billDTO.setConservationStatusDetailed(
                ConservationStatus.translateConservationStatus(bill.getConservationStatus())
        );
    }
}
