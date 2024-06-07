package com.debbech.divide.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidateLoginResp {
    private String token;
    private String ok;
    private String error;
}
