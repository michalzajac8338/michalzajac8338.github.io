package com.michal.booksylikeapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="workdays")
public class Workday {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

//    @Column(unique = true)
    private LocalDate date;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Employee employee;

    @OneToMany(mappedBy = "workday", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Visit> visits;

}
