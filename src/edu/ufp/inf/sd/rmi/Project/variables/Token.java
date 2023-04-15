package edu.ufp.inf.sd.rmi.Project.variables;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.time.Instant;
import java.util.Date;
import java.security.SecureRandom;

public class Token {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private static final String issuer = "AdvancedWars";
    private static final int expiresIn = 25;
    private static final int keyLength = 256;
    private final String value;

    public Token(String subject) {
        byte[] secret = generateSecret();
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
        this.value= this.generate(subject);
    }

    private byte[] generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[keyLength / 8];
        random.nextBytes(bytes);
        return bytes;
    }

    public String generate(String subject) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiresAt = Date.from(now.plusSeconds(expiresIn));
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public String verify() throws JWTVerificationException {
        DecodedJWT decodedJWT = verifier.verify(this.value);
        return decodedJWT.getToken();
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}