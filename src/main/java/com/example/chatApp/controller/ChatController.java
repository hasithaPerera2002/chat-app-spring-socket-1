package com.example.chatApp.controller;

import com.example.chatApp.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/public")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload MessageDto messageDto) {
        log.info("Sending message to all users : " + messageDto.getSenderName());
        return messageDto;
    }

    @MessageMapping("/private")
    public MessageDto sendPrivateMessage(@Payload MessageDto messageDto) {
        log.info("Sending message to user : " + messageDto.getReceiverName() + " From : " + messageDto.getSenderName());
        messagingTemplate.convertAndSend("/user/"+messageDto.getReceiverName() + "/private", messageDto);
        return messageDto;
    }
}
