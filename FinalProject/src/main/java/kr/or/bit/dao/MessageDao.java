package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Message;

/*
*
* @date: 2019. 06. 21.
*
* @author: 정성윤
*
* @description: MessageDao의 CRUD
*
*/
public interface MessageDao {
  void insertMessage(Message message);

  void deleteMessage(int id);

  List<Message> selectAllMessage(String receiver_username);
  
  List<Message> selectAllSenderMessage(String sender_username);
      
  List<Message> selectMessageById(String sender_username);
  
  List<Message> selectReadMessage(String receiver_username);

  List<Message> selectUnreadMessage(String receiver_username);
  
  List<Message> selectMainMessage(String receiver_username);
  
  Message selectOneMessage(int id);
  
  void updateMessageChecked(int id);
  
  int selectCountMessage(String receiver_username);
  
}
