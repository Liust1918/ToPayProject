package com.liust.myproject.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyulong
 * @create 2021-08-31 21:14
 * @create 2021-八月  星期二
 * @project MyChat
 */
@Component
public class JwtTokenUtil {
    //私钥
    private static final String SECRET_KEY = "ABCDFGHSD";

    // 过期时间 1周
    private  static final long EXPIRATION_TIME = 1000L*60*60*60*7;

    /**
     * 生成令牌
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT,username);
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims);
    }

    /**
     * 判断令牌是否过期
     *
     */
    public Boolean isTokenExpired(String token) throws  Exception{
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            new Throwable(e);
        }
        return true;
    }

    /**
     * 验证令牌
     */
    public Boolean validateToken(String token, String iusername) throws Exception {
        String username = getUsernameFromToken(token);
        return (username.equals(iusername) && !isTokenExpired(token));
    }


    /**
     * 从令牌中获取用户名
     */
    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            System.out.println("claims = " + claims.toString());
            username = claims.getSubject();
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        }
        return username;
    }



    /**
     * 刷新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }




    /**
     * 从数据声明生成令牌
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis()+ EXPIRATION_TIME);
        return Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    /**
     * 从令牌中获取数据声明
     */
    private Claims getClaimsFromToken(String token) throws Exception {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            new Throwable(e);
        }
        return claims;
    }
}
