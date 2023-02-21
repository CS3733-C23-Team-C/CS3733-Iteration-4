package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.MoveEntity;
import java.util.ArrayList;

public interface MoveDAO {

  ArrayList<MoveEntity> getMoves();

  void addMove(MoveEntity submission);

  void deleteMove(MoveEntity move);
}
