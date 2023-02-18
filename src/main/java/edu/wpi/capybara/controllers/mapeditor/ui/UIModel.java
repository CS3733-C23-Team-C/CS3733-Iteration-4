package edu.wpi.capybara.controllers.mapeditor.ui;

import java.util.List;
import java.util.Set;

import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.LocationNameAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.MoveAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public interface UIModel {

    interface ElementInternal<MAP, TABLE, REPO> {
        MAP onMap(); // graphical representation on the map view
        TABLE inTable(); // graphical representation in the table view
        REPO inRepo();

        void showAsSelected();
        void showAsDeselected();
    }

    interface GFXNode {
        Circle circle();
        Label name();
    }

    interface GFXEdge {
        Line line();
    }

    interface GFXMove {

    }

    interface GFXLocation {

    }

    class TableNode extends TableRow<NodeAdapter> {}
    class TableEdge extends TableRow<EdgeAdapter> {}
    class TableMove extends TableRow<MoveAdapter> {}
    class TableLocation extends TableRow<LocationNameAdapter> {}

    interface NodeElement extends ElementInternal<GFXNode, TableNode, NodeAdapter>, Element {}
    interface EdgeElement extends ElementInternal<GFXEdge, TableEdge, EdgeAdapter>, Element {}
    interface MoveElement extends ElementInternal<GFXMove, TableMove, MoveAdapter>, Element {}
    interface LocationElement extends ElementInternal<GFXLocation, TableLocation, LocationNameAdapter>, Element {}

    interface Element {} // poor imitation of c++'s std::variant and rust's enum

    List<Element> elements();
    void add(Element element);
    void delete(Element element);

    Set<Element> selected();
    void select(Element element);
    void deselect(Element element);
}
