/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.senserver;

import com.forall.DataProxyDecoder;
import com.forall.DataProxyEncoder;
import com.forall.modell.DataProxy;
import com.forall.service.DataService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author jd
 */
@ServerEndpoint(value = "/temperature",
        decoders = DataProxyDecoder.class,
        encoders = DataProxyEncoder.class)
@Singleton
public class TemperatureDispatcher {

    @EJB
    private DataService dataService;

    private final Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(final DataProxy proxy, final Session session) {
        System.out.println("Server receiving! " + System.currentTimeMillis());
        long start = System.currentTimeMillis();

        clients
                .parallelStream()
                .filter(client -> !client.equals(session))
                .forEach(client -> {

            client.getAsyncRemote().sendObject(proxy);
                });

        dataService.save(proxy.getData(), proxy.getTimeStamp());
        System.out.println("Data 2 sent in " + (System.currentTimeMillis() - start) + " ms");
    }

    @OnOpen
    public void addSession(Session session) {
        clients.add(session);
        System.out.println("Session added " + session.getId());
    }

    @OnClose
    public void removeSession(Session session) {
        clients.remove(session);
        System.out.println("Session removed !");
    }
}
