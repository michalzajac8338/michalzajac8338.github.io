package com.michal.booksylikeapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "enterprises_id")
    private Enterprise enterprise;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "employees_id")
    private Set<Review> reviews;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Set<Visit> visits;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<Availability> availabilityList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id")
    private Role role;
}
