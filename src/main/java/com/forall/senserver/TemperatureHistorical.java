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
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.EncodeException;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author jd
 */

@ServerEndpoint(value = "/historical/{from}/{to}",
        decoders = DataProxyDecoder.class,
        encoders = DataProxyEncoder.class)
@Singleton
public class TemperatureHistorical {

    @EJB
    private DataService dataService;

    @OnOpen
    public void onOpen(@PathParam("from") String from, @PathParam("to") String to, Session session) throws IOException, InterruptedException {
        returnData(from, to, "", session);
    }

    @OnMessage
    public void returnData(@PathParam("from") String from, @PathParam("to") String to, String message, final Session session) throws IOException, InterruptedException {
        System.out.println("historical connected ");

        long f = Long.parseLong(from);
        long t = Long.parseLong(to);

        List<DataProxy> proxys = dataService.get(f, t);
        for (DataProxy proxy : proxys) {
            try {
                session.getBasicRemote().sendObject(proxy);
            } catch (EncodeException ex) {
                Logger.getLogger(TemperatureHistorical.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread.sleep(50);
        }
    }

    @OnError
    public void error(Throwable t, Session session) {
        t.printStackTrace();
    }
}
