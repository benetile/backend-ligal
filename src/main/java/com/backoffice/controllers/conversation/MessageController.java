package com.backoffice.controllers.conversation;

import com.backoffice.entites.Message;
import com.backoffice.services.EmailService;
import com.backoffice.services.conversation.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/message")
@CrossOrigin
public class MessageController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageService messageService;
    @PostMapping("/envoi")
    public void envoiEmailAnnonce(@RequestBody Message message){
        emailService.envoyerEmailAnnonce(message);
    }

    @PostMapping("/programmer")
    public Message programmerEnvoiEmailAnnonce(@RequestBody Message message){
        return messageService.programmerMessage(message);
    }
}
