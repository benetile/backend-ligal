package com.backoffice.services;

import com.backoffice.entites.Account;
import com.backoffice.entites.Conversation;
import com.backoffice.entites.Email;
import com.backoffice.entites.Message;
import com.backoffice.entites.Users.User;
import com.backoffice.entites.annonce.Annonce;
import com.backoffice.repositories.*;
import com.backoffice.services.conversation.ConversationService;
import com.backoffice.services.users.UserService;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private AccountRepository accountRepository;
    /*@Autowired
    private ConversationService conversationService;*/

    @Autowired
    Configuration configuration;
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    //@Scheduled(fixedDelay = 8640000)
    public void messageNonEnvoye(){
        emailRepository.findByEnvoyeFalse().parallelStream().forEach(this::confirmationCreationCompte);
    }
    public void confirmationCreationCompte(Email email){
        User user = userRepository.findByEmail(email.getDestinataire()).orElseThrow(()-> new IllegalArgumentException("Invalid Email : " +email.getDestinataire()));
        Account account = user.getAccount();
        String password = email.getCode();
        Map<Object, Object> map = new HashMap<>();
        map.put("user",user);
        map.put("password",password);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate("email-creation-compte.ftl");
            helper.setTo(email.getDestinataire());
           //helper.addCc("etilebenny@gmail.com");
            String confirmationPage = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
            helper.setText(confirmationPage,true);
            helper.setSubject("Confirmation de la création du compte client sur Ligal pour "+account.getPrenom()+ " "+account.getNom());
            helper.setFrom("etilebenny@gmail.com");
            javaMailSender.send(message);
            logger.info("Message de création envoyé avec succès");
            email.setEnvoye(true);
            email.setCode("");
            emailRepository.save(email);
            logger.info("Mis à jour du status du Mail portant l'id : "+email.getId());
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean envoyerEmailAnnonce(Message msg){
        Annonce annonce = annonceRepository.findById(msg.getIdAnnonce()).orElseThrow(()-> new IllegalArgumentException("Inavlide annonce ID : "+msg.getIdAnnonce()));
        Account account = annonce.getAccount();
        Map<Object,Object> map =new HashMap<>();

        MimeMessage message = javaMailSender.createMimeMessage();
        try {

            map.put("message",msg.getContenue());
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            Template template = configuration.getTemplate("email-annonce.ftl");
            helper.setFrom(msg.getExpediteur());
            logger.info("Email de l'expediteur "+msg.getExpediteur());
            helper.setTo(account.getEmail());
            msg.setDestinataire(account.getEmail());
            logger.info("Email du destinataire "+account.getEmail());
            String confirmationPage = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
            helper.setText(confirmationPage,true);
            helper.setSubject("Proposition pour "+annonce.getTitre()+ " référence "+annonce.getReference());
            logger.info("L'envoi du mail à été envoyé avec succès de l'annonce "+msg.getIdAnnonce()+ " au "+msg.getExpediteur()+ "\n");
            javaMailSender.send(message);
            //creation de la conversation
          //  conversationService.creationConversation(msg,annonce);

        } catch (MessagingException | TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Boolean envoiEmailProgrammer(Message msg){
        Annonce annonce = annonceRepository.findById(msg.getIdAnnonce()).orElseThrow(()-> new IllegalArgumentException("Inavlide annonce ID : "+msg.getIdAnnonce()));
        Account account = annonce.getAccount();
        Map<Object,Object> map =new HashMap<>();

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            map.put("message",msg.getContenue());
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            Template template = configuration.getTemplate("email-annonce.ftl");
            helper.setFrom(msg.getExpediteur());
            logger.info("Email de l'expediteur "+msg.getExpediteur());
            helper.setTo(msg.getDestinataire());
            logger.info("Email du destinataire "+msg.getDestinataire());
            String confirmationPage = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
            helper.setText(confirmationPage,true);
            helper.setSubject("Proposition pour "+annonce.getTitre()+ " référence "+annonce.getReference());
            logger.info("L'envoi du mail programmé à été envoyé avec succès de l'annonce "+msg.getIdAnnonce()+ " au "+msg.getDestinataire()+" a "+ LocalDateTime.now()+"\n");
            javaMailSender.send(message);
            msg.setProgrammer(false);
            messageRepository.save(msg);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    /*public boolean envoyerEmailAnnonce(

    )*/
}
