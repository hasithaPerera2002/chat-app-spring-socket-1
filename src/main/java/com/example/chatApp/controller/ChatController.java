package com.example.chatApp.controller;

import com.example.chatApp.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/public")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload MessageDto messageDto) {
        return messageDto;
    }

    @MessageMapping("/private")

    public MessageDto sendPrivateMessage(@Payload MessageDto messageDto) {
        messagingTemplate.convertAndSend("user/"+messageDto.getReceiver() + "/private", messageDto);
        return messageDto;
    }
}
