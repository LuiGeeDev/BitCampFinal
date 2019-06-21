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

  void selectMessageById(String sender_username);

  void selectReadMessage(String receiver_username);

  void selectUnreadMessage(String receiver_username);
}
