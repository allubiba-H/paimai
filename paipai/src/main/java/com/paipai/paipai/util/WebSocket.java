package com.paipai.paipai.util;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint(value = "/websocket/{username}")
public class WebSocket {
    private static final Logger log = LoggerFactory.getLogger(WebSocket.class);
    public static int onlineCount = 0;
    public static Map<String, WebSocket> clients = new HashMap<String, WebSocket>();
    public Session session;
    public String username;

    public String getUsername() {
        return username;
    }

}
