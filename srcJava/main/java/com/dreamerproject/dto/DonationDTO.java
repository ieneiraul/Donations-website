package com.dreamerproject.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.util.Date;

@Data
public class DonationDTO {
    private Long donatorID;
    private Long storyID;
    private Double amount;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
}
