package com.backoffice.config;

import com.backoffice.entites.Users.ERole;
import com.backoffice.entites.Users.User;
import com.backoffice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    @Autowired
    private UserRepository userRepository;

    private static final long serialUID =-2550185165626007488L;

    private static long JWT_TOKEN_VALIDITY= 10 * 60 * 60;

    @Value("{jwt.secret}")
    private String secret;

    /** méthode pour récuperer le token*/
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims:: getSubject);
    }

    /** méthode pour récuperer la date d'expiration du token */
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims:: getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /** */
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /** Verifier si le token à expirer */
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /** méthode pour récupérer le token de générer */
    public String generateToken(User user){
        Map<String,Object> claims = new HashMap<>();
        ERole eRole = user.getERole();
        claims.put("role",eRole.getNom());
        return createToken(claims,user.getEmail());
    }

    /** méthode pour generer un token */
    public String createToken(Map<String,Object> claims, String subject){
        User user = userRepository.findByEmail(subject).orElseThrow(()-> new IllegalArgumentException("Invalid username "));
        String role = user.getERole().getNom();
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(
                        new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    /** methode pour verifier si le token est toujours valide*/
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
