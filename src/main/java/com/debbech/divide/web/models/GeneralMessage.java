package com.debbech.divide.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralMessage {
    private String error;
    private boolean ok;
}
