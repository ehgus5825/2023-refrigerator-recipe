package refrigerator.server.security.authentication.application.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import refrigerator.server.security.authentication.application.TokenStatus;
import refrigerator.server.security.authentication.application.ClaimsDto;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import java.security.Key;
import java.util.Date;

import static refrigerator.server.security.config.JsonWebTokenKey.*;


@Component
public class JsonWebTokenService implements JsonWebTokenUseCase {

    private final Key key;

    public JsonWebTokenService(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    @Override
    public String createToken(String username, String authority, long expirationTime) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTHORITIES_KEY, authority);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 토큰 발행 유저 정보
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + expirationTime)) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘 설정
                .compact();
    }

    @Override
    public ClaimsDto parseClaims(String token) {
        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new ClaimsDto(claims.getSubject(), claims.get("auth").toString(), claims.getExpiration());
        } catch(ExpiredJwtException e){ // 만료된 토큰
            Claims expiredClaims = e.getClaims();
            return new ClaimsDto(expiredClaims.getSubject(), expiredClaims.get("auth").toString(), expiredClaims.getExpiration());
        }
    }

    @Override
    public TokenStatus getTokenStatus(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return TokenStatus.PASS;
        } catch (ExpiredJwtException e){
            return TokenStatus.EXPIRED;
        } catch (Exception e){
            return TokenStatus.WRONG;
        }
    }

}
