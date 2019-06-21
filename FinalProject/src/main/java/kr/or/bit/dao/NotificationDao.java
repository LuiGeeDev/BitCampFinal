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

  void updateNotification(int checked);

  void deleteNotification(int id);

  List<Notification> selectAllNotification(int id);

  List<Notification> selectAllNewNotification(int id);

  List<Notification> selectAllOldNotification(int id);
}
