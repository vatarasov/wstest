package com.example.wsserver;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author vtarasov
 * @since 24.01.19
 */
@Controller
public class WsServerController {
    @MessageMapping("/log")
    public void log(String message) {
        System.out.println(message);
    }
}
