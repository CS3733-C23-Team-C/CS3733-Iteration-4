package edu.wpi.cs3733.C23.teamC.database.hibernate;

import java.util.ArrayList;

public interface MoveDAO {

  ArrayList<MoveEntity> getMoves();

  void addMove(MoveEntity submission);

  void deleteMove(MoveEntity move);
}
