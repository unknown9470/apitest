package com.example.apitest.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 86400000;
    private static final String SECRET_KEY = "1133c8b87ea7122e90a3da3db22784820d43b3c57ac784f5189d646af091aa411d835ba3a966bef47573a3aff161a84c2306a2fefaec240a8c7636b132b554399d3d03c0c97df3d0be8fcba9a383b327b4fc0f6e2a497417bfc07524d9b7268eaaf6b177d8770510b373233d85c8945e1cc9827dfb68fd024926b569133f5a189ecc23d238504f7d74b819a77e5708717d56bed273a1c0fbd31690f1989cea0d434ad95953515c8dee26395ad7e8e6d3d81fbf79a56ca98f9805acdc94fbd33daffded8d51bdc0ca80c2dfaea44c51381d0b64fdabd892e07f6ea01174e0baa40469acc73f7a63dc9273a639475dac841990991069b91b43ac30b2e3db6020cb5972f48ce8c960cd1a1ac766ec386f16890dae85c0a8fac2770303f95197bac667f9a5f64f169d80bd825b91fb59dca547e5e30d91de4e9db5b42ec0e1399b0152f3eb2b2dc157ceb35b35bc7cd5d8f3e67d5439b783d66aacd4b1abe57c5ecf2ac59a3c95d6ec031ede52b8d9655a4111202611449f019d92879167cface71920ea2d7a5d9950bce48b361f55092f02d6e3fc0dc188656c32e74a7033783fae7d7612e3ddc9be4e13509f146cbcddc5d38b4742b0f9dcbc549c5ec8e9d2c2a6dddcccb1f14823caf9926114927e5f98069b4b3f2ef9d8a40643f4443c0e83e774aa8831ffe09c6acbfc56817bc196b1f512a883909fa8db4085ff8e8fe266ec";

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
        return expiration.before(new Date());
    }

}
