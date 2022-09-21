package com.dreamerproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private Long id;
    private User reporter;
    private Story story;
    private String motive;
    private LocalDateTime time;
}
