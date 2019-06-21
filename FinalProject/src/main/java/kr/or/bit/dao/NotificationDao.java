package kr.or.bit.dao;

import kr.or.bit.model.Notification;

public interface NotificationDao {
  void insertNotification(Notification Notification);
  void updateNotification(int checked);
  void deleteNotification(int id);
  void selectAllNotification(int id);
  void selectAllNewNotification(int id);
  void selectAllOldNotification(int id);
}
