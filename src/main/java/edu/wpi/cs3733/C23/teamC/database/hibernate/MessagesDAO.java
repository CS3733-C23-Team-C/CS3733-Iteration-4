package edu.wpi.cs3733.C23.teamC.database.hibernate;

import java.util.HashMap;

public interface MessagesDAO {

  HashMap<Integer, MessagesEntity> getMessages();

  MessagesEntity getMessage(int id);

  void addMessage(MessagesEntity message);

  void deleteMessage(int id);

  int generateMessageID();

  HashMap<Integer, MessagesEntity> getMessages(String id);

  HashMap<Integer, MessagesEntity> getMessages(String id, int lastID);
}
