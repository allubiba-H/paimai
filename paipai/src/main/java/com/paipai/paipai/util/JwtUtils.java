package com.paipai.paipai.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.paipai.paipai.entity.Admin;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class JwtUtils {
    private static final String SING = "habibulla";

    public static String getToken(Admin admin) throws UnsupportedEncodingException {
        Calendar instance = Calendar.getInstance();
        //默认7天过期
        instance.add(Calendar.DATE, 7);
        //创建builder
        JWTCreator.Builder builder = JWT.create();
//        Map<String,Object> map = new HashMap<>();
//        map.put("username",admin.getAname());
//        map.put("id",admin.getAid());
//        builder.withHeader(map);
        builder.withAudience(admin.getAid());
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));
        return token;
    }

    public static DecodedJWT verify(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

}
