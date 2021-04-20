package com.dzy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dzy.service.MessageService;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/sendMessage")
    public String sendMessage() {
        return messageService.send();
    }
}
