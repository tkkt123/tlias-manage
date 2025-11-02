package com.tk.tliaswebmanagment.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final String keyStr = "KkFmT3BqU2V4d1p5ckN1QHZHejJNdTV8IX4lKiZbJyg8Kz0q";
    private static final SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(keyStr));
    private static final Long expiration=1000*60*60*24L;//1天

    public static String createToken(Integer id,String username){
        Map<String,Object> dataMap= new HashMap<>();
        dataMap.put("id",id);
        dataMap.put("username",username);

        return Jwts
                .builder()
                .signWith(key)
                .claims(dataMap)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public static Claims parseToken(String token){
        try{
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (Exception e){
            throw new RuntimeException("用户未登录,token解析失败");
        }
    }

    public  static void main(String[] args) {
        String jwt=createToken(1001,"张三");
        System.out.println(jwt);
        Claims claims=parseToken(jwt);
        System.out.println(claims.get("id"));
        System.out.println(claims.get("username"));
    }

}
