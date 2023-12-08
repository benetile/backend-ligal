package com.backoffice.services.conversation;

import com.backoffice.entites.Account;
import com.backoffice.entites.Conversation;
import com.backoffice.entites.Message;
import com.backoffice.entites.annonce.Annonce;
import com.backoffice.repositories.AccountRepository;
import com.backoffice.repositories.AnnonceRepository;
import com.backoffice.repositories.ConversationRepository;
import com.backoffice.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
   /* @Autowired
    private ConversationRepository conversationRepository;*/
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(MessageService.class);

    public Message programmerMessage(Message message){
        List<Account>accounts = new ArrayList<>();
        logger.info("Début du traitement de la programmation de l'envoi de l'Email");
        Conversation conversation = new Conversation();
        logger.info("Création d'un fil de conversation entre le 2 utilisateurs ");
        Annonce annonce = annonceRepository.findById(message.getIdAnnonce()).orElseThrow(()-> new IllegalArgumentException("Invalid Id : "+message.getIdAnnonce()));
        Account account = annonce.getAccount();
        Account accountTwo = accountRepository.findByEmail(message.getExpediteur()).orElseThrow(()->new  IllegalArgumentException("Invalid Email : "+message.getExpediteur()));
        accounts.add(account);
        accounts.add(accountTwo);
        message.setDestinataire(account.getEmail());
        message.setProgrammer(true);
        Message msg = messageRepository.save(message);
        logger.info("Sauvergade du message programmé portant l'Id : "+msg.getId()+ " avec succès");
        conversation.setAnnonce(annonce);
        //conversation.setAccounts(accounts);
        conversation.setTitre(annonce.getTitre());
        List<Message> messages = new ArrayList<>();
        messages.add(msg);
        conversation.setMessages(messages);
        //conversationRepository.save(conversation);
        logger.info("Fin de la création du fil de conversation entre le 2 utilisateurs");
        logger.info("L'envoi du message a été programmé avec succès pour le : "+message.getDate());
        return message;
    }
}
