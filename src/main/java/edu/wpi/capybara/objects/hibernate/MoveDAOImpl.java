package edu.wpi.capybara.objects.hibernate;

import java.util.ArrayList;

public class MoveDAOImpl implements MoveDAO {
  ArrayList<MoveEntity> moves = new ArrayList<>();

  @Override
  public ArrayList<MoveEntity> getMoves() {
    return moves;
  }

  @Override
  public void addMove(MoveEntity submission) {
    newDBConnect.insertNew(submission);
    this.moves.add(submission);
  }

  public MoveDAOImpl(ArrayList<MoveEntity> moves) {
    this.moves = moves;
  }

  @Override
  public void deleteMove(MoveEntity move) {
    newDBConnect.delete(move);
    moves.remove(move);
  }
}
