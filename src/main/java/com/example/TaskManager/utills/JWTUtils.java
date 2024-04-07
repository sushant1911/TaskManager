package com.example.TaskManager.utills;

import com.example.TaskManager.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtils {
    private SecretKey key;
    private static final long EXPIRATION_TIME = 86400000;//24 hour;

    public JWTUtils() {
        String secreteString = "kjkajnhhhqw978y12938y12938y7bsabdibidb71y2983y1298bsjhbdcjshbdcibhwdib";
        byte[] keyByte = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyByte, "HmacSHA256");
    }

    public String generateToken(User user) {
        return Jwts.builder().
                subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key).compact();
    }

    public String extractUserDetail(String token)
    {
        return extractClaims(token, Claims::getSubject);
    }

    private <T>  T extractClaims(String token, Function<Claims,T> claimsTFunction) {

        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }
    public  boolean isValidToken(String token,User user)
    {
        final String userMail=extractUserDetail(token);
        return userMail.equals(user.getEmail())&&!isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
