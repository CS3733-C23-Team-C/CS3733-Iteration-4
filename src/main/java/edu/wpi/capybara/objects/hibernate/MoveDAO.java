package edu.wpi.capybara.objects.hibernate;

import java.util.ArrayList;
import java.util.HashMap;

public interface MoveDAO {

    ArrayList<MoveEntity> getMoves();
    void addMove(MoveEntity submission);
    void deleteMove(MoveEntity move);
}
