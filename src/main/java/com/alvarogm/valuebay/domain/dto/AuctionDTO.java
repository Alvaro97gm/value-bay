package com.alvarogm.valuebay.domain.dto;

import com.alvarogm.valuebay.domain.model.Auction;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Setter
@Getter
public class AuctionDTO {

    private Integer auctionId;
    private List<Integer> lotIds;
    private Date endTime;

    public AuctionDTO(){}
    public AuctionDTO(Integer auctionId, Date endTime){
        this.auctionId = auctionId;
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public List<Integer> getLotIds() {
        return lotIds;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setLotIds(List<Integer> lotIds) {
        this.lotIds = lotIds;
    }
}
