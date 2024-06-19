package com.debbech.divide.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FriendshipRegistryDto {

    private Long id;

    private UserDto from;
    private UserDto to;

    private UserDto opposite;

    private LocalDateTime madeOn;
    private LocalDateTime acceptedOn;
    private LocalDateTime unfriendedOn;
    private LocalDateTime canceledOn;
}
