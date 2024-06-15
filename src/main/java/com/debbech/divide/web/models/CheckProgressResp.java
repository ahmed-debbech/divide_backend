package com.debbech.divide.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CheckProgressResp {
    private boolean ready;
    private String message;
}
