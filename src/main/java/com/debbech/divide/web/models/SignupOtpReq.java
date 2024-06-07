package com.debbech.divide.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SignupOtpReq {
    private String email;
    private String code;
}
