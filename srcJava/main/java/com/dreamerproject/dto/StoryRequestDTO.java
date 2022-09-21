package com.dreamerproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class StoryRequestDTO {
    private String text;
    private Double amountNeeded;
    private Double amountGiven;
    private String imageUrl;
    private String videoUrl;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
