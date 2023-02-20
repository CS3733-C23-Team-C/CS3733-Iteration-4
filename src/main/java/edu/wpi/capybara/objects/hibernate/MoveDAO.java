package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.Move;

import java.util.ArrayList;

public interface MoveDAO {

  ArrayList<Move> getMoves();

  void addMove(Move submission);

  void deleteMove(Move move);
}
