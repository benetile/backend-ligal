package com.backoffice.controllers.account;

import com.backoffice.entites.Account;
import com.backoffice.entites.annonce.Annonce;
import com.backoffice.repositories.AccountRepository;
import com.backoffice.services.account.AccountService;
import org.aspectj.weaver.ast.Literal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping(name = "/")
    public List<Account>getAllAccount(){
        return accountRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Account getAccount(@PathVariable("id") Long id){
        return accountRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Une erreur s'est produite lors de la récupération de l'utilisateur"));
    }

    @GetMapping("/search/{value}")
    public List<Account> getAccountByNameOrEmail(@PathVariable("value") String value){
        if(value.isEmpty()){
            logger.info("Retour de la listet de tous les utilisateur car la valeur saisie est null");
            return accountRepository.findAll();
        }else if(accountRepository.findByNomContainsIgnoreCaseOrPrenomContainsIgnoreCaseOrEmailContainsIgnoreCase(value,value,value)!= null){
            logger.info("Renvoie la liste d'utilisateur qui contiennent le mot "+value);
            return accountRepository.findByNomContainsIgnoreCaseOrPrenomContainsIgnoreCaseOrEmailContainsIgnoreCase(value,value,value);
        }else{
            return accountRepository.findAll();
        }
    }



    @PostMapping("/add")
    public Account createAccount(@RequestBody Account account){
        return accountService.creation(account);
    }

    @GetMapping("/email/{email}")
    public Boolean verifierSiEmailExiste(@PathVariable("email") String email){
        return accountRepository.findByEmail(email).isPresent();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable("id") long id){
        logger.info("suppréssionn du compte utilisateur");
        accountService.suppressionAccount(id);
    }

}
