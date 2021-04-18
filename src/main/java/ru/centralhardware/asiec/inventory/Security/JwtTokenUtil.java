package ru.centralhardware.asiec.inventory.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.centralhardware.asiec.inventory.Configuration.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 365 * 24 * 60 * 60;

    private final Config config;

    public JwtTokenUtil(Config config) {
        this.config = config;
    }

    /**
     * get user by giving token
     * @param token jwt token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    /**
     * get date of expire from jwt token
     * @param token jwt token
     * @return date of expiration
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final var claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(config.secret).parseClaimsJws(token).getBody();
    }

    /**
     * check whether the token has expired
     * @param token jwt token
     * @return true if token expired
     */
    private Boolean isTokenExpired(String token) {
        final var expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    /**
     * generate token for user
     * @param userDetails object with username and password
     * @return jwt token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
    }

    /**
     * compaction of the JWT to a URL-safe string
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     * @return jwt token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject, Date validTo) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(validTo)
                .signWith(SignatureAlgorithm.HS512, config.secret).compact();
    }


    /**
     * validate token
     * @param token jwt token
     * @param userDetails object with username and password
     * @return true if token valid
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (!config.enableAuth) return true;
        final var username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
