package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.Messages;

import java.util.HashMap;

public interface MessagesDAO {

  HashMap<Integer, Messages> getMessages();

  Messages getMessage(int id);

  void addMessage(Messages message);

  void deleteMessage(int id);

  int generateMessageID();

  HashMap<Integer, Messages> getMessages(String id);

  HashMap<Integer, Messages> getMessages(String id, int lastID);
}
