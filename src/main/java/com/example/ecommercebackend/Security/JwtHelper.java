package com.example.ecommercebackend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private String secret  = "jwtTokenKey";

    //retrieve user name from jwt token
    public String getUsername(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }


    //expire date
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check token is expired
    private boolean isTokenExpired(String token) {
         final Date expirationDateFromToken = getExpirationDateFromToken(token);
        return expirationDateFromToken.before(new Date());
    }

    //generated token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername()) ;
    }

    //compaction of the JWT to a Url-safe String
    private String doGenerateToken(Map<String,Object> claims , String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
                ;
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
      final   Claims allClaimFromToken = getAllClaimFromToken(token);
        return claimsResolver.apply(allClaimFromToken);
    }

    private Claims getAllClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    }

    //validate Token
    public Boolean validateToken(String token , UserDetails userDetails) {
        final String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
