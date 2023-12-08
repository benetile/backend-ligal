package com.backoffice.repositories;

import com.backoffice.entites.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ConversationRepository /*extends JpaRepository<Conversation,Long> */{

   /* List<Conversation> findByAccountsId(long id);

    List<Conversation> findByAnnonceIdAndAccountsIdIn(long id,List<Long> ids);*/
}
