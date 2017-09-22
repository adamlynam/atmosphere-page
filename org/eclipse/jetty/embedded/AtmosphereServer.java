package org.eclipse.jetty.embedded;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.File;

public class AtmosphereServer {

    public static final String DOMAIN = "lynam.co.nz";
    public static final int PORT = 9090;

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {
            getWebSocketHandler(),
            new FastFileHandler(new File("index.html"), DOMAIN, PORT),
            new DefaultHandler()
        });
        server.setHandler(handlers);
        server.start();
        server.join();
    }

    private static WebSocketHandler getWebSocketHandler() {
        return new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(WebSocketManager.class);
            }
        };
    }
}