package com.debbech.divide.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JwtService {

    @Value("${spring.security.secret}")
    private String secret;

    public String createJwt(String uuid, HttpServletRequest req){
        Algorithm algo = Algorithm.HMAC256(secret);
        String accesstoken = JWT.create()
                .withSubject(uuid)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withIssuer(req.getRequestURI().toString())
                .withClaim("roles", true)
                .sign(algo);
        return accesstoken;
    }
}
