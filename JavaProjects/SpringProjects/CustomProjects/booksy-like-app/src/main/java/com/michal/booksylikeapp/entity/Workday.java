package com.michal.booksylikeapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="workdays")
public class Workday {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
    private LocalDate date;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Employee employee;

    @OneToMany(mappedBy = "workday", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Visit> visits;

}
