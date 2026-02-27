package project.application.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.stereotype.Component;
import project.application.entities.Users;

import java.time.Instant;
import java.util.Optional;


@Component
public class TokenConfig {

    private String secret = "secret";

    public String generateToken(Users users){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", users.getUserId())
                .withSubject(users.getName())
                .withExpiresAt(Instant.now().plusSeconds(845454))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .nome(decode.getSubject())
                    .build());
        }
        catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
