package com.debbech.divide.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friendship_registery")
public class FriendshipRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid_from")
    private User from;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid_to")
    private User to;

    private LocalDateTime madeOn;
    private LocalDateTime acceptedOn;
    private LocalDateTime unfriendedOn;
    private LocalDateTime canceledOn;
    private boolean isDeleted;
}
