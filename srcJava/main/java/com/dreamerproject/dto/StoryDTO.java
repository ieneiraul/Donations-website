package com.dreamerproject.dto;

import com.dreamerproject.model.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class StoryDTO {
    private Long id;
    private String authorUsername;
    private String text;
    private Double amountNeeded;
    private Double amountGiven;
    private String imageUrl;
    private String videoUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
