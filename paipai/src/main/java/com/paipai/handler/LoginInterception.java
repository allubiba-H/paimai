package com.paipai.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.paipai.paipai.util.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterception implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        String Token = request.getHeader("X-Token");
        DecodedJWT jwt = JwtUtils.verify(Token);
        if (jwt.getToken().isEmpty()) {
            flag = false;
        }

        return flag;
    }

}
