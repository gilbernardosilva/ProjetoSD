package edu.ufp.inf.sd.rabbit.project.variables;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;


import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.security.SecureRandom;

public class Token{

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private static final String issuer = "AdvancedWars";
    private static final int expiresIn = 3600;
    private static final int keyLength = 256;
    private String value;

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

    public Boolean verify() throws JWTVerificationException {
        try {
            verifier.verify(this.value);
            return true; // token is valid
        } catch (JWTVerificationException e) {
            return false; // token is invalid
        }
    }

    public void updateToken(String username){
        setValue(generate(username));
    }

    public void setValue(String token){
        this.value=token;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}