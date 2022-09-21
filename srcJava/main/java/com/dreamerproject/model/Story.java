package com.dreamerproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stories")
@AllArgsConstructor
@NoArgsConstructor
public class Story {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private String text;
    @Column(name = "amount_needed")
    private Double amountNeeded;
    @Column(name = "amount_given")
    private Double amountGiven;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;



    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", authorUsername=" + author.getUsername() +
                ", text='" + text + '\'' +
                ", amountNeeded=" + amountNeeded +
                ", amountGiven=" + amountGiven +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
