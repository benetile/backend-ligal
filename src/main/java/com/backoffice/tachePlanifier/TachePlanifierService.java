package com.backoffice.tachePlanifier;

import com.backoffice.entites.Message;
import com.backoffice.repositories.EmailRepository;
import com.backoffice.repositories.MessageRepository;
import com.backoffice.services.EmailService;
import com.backoffice.services.adresse.DepartementService;
import com.backoffice.services.adresse.RegionService;
import com.backoffice.services.adresse.VilleService;
import com.backoffice.services.users.RoleService;
import com.backoffice.services.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TachePlanifierService {

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RegionService regionService;
    @Autowired
    private DepartementService departementService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private VilleService villeService;

    Logger logger = LoggerFactory.getLogger(TachePlanifierService.class);

    //@Scheduled(fixedDelay = 846000)
    public void initialisation(){
        logger.info("Création de l'admin par defaut ");
        roleService.initialisation();

        logger.info("debut de traitement des departements");
        departementService.intialiserLesDepartement();
        logger.info("Fin de traitement des departements");

        logger.info("début de traitement des villes");
        villeService.initialisationDesVilles();
        logger.info("Fin de traitements des villes ");

        userService.initialisationAdminAccount();


    }

    //@Scheduled(fixedDelay = 60000)
    public void programmerContactVendeur(){
        List<Message> messages = messageRepository.findByProgrammerTrue();
        if (messages.isEmpty()){
            logger.info("Aucun Email planifer à été trouvé ");
        }else{
            messages.parallelStream().
                    filter(message -> compareDate(message.getDate()))
                    .forEach(message -> emailService.envoiEmailProgrammer(message));
        }
        logger.warn("Tache planifier pour contacter vendeur éffectué le "+new Date());
    }

    public Boolean compareDate(Date email){
        boolean isTrue = false;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime emailDate = LocalDateTime.of(email.getYear(),email.getMonth(),email.getDay(),email.getHours(),email.getMinutes());
        if(now.isEqual(emailDate) || emailDate.isBefore(now)){
            isTrue = true;
        }
        return isTrue;
    }


}
