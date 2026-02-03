package com.keisar.MovieBookingApplication.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String theaterName;
    private String theaterLocation;
    private int theaterCapacity;
    private String theaterScreenType;

    @OneToMany(mappedBy = "theater", fetch =  FetchType.LAZY)
    private List<Show> shows;
}
