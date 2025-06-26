package dev.eliezer.lojaonline.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.eliezer.lojaonline.modules.shared.entities.UserToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class JWTUserProvider {

    @Value("${security.token.secret-user}")
    private String secretKey;

    public DecodedJWT validateToken(String token){
        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        try {
            var tokenDecoded = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return tokenDecoded;
        }catch (JWTVerificationException e){
            e.printStackTrace();
            return null;
        }
    }

    public <T> UserToken tokenGenerator(String userId, List<T> userRole){

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withIssuer("LojaOnline")
                .withSubject(userId)
                .withClaim("roles", Collections.singletonList(userRole))
                .withExpiresAt(expiresIn)
                .sign(algorithm);


        var userToken = UserToken.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();

        return userToken;
    }


}