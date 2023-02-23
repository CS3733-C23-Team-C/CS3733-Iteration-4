package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.dialogs;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.AdapterRepository;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import javafx.stage.Window;

public class ReplaceNodeDialog extends NodeDialog {

  private final NodeAdapter node;
  private final AdapterRepository repository;

  public ReplaceNodeDialog(Window owner, AdapterRepository repository, NodeAdapter node) {
    super(owner);
    this.node = node;
    this.repository = repository;
    setTitle("Modify Node");

    xCoord.setText(Integer.toString(node.getXCoord()));
    yCoord.setText(Integer.toString(node.getYCoord()));
    floor.setText(node.getFloor());
    building.setText(node.getFloor());

    floor.setEditable(false);
    building.setEditable(false);
    floor.setDisable(true);
    building.setDisable(true);
  }

  @Override
  protected void onOKPressed() {
    final var newNode =
        repository.createNode(
            nodeID.getText(),
            Integer.parseInt(xCoord.getText()),
            Integer.parseInt(yCoord.getText()),
            floor.getText(),
            building.getText());
    for (EdgeEntity edge : ((NodeEntity) node.buildingProperty().getBean()).getEdges()) {
      System.out.println("Modifying edge " + edge.toString());
      if (edge.getNode1().equals(node.getNodeID())) {
        edge.setNode1(newNode.getEntity());
      } else {
        edge.setNode2(newNode.getEntity());
      }
    }
    // repository.getNodes().remove(node);
  }
}
