package com.paipai.paipai.util;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocketServer {

    public static ConcurrentHashMap<Integer, List<Session>> map = new ConcurrentHashMap<>();

    @OnOpen
    public void openSocket(@PathParam("id") Integer id, Session session) {
        System.out.println("开启成功" + session);
        List<Session> list = null;
        //检查集合中是都有Aid,如果没有，新创建一个list ,把list 绑定到对应的aid 上；
        if (!map.containsKey(id)) {
            list = new ArrayList<>();
            map.put(id, list);
        } else {
            list = map.get(id);
        }
        list.add(session);
    }

    @OnMessage
    public void receiveMessage(Session session, String message) {
        System.out.println("session" + session);
    }
    @OnClose
    public void closeSocket(Session session) {
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        boolean flag = true;
        while (flag && iterator.hasNext()) {
            Integer id = iterator.next();
            List<Session> list = map.get(id);
            for (Session tempsession : list) {
                if (tempsession == session) {
                    list.remove(tempsession);
                    flag = false;
                    break;
                }
            }
        }
    }
    public static void sendMessage(Integer id,Integer nprice,String hid) {
        List<Session> list = map.get(id);
        for (Session session:list) {
            session.getAsyncRemote().sendText(nprice.toString() + "," +  hid);
        }
    }


}



















