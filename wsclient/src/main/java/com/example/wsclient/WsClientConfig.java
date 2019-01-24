package com.example.wsclient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * @author vtarasov
 * @since 24.01.19
 */
@Configuration
public class WsClientConfig {

    @Autowired
    private WebSocketStompClient client;

    @Bean
    public WebSocketStompClient client() {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());

        return stompClient;
    }

    @EventListener
    public void onStartApplication(ContextRefreshedEvent event) {
        //connect(new WsClientStompSessionHandler());
        connect();
    }

    /*private void connect(StompSessionHandler sessionHandler) {
        client.connect("ws://localhost:8080/app", sessionHandler);
    }*/

    private void connect() {
        client.connect("ws://localhost:8080/app", new WsClientStompSessionHandler());
    }

    private class WsClientStompSessionHandler implements StompSessionHandler {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            /*int counter = 0;
            while (true) {
                session.send("/log", String.valueOf(counter++));
                sleep(5000);
            }*/
            session.send("/log", "Hello World!");
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            sleep(5000);
            //CompletableFuture.runAsync(() -> connect(this));
            //connect();
            CompletableFuture.runAsync(() -> connect());
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return null;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
        }

        private void sleep(int time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {}
        }
    }
}
