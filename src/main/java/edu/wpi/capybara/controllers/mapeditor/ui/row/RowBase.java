package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.beans.InvalidationListener;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import lombok.Getter;

class RowBase<T> extends TableRow<T> implements Selectable {
    public RowBase() {

    }

    @Override
    public void showAsSelected() {

    }

    @Override
    public void showAsDeselected() {

    }
}
