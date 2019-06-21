package kr.or.bit.dao;

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

  void updateNotification(int checked);

  void deleteNotification(int id);

  void selectAllNotification(int id);

  void selectAllNewNotification(int id);

  void selectAllOldNotification(int id);
}
