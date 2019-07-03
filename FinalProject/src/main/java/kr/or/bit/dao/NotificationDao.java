package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Notification;

/*
*
* @date: 2019. 06. 21.
*
* @author: 정성윤
*
* @description: Notification의 CRUD
*
*/
public interface NotificationDao {
  void insertNotification(Notification Notification);

  void checkAllNotification(String username);

  void deleteNotification(int id);

  List<Notification> selectAllNotification(String username);

  List<Notification> selectAllNewNotification(String username);

  List<Notification> selectAllOldNotification(String username);
}
