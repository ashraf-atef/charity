package org.revo.charity.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.revo.charity.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.HashMap;

@Service
public class JwtSigner {
    private final ObjectMapper objectMapper;
    private final KeyPair keyPair;

    public JwtSigner(ObjectMapper objectMapper, KeyPair keyPair) {
        this.objectMapper = objectMapper;
        this.keyPair = keyPair;
    }

    public String createJwt(UserDetails userDetails) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", userDetails);
        return Jwts.builder()
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setIssuer("identity")
                .setClaims(map)
//                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(15))))
//                .setIssuedAt(Date.from(Instant.now()))
                .compact();

    }

    public User validateJwt(String jwt) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(jwt);
        return objectMapper.convertValue(claimsJws.getBody().get("user"), User.class);
    }

}
