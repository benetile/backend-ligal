package com.backoffice.services.conversation;

import com.backoffice.entites.Account;
import com.backoffice.entites.Conversation;
import com.backoffice.entites.Message;
import com.backoffice.entites.annonce.Annonce;
import com.backoffice.repositories.AccountRepository;
import com.backoffice.repositories.ConversationRepository;
import com.backoffice.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
public class ConversationService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;
    //@Autowired
    //private ConversationRepository conversationRepository;

    @Autowired
    private AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(ConversationService.class);

    public Conversation creationConversation(Message message, Annonce annonce){
        Conversation conversation = new Conversation();
        Account expediteur= accountRepository.findByEmail(message.getExpediteur()).orElseThrow(()-> new IllegalArgumentException("Invalid Email expediteur "+message.getExpediteur()));
        Account destinataire = accountRepository.findByEmail(message.getDestinataire()).orElseThrow(()-> new IllegalArgumentException("Invalid Email destinataire "+message.getDestinataire()));
        List<Account> accounts = new ArrayList<>();
        accounts.add(expediteur);
        accounts.add(destinataire);
        Message msg = messageRepository.save(message);
        List<Message> messages = new ArrayList<>();
        messages.add(msg);
      //  conversation.setAccounts(accounts);
        conversation.setAnnonce(annonce);
        conversation.setTitre(annonce.getReference());
        logger.info("Début de la création d'une nouvelle conversation");
        List<Long>ids = new ArrayList<>();
        for (Account acc : accounts) {
            ids.add(acc.getId());
        }

        /*if (conversationRepository.findByAnnonceIdAndAccountsIdIn(annonce.getId(),ids).isEmpty()){
            logger.info("Aucune conversation existe entre le 2 utilisateurs pour cette annonce ");
           // conversationRepository.save(conversation);
        }*/

        return conversation;
    }


}
