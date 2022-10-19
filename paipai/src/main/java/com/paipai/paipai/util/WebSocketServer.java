package com.paipai.paipai.util;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocketServer {

    @OnOpen
    public void openSocket(@PathVariable("id") Integer id, Session session) {
        session.getAsyncRemote().sendText("服务器收到客户端请求");
    }
    @OnMessage
    public void receiveMessage(Session session,String message) {
        System.out.println("session" + session );
    }



}