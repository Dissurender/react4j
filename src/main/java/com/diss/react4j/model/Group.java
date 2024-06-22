package com.diss.react4j.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "user_group")
public class Group {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String name;

    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> events;
}
