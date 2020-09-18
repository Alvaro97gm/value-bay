package com.alvarogm.valuebay.persistence.domain.mapper;

import com.alvarogm.valuebay.persistence.domain.dto.BidDTO;
import com.alvarogm.valuebay.persistence.domain.model.Bid;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BidMapper {

    public abstract BidDTO bidToBidDTO(Bid bid);
    public abstract List<BidDTO> bidsToBidDTOs(List<Bid> bids);
    public abstract Bid bidDTOToBid(BidDTO bidDTO);
}
