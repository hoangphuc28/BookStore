package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TokenService {
    private static final String SECRET_KEY = "electricecommerceinhochiminhuniversityforeignlanguageandinformationtechnology2023";
    public String generateToken(User registrationInfo) {
        Claims claims = Jwts.claims();
        claims.put("name", registrationInfo.getName());
        claims.put("phone", registrationInfo.getPhone());
        claims.put("email", registrationInfo.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public User decryptToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String name = (String) claims.get("name");
        String phone = (String) claims.get("phone");
        String email = (String) claims.get("email");

        return new User(email, name, phone);
    }
    public String generateTokenCourses(String courses) {
        Claims claims = Jwts.claims();
        claims.put("courses", courses);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public List<String> decryptTokenCourses(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        List<String> courses = (List<String>) claims.get("courses");

        return courses;
    }
}
