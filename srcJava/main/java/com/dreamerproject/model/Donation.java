package com.dreamerproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "donations")
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "donator_id")
    private User donator;
    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;
    private Double amount;
    private LocalDateTime date;
}
