package com.michal.socialnetworkingsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;


    @OneToMany(mappedBy = "creator")
    private List<Post> posts;
    @OneToMany(mappedBy = "creator")
    private List<Comment> comments;
    @OneToMany(mappedBy = "creator")
    private List<CommentLike> likedComments;
    @OneToMany(mappedBy = "creator")
    private List<PostLike> likedPosts;




    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "following")
    private List<User> followers = new ArrayList<>();

    @JoinTable(name = "followers",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> following = new ArrayList<>();

}
