package com.dreamerproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Update {
    private Long id;
    private User author;
    private Story story;
    private List<User> receivers;
    private String text;
    private LocalDateTime time;
    private String imageUrl;
    private String videoUrl;
}
