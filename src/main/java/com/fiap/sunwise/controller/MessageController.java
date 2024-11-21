package com.fiap.sunwise.controller;

import com.fiap.sunwise.service.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class MessageController {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        rabbitMQProducer.sendMessage("sunwise_exchange", "sunwise.key", message);
        return "Message sent: " + message;
    }
}
